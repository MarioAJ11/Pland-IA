using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace AuthService.Models.Entities;

/// <summary>
/// Entidad User - Representa un usuario en el sistema de autenticación
/// </summary>
[Table("users", Schema = "auth_schema")]
public class User
{
    /// <summary>
    /// Identificador único del usuario (UUID)
    /// </summary>
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public Guid Id { get; set; }

    /// <summary>
    /// Email del usuario - Único y requerido
    /// </summary>
    [Required]
    [EmailAddress]
    [MaxLength(255)]
    [Column("email")]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Hash de la contraseña (BCrypt) - NUNCA guardar contraseñas en texto plano
    /// </summary>
    [Required]
    [MaxLength(255)]
    [Column("password_hash")]
    public string PasswordHash { get; set; } = string.Empty;

    /// <summary>
    /// Nombre completo del usuario
    /// </summary>
    [Required]
    [MaxLength(100)]
    [Column("name")]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// URL del avatar del usuario (opcional)
    /// </summary>
    [MaxLength(500)]
    [Column("avatar")]
    public string? Avatar { get; set; }

    /// <summary>
    /// Indica si el usuario está activo
    /// </summary>
    [Column("is_active")]
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Fecha de creación del usuario
    /// </summary>
    [Column("created_at")]
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Fecha de última actualización
    /// </summary>
    [Column("updated_at")]
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Token de refresco actual (para invalidar sesiones)
    /// </summary>
    [MaxLength(500)]
    [Column("refresh_token")]
    public string? RefreshToken { get; set; }

    /// <summary>
    /// Fecha de expiración del refresh token
    /// </summary>
    [Column("refresh_token_expiry")]
    public DateTime? RefreshTokenExpiry { get; set; }
}
