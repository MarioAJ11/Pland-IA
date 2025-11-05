namespace AuthService.Models.DTOs;

/// <summary>
/// DTO con información pública del usuario
/// NO incluye datos sensibles como PasswordHash o RefreshToken
/// </summary>
public class UserDto
{
    /// <summary>
    /// ID único del usuario
    /// </summary>
    public Guid Id { get; set; }

    /// <summary>
    /// Email del usuario
    /// </summary>
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Nombre completo del usuario
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// URL del avatar del usuario (puede ser null)
    /// </summary>
    public string? Avatar { get; set; }

    /// <summary>
    /// Indica si el usuario está activo
    /// </summary>
    public bool IsActive { get; set; }

    /// <summary>
    /// Fecha de creación de la cuenta
    /// </summary>
    public DateTime CreatedAt { get; set; }
}
