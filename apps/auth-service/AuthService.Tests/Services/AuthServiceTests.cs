using AuthService.Data;
using AuthService.Models.DTOs;
using AuthService.Models.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Moq;
using Xunit;

namespace AuthService.Tests.Services;

/// <summary>
/// Tests unitarios para AuthService
/// Valida: Register, Login, Refresh Token, validaciones
/// </summary>
public class AuthServiceTests : IDisposable
{
    private readonly AppDbContext _context;
    private readonly Mock<IConfiguration> _configurationMock;
    private readonly AuthService.Services.AuthService _authService;

    public AuthServiceTests()
    {
        // Configurar base de datos en memoria para tests
        var options = new DbContextOptionsBuilder<AppDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        _context = new AppDbContext(options);

        // Mock de IConfiguration para JWT settings
        _configurationMock = new Mock<IConfiguration>();
        _configurationMock.Setup(c => c["JwtSettings:Secret"])
            .Returns("CAMBIAME_POR_UN_SECRET_SEGURO_DE_AL_MENOS_32_CARACTERES");
        _configurationMock.Setup(c => c["JwtSettings:Issuer"])
            .Returns("PlandIA.AuthService");
        _configurationMock.Setup(c => c["JwtSettings:Audience"])
            .Returns("PlandIA.Clients");
        _configurationMock.Setup(c => c["JwtSettings:ExpirationMinutes"])
            .Returns("60");
        _configurationMock.Setup(c => c["JwtSettings:RefreshExpirationDays"])
            .Returns("7");

        _authService = new AuthService.Services.AuthService(_context, _configurationMock.Object);
    }

    public void Dispose()
    {
        _context.Database.EnsureDeleted();
        _context.Dispose();
    }

    [Fact(DisplayName = "Register debe crear usuario correctamente")]
    public async Task Register_CreatesUserSuccessfully()
    {
        // Arrange
        var registerDto = new RegisterRequest
        {
            Email = "test@plandai.com",
            Password = "Test123!",
            Name = "Test User"
        };

        // Act
        var result = await _authService.RegisterAsync(registerDto);

        // Assert
        Assert.NotNull(result);
        Assert.NotNull(result.AccessToken);
        Assert.NotNull(result.RefreshToken);
        Assert.True(result.AccessToken.Length > 50, "AccessToken debe tener longitud razonable");
        Assert.True(result.RefreshToken.Length >= 30, "RefreshToken debe ser un GUID");

        // Verificar que el usuario se guardó en la base de datos
        var user = await _context.Users.FirstOrDefaultAsync(u => u.Email == registerDto.Email);
        Assert.NotNull(user);
        Assert.Equal(registerDto.Name, user.Name);
        Assert.NotEqual(registerDto.Password, user.PasswordHash); // Password debe estar hasheado
    }

    [Fact(DisplayName = "Register debe lanzar excepción con email duplicado")]
    public async Task Register_ThrowsException_WhenEmailExists()
    {
        // Arrange
        var existingUser = new User
        {
            Email = "existing@plandai.com",
            PasswordHash = BCrypt.Net.BCrypt.HashPassword("Password123!"),
            Name = "Existing User"
        };
        _context.Users.Add(existingUser);
        await _context.SaveChangesAsync();

        var registerDto = new RegisterRequest
        {
            Email = "existing@plandai.com",
            Password = "AnotherPassword123!",
            Name = "Another User"
        };

        // Act & Assert
        var exception = await Assert.ThrowsAsync<InvalidOperationException>(
            () => _authService.RegisterAsync(registerDto)
        );

        Assert.Equal("El email ya está registrado", exception.Message);
    }

    [Fact(DisplayName = "Login debe retornar tokens con credenciales correctas")]
    public async Task Login_ReturnsTokens_WithValidCredentials()
    {
        // Arrange - Crear usuario primero
        var password = "Test123!";
        var user = new User
        {
            Email = "login@plandai.com",
            PasswordHash = BCrypt.Net.BCrypt.HashPassword(password),
            Name = "Login User"
        };
        _context.Users.Add(user);
        await _context.SaveChangesAsync();

        var loginDto = new LoginRequest
        {
            Email = "login@plandai.com",
            Password = password
        };

        // Act
        var result = await _authService.LoginAsync(loginDto);

        // Assert
        Assert.NotNull(result);
        Assert.NotNull(result.AccessToken);
        Assert.NotNull(result.RefreshToken);
        Assert.True(result.AccessToken.Length > 50);
        Assert.True(result.RefreshToken.Length >= 30);
    }

    [Fact(DisplayName = "Login debe lanzar excepción con email inexistente")]
    public async Task Login_ThrowsException_WhenEmailNotFound()
    {
        // Arrange
        var loginDto = new LoginRequest
        {
            Email = "nonexistent@plandai.com",
            Password = "Test123!"
        };

        // Act & Assert
        var exception = await Assert.ThrowsAsync<UnauthorizedAccessException>(
            () => _authService.LoginAsync(loginDto)
        );

        Assert.Equal("Credenciales inválidas", exception.Message);
    }

    [Fact(DisplayName = "Login debe lanzar excepción con password incorrecto")]
    public async Task Login_ThrowsException_WhenPasswordIsWrong()
    {
        // Arrange - Crear usuario
        var user = new User
        {
            Email = "wrongpass@plandai.com",
            PasswordHash = BCrypt.Net.BCrypt.HashPassword("CorrectPassword123!"),
            Name = "User"
        };
        _context.Users.Add(user);
        await _context.SaveChangesAsync();

        var loginDto = new LoginRequest
        {
            Email = "wrongpass@plandai.com",
            Password = "WrongPassword123!"
        };

        // Act & Assert
        var exception = await Assert.ThrowsAsync<UnauthorizedAccessException>(
            () => _authService.LoginAsync(loginDto)
        );

        Assert.Equal("Credenciales inválidas", exception.Message);
    }

    [Fact(DisplayName = "RefreshToken debe generar nuevo AccessToken con token válido")]
    public async Task RefreshToken_GeneratesNewAccessToken_WithValidToken()
    {
        // Arrange - Registrar usuario para obtener tokens
        var registerDto = new RegisterRequest
        {
            Email = "refresh@plandai.com",
            Password = "Test123!",
            Name = "Refresh User"
        };
        var authResponse = await _authService.RegisterAsync(registerDto);
        var originalAccessToken = authResponse.AccessToken;
        var refreshToken = authResponse.RefreshToken;

        // Esperar un momento para que el nuevo token sea diferente
        await Task.Delay(1000);

        var refreshDto = new RefreshTokenRequest
        {
            RefreshToken = refreshToken
        };

        // Act
        var result = await _authService.RefreshTokenAsync(refreshDto);

        // Assert
        Assert.NotNull(result);
        Assert.NotNull(result.AccessToken);
        Assert.NotNull(result.RefreshToken);
        Assert.NotEqual(originalAccessToken, result.AccessToken); // Nuevo token diferente
    }

    [Fact(DisplayName = "RefreshToken debe lanzar excepción con token inválido")]
    public async Task RefreshToken_ThrowsException_WithInvalidToken()
    {
        // Arrange
        var refreshDto = new RefreshTokenRequest
        {
            RefreshToken = "invalid.refresh.token"
        };

        // Act & Assert
        await Assert.ThrowsAnyAsync<Exception>(
            () => _authService.RefreshTokenAsync(refreshDto)
        );
    }

    [Fact(DisplayName = "Password debe ser hasheado correctamente")]
    public async Task Register_HashesPassword_Correctly()
    {
        // Arrange
        var registerDto = new RegisterRequest
        {
            Email = "hash@plandai.com",
            Password = "PlainTextPassword123!",
            Name = "Hash User"
        };

        // Act
        await _authService.RegisterAsync(registerDto);

        // Assert
        var user = await _context.Users.FirstOrDefaultAsync(u => u.Email == registerDto.Email);
        Assert.NotNull(user);
        Assert.NotEqual(registerDto.Password, user.PasswordHash);
        Assert.True(BCrypt.Net.BCrypt.Verify(registerDto.Password, user.PasswordHash),
            "Password debe poder verificarse con BCrypt");
    }

    [Fact(DisplayName = "JWT AccessToken debe contener claims correctos")]
    public async Task Login_AccessToken_ContainsCorrectClaims()
    {
        // Arrange - Crear usuario
        var user = new User
        {
            Email = "claims@plandai.com",
            PasswordHash = BCrypt.Net.BCrypt.HashPassword("Test123!"),
            Name = "Claims User"
        };
        _context.Users.Add(user);
        await _context.SaveChangesAsync();

        var loginDto = new LoginRequest
        {
            Email = "claims@plandai.com",
            Password = "Test123!"
        };

        // Act
        var result = await _authService.LoginAsync(loginDto);

        // Assert
        Assert.NotNull(result.AccessToken);
        
        // El token JWT tiene 3 partes separadas por puntos
        var tokenParts = result.AccessToken.Split('.');
        Assert.Equal(3, tokenParts.Length);

        // Verificar que el token no está vacío y tiene estructura correcta
        Assert.True(tokenParts[0].Length > 10); // Header
        Assert.True(tokenParts[1].Length > 10); // Payload
        Assert.True(tokenParts[2].Length > 10); // Signature
    }

    [Fact(DisplayName = "Register debe crear usuario con timestamp correcto")]
    public async Task Register_SetsCreatedAtTimestamp()
    {
        // Arrange
        var beforeRegister = DateTime.UtcNow;
        var registerDto = new RegisterRequest
        {
            Email = "timestamp@plandai.com",
            Password = "Test123!",
            Name = "Timestamp User"
        };

        // Act
        await _authService.RegisterAsync(registerDto);
        var afterRegister = DateTime.UtcNow;

        // Assert
        var user = await _context.Users.FirstOrDefaultAsync(u => u.Email == registerDto.Email);
        Assert.NotNull(user);
        Assert.True(user.CreatedAt >= beforeRegister, "CreatedAt debe ser después o igual al inicio del test");
        Assert.True(user.CreatedAt <= afterRegister, "CreatedAt debe ser antes o igual al fin del test");
    }
}
