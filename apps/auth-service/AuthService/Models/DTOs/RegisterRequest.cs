using System.ComponentModel.DataAnnotations;

namespace AuthService.Models.DTOs;

/// <summary>
/// DTO para el registro de nuevos usuarios
/// </summary>
public class RegisterRequest
{
    /// <summary>
    /// Email del usuario - Debe ser único
    /// </summary>
    [Required(ErrorMessage = "El email es requerido")]
    [EmailAddress(ErrorMessage = "El formato del email no es válido")]
    [MaxLength(255)]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Contraseña del usuario - Mínimo 8 caracteres
    /// </summary>
    [Required(ErrorMessage = "La contraseña es requerida")]
    [MinLength(8, ErrorMessage = "La contraseña debe tener al menos 8 caracteres")]
    [MaxLength(100)]
    public string Password { get; set; } = string.Empty;

    /// <summary>
    /// Nombre completo del usuario
    /// </summary>
    [Required(ErrorMessage = "El nombre es requerido")]
    [MinLength(2, ErrorMessage = "El nombre debe tener al menos 2 caracteres")]
    [MaxLength(100)]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// URL del avatar (opcional)
    /// </summary>
    [MaxLength(500)]
    [Url(ErrorMessage = "La URL del avatar no es válida")]
    public string? Avatar { get; set; }
}
