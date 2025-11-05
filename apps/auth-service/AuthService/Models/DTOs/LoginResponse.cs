namespace AuthService.Models.DTOs;

/// <summary>
/// DTO de respuesta del login exitoso
/// Contiene los tokens JWT y la información del usuario
/// </summary>
public class LoginResponse
{
    /// <summary>
    /// Token de acceso JWT (válido por 15 minutos)
    /// </summary>
    public string AccessToken { get; set; } = string.Empty;

    /// <summary>
    /// Token de refresco (válido por 7 días)
    /// Se usa para obtener un nuevo AccessToken sin volver a hacer login
    /// </summary>
    public string RefreshToken { get; set; } = string.Empty;

    /// <summary>
    /// Fecha de expiración del AccessToken (en UTC)
    /// </summary>
    public DateTime ExpiresAt { get; set; }

    /// <summary>
    /// Información pública del usuario autenticado
    /// </summary>
    public UserDto User { get; set; } = null!;
}
