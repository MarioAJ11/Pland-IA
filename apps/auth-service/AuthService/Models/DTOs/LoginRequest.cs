using System.ComponentModel.DataAnnotations;

namespace AuthService.Models.DTOs;

/// <summary>
/// DTO para el inicio de sesión
/// </summary>
public class LoginRequest
{
    /// <summary>
    /// Email del usuario
    /// </summary>
    [Required(ErrorMessage = "El email es requerido")]
    [EmailAddress(ErrorMessage = "El formato del email no es válido")]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Contraseña del usuario
    /// </summary>
    [Required(ErrorMessage = "La contraseña es requerida")]
    public string Password { get; set; } = string.Empty;

    /// <summary>
    /// Si true, la sesión dura 30 días. Si false, dura 7 días.
    /// Implementa la funcionalidad "Recordarme" (Remember Me)
    /// </summary>
    public bool RememberMe { get; set; } = false;
}
