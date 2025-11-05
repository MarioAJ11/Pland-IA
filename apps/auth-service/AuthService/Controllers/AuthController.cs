using Microsoft.AspNetCore.Mvc;
using AuthService.Models.DTOs;
using AuthService.Services;

namespace AuthService.Controllers;

/// <summary>
/// Controlador de autenticación - Endpoints públicos para registro, login y refresh tokens
/// </summary>
[ApiController]
[Route("api/[controller]")]
public class AuthController : ControllerBase
{
    private readonly IAuthService _authService;
    private readonly ILogger<AuthController> _logger;

    public AuthController(IAuthService authService, ILogger<AuthController> logger)
    {
        _authService = authService;
        _logger = logger;
    }

    /// <summary>
    /// Registra un nuevo usuario en el sistema
    /// </summary>
    /// <param name="request">Datos del nuevo usuario</param>
    /// <returns>Tokens JWT y datos del usuario creado</returns>
    /// <response code="200">Usuario registrado exitosamente</response>
    /// <response code="400">Datos inválidos o email ya registrado</response>
    [HttpPost("register")]
    [ProducesResponseType(typeof(LoginResponse), StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<IActionResult> Register([FromBody] RegisterRequest request)
    {
        try
        {
            var response = await _authService.RegisterAsync(request);
            _logger.LogInformation("Usuario registrado exitosamente: {Email}", request.Email);
            return Ok(response);
        }
        catch (InvalidOperationException ex)
        {
            _logger.LogWarning("Intento de registro con email existente: {Email}", request.Email);
            return BadRequest(new { error = ex.Message });
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error al registrar usuario: {Email}", request.Email);
            return StatusCode(500, new { error = "Error interno del servidor" });
        }
    }

    /// <summary>
    /// Inicia sesión de un usuario existente
    /// </summary>
    /// <param name="request">Credenciales del usuario</param>
    /// <returns>Tokens JWT y datos del usuario</returns>
    /// <response code="200">Login exitoso</response>
    /// <response code="401">Credenciales inválidas o usuario inactivo</response>
    [HttpPost("login")]
    [ProducesResponseType(typeof(LoginResponse), StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public async Task<IActionResult> Login([FromBody] LoginRequest request)
    {
        try
        {
            var response = await _authService.LoginAsync(request);
            _logger.LogInformation("Login exitoso: {Email}", request.Email);
            return Ok(response);
        }
        catch (UnauthorizedAccessException ex)
        {
            _logger.LogWarning("Intento de login fallido: {Email} - {Error}", request.Email, ex.Message);
            return Unauthorized(new { error = ex.Message });
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error al hacer login: {Email}", request.Email);
            return StatusCode(500, new { error = "Error interno del servidor" });
        }
    }

    /// <summary>
    /// Renueva el Access Token usando un Refresh Token válido
    /// Implementa Sliding Expiration para mantener la sesión activa
    /// </summary>
    /// <param name="request">Refresh Token del usuario</param>
    /// <returns>Nuevos tokens JWT</returns>
    /// <response code="200">Tokens renovados exitosamente</response>
    /// <response code="401">Refresh token inválido o expirado</response>
    [HttpPost("refresh")]
    [ProducesResponseType(typeof(LoginResponse), StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public async Task<IActionResult> RefreshToken([FromBody] RefreshTokenRequest request)
    {
        try
        {
            var response = await _authService.RefreshTokenAsync(request);
            _logger.LogInformation("Token renovado exitosamente");
            return Ok(response);
        }
        catch (UnauthorizedAccessException ex)
        {
            _logger.LogWarning("Intento de refresh con token inválido: {Error}", ex.Message);
            return Unauthorized(new { error = ex.Message });
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error al renovar token");
            return StatusCode(500, new { error = "Error interno del servidor" });
        }
    }

    /// <summary>
    /// Cierra la sesión del usuario invalidando su Refresh Token
    /// </summary>
    /// <param name="userId">ID del usuario</param>
    /// <returns>Confirmación de cierre de sesión</returns>
    /// <response code="200">Sesión cerrada exitosamente</response>
    [HttpPost("logout/{userId}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public async Task<IActionResult> Logout(Guid userId)
    {
        try
        {
            await _authService.RevokeRefreshTokenAsync(userId);
            _logger.LogInformation("Sesión cerrada para usuario: {UserId}", userId);
            return Ok(new { message = "Sesión cerrada exitosamente" });
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error al cerrar sesión: {UserId}", userId);
            return StatusCode(500, new { error = "Error interno del servidor" });
        }
    }

    /// <summary>
    /// Endpoint de healthcheck para verificar que el servicio está funcionando
    /// </summary>
    /// <returns>Estado del servicio</returns>
    [HttpGet("health")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public IActionResult Health()
    {
        return Ok(new
        {
            service = "Auth Service",
            status = "healthy",
            timestamp = DateTime.UtcNow
        });
    }
}
