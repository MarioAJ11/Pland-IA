using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using AuthService.Data;
using AuthService.Models.DTOs;
using AuthService.Models.Entities;

namespace AuthService.Services;

/// <summary>
/// Servicio de autenticación que implementa registro, login y gestión de tokens JWT
/// </summary>
public class AuthService : IAuthService
{
    private readonly AppDbContext _context;
    private readonly IConfiguration _configuration;

    public AuthService(AppDbContext context, IConfiguration configuration)
    {
        _context = context;
        _configuration = configuration;
    }

    /// <summary>
    /// Registra un nuevo usuario en el sistema
    /// </summary>
    public async Task<LoginResponse> RegisterAsync(RegisterRequest request)
    {
        // 1. Validar que el email no exista
        var existingUser = await _context.Users
            .FirstOrDefaultAsync(u => u.Email.ToLower() == request.Email.ToLower());

        if (existingUser != null)
        {
            throw new InvalidOperationException("El email ya está registrado");
        }

        // 2. Hash de la contraseña con BCrypt (NUNCA guardar contraseñas en texto plano)
        var passwordHash = BCrypt.Net.BCrypt.HashPassword(request.Password);

        // 3. Crear nuevo usuario
        var user = new User
        {
            Id = Guid.NewGuid(),
            Email = request.Email.ToLower(),
            PasswordHash = passwordHash,
            Name = request.Name,
            Avatar = request.Avatar,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            UpdatedAt = DateTime.UtcNow
        };

        _context.Users.Add(user);
        await _context.SaveChangesAsync();

        // 4. Generar tokens JWT
        var accessToken = GenerateAccessToken(user);
        var refreshToken = GenerateRefreshToken();

        // 5. Guardar refresh token en BD (por defecto 7 días en registro)
        var refreshTokenDays = GetRefreshTokenExpirationDays(rememberMe: false);
        user.RefreshToken = refreshToken;
        user.RefreshTokenExpiry = DateTime.UtcNow.AddDays(refreshTokenDays);
        await _context.SaveChangesAsync();

        // 6. Retornar respuesta con tokens y datos del usuario
        return new LoginResponse
        {
            AccessToken = accessToken,
            RefreshToken = refreshToken,
            ExpiresAt = DateTime.UtcNow.AddMinutes(GetAccessTokenExpirationMinutes()),
            User = MapToUserDto(user)
        };
    }

    /// <summary>
    /// Inicia sesión de un usuario existente
    /// </summary>
    public async Task<LoginResponse> LoginAsync(LoginRequest request)
    {
        // 1. Buscar usuario por email
        var user = await _context.Users
            .FirstOrDefaultAsync(u => u.Email.ToLower() == request.Email.ToLower());

        if (user == null)
        {
            throw new UnauthorizedAccessException("Credenciales inválidas");
        }

        // 2. Verificar contraseña con BCrypt
        var isValidPassword = BCrypt.Net.BCrypt.Verify(request.Password, user.PasswordHash);

        if (!isValidPassword)
        {
            throw new UnauthorizedAccessException("Credenciales inválidas");
        }

        // 3. Verificar que el usuario esté activo
        if (!user.IsActive)
        {
            throw new UnauthorizedAccessException("Usuario inactivo. Contacte al administrador.");
        }

        // 4. Generar tokens JWT
        var accessToken = GenerateAccessToken(user);
        var refreshToken = GenerateRefreshToken();

        // 5. Determinar expiración según RememberMe
        var refreshTokenDays = GetRefreshTokenExpirationDays(request.RememberMe);

        // 6. Guardar refresh token en BD
        user.RefreshToken = refreshToken;
        user.RefreshTokenExpiry = DateTime.UtcNow.AddDays(refreshTokenDays);
        user.UpdatedAt = DateTime.UtcNow;
        await _context.SaveChangesAsync();

        // 7. Retornar respuesta
        return new LoginResponse
        {
            AccessToken = accessToken,
            RefreshToken = refreshToken,
            ExpiresAt = DateTime.UtcNow.AddMinutes(GetAccessTokenExpirationMinutes()),
            User = MapToUserDto(user)
        };
    }

    /// <summary>
    /// Renueva el Access Token usando un Refresh Token válido
    /// Implementa Sliding Expiration: renueva automáticamente el RefreshToken
    /// </summary>
    public async Task<LoginResponse> RefreshTokenAsync(RefreshTokenRequest request)
    {
        // 1. Buscar usuario por refresh token
        var user = await _context.Users
            .FirstOrDefaultAsync(u => u.RefreshToken == request.RefreshToken);

        if (user == null)
        {
            throw new UnauthorizedAccessException("Refresh token inválido");
        }

        // 2. Verificar expiración
        if (user.RefreshTokenExpiry == null || user.RefreshTokenExpiry <= DateTime.UtcNow)
        {
            throw new UnauthorizedAccessException("Refresh token expirado. Por favor, inicie sesión nuevamente.");
        }

        // 3. Verificar que el usuario esté activo
        if (!user.IsActive)
        {
            throw new UnauthorizedAccessException("Usuario inactivo");
        }

        // 4. Determinar si era RememberMe basado en días restantes
        var daysRemaining = (user.RefreshTokenExpiry.Value - DateTime.UtcNow).TotalDays;
        var rememberMeDays = GetRefreshTokenExpirationDays(rememberMe: true);
        var normalDays = GetRefreshTokenExpirationDays(rememberMe: false);
        
        // Si tenía más de 14 días restantes, asumimos que era RememberMe (30 días)
        var wasRememberMe = daysRemaining > (normalDays + rememberMeDays) / 2;
        var newExpirationDays = wasRememberMe ? rememberMeDays : normalDays;

        // 5. Generar nuevos tokens (rotación de tokens para mayor seguridad)
        var accessToken = GenerateAccessToken(user);
        var refreshToken = GenerateRefreshToken();

        // 6. ⭐ SLIDING EXPIRATION: Renovar la fecha desde AHORA
        // Mientras el usuario use la app, nunca expira
        user.RefreshToken = refreshToken;
        user.RefreshTokenExpiry = DateTime.UtcNow.AddDays(newExpirationDays);
        user.UpdatedAt = DateTime.UtcNow;
        await _context.SaveChangesAsync();

        // 7. Retornar nuevos tokens
        return new LoginResponse
        {
            AccessToken = accessToken,
            RefreshToken = refreshToken,
            ExpiresAt = DateTime.UtcNow.AddMinutes(GetAccessTokenExpirationMinutes()),
            User = MapToUserDto(user)
        };
    }

    /// <summary>
    /// Invalida el Refresh Token del usuario (cierra sesión)
    /// </summary>
    public async Task RevokeRefreshTokenAsync(Guid userId)
    {
        var user = await _context.Users.FindAsync(userId);

        if (user != null)
        {
            user.RefreshToken = null;
            user.RefreshTokenExpiry = null;
            user.UpdatedAt = DateTime.UtcNow;
            await _context.SaveChangesAsync();
        }
    }

    // ============================================
    // MÉTODOS PRIVADOS AUXILIARES
    // ============================================

    /// <summary>
    /// Genera un Access Token JWT con los claims del usuario
    /// </summary>
    private string GenerateAccessToken(User user)
    {
        // Leer configuración JWT desde appsettings.json
        var secret = _configuration["JwtSettings:Secret"] 
            ?? throw new InvalidOperationException("JWT Secret no configurado");
        var issuer = _configuration["JwtSettings:Issuer"];
        var audience = _configuration["JwtSettings:Audience"];
        var expirationMinutes = GetAccessTokenExpirationMinutes();

        // Claims: información del usuario que se incluye en el token
        var claims = new[]
        {
            new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
            new Claim(ClaimTypes.Email, user.Email),
            new Claim(ClaimTypes.Name, user.Name),
            new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()), // JWT ID único
            new Claim(JwtRegisteredClaimNames.Iat, DateTimeOffset.UtcNow.ToUnixTimeSeconds().ToString()) // Issued At
        };

        // Crear clave de seguridad simétrica
        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(secret));
        var credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

        // Crear el token JWT
        var token = new JwtSecurityToken(
            issuer: issuer,
            audience: audience,
            claims: claims,
            expires: DateTime.UtcNow.AddMinutes(expirationMinutes),
            signingCredentials: credentials
        );

        // Serializar el token a string
        return new JwtSecurityTokenHandler().WriteToken(token);
    }

    /// <summary>
    /// Genera un Refresh Token (UUID aleatorio)
    /// </summary>
    private string GenerateRefreshToken()
    {
        // Genera un GUID aleatorio como refresh token
        // En producción podrías usar tokens más complejos con criptografía adicional
        return Guid.NewGuid().ToString();
    }

    /// <summary>
    /// Convierte una entidad User a UserDto (solo información pública)
    /// </summary>
    private UserDto MapToUserDto(User user)
    {
        return new UserDto
        {
            Id = user.Id,
            Email = user.Email,
            Name = user.Name,
            Avatar = user.Avatar,
            IsActive = user.IsActive,
            CreatedAt = user.CreatedAt
        };
    }

    /// <summary>
    /// Obtiene la duración del Access Token desde la configuración
    /// </summary>
    private int GetAccessTokenExpirationMinutes()
    {
        return int.Parse(_configuration["JwtSettings:AccessTokenExpirationMinutes"] ?? "15");
    }

    /// <summary>
    /// Obtiene la duración del Refresh Token según si es RememberMe o no
    /// </summary>
    private int GetRefreshTokenExpirationDays(bool rememberMe)
    {
        if (rememberMe)
        {
            return int.Parse(_configuration["JwtSettings:RefreshTokenExpirationDaysRememberMe"] ?? "30");
        }
        else
        {
            return int.Parse(_configuration["JwtSettings:RefreshTokenExpirationDays"] ?? "7");
        }
    }
}
