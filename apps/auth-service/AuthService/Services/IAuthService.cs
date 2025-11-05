using AuthService.Models.DTOs;

namespace AuthService.Services;

/// <summary>
/// Interfaz del servicio de autenticación
/// Define los métodos para registro, login y refresh tokens
/// </summary>
public interface IAuthService
{
    /// <summary>
    /// Registra un nuevo usuario en el sistema
    /// </summary>
    /// <param name="request">Datos del nuevo usuario</param>
    /// <returns>Información del usuario creado con tokens JWT</returns>
    Task<LoginResponse> RegisterAsync(RegisterRequest request);

    /// <summary>
    /// Inicia sesión de un usuario existente
    /// </summary>
    /// <param name="request">Credenciales del usuario</param>
    /// <returns>Información del usuario con tokens JWT</returns>
    Task<LoginResponse> LoginAsync(LoginRequest request);

    /// <summary>
    /// Renueva el Access Token usando un Refresh Token válido
    /// </summary>
    /// <param name="request">Refresh Token del usuario</param>
    /// <returns>Nuevo Access Token y Refresh Token</returns>
    Task<LoginResponse> RefreshTokenAsync(RefreshTokenRequest request);

    /// <summary>
    /// Invalida el Refresh Token del usuario (cierra sesión)
    /// </summary>
    /// <param name="userId">ID del usuario</param>
    Task RevokeRefreshTokenAsync(Guid userId);
}
