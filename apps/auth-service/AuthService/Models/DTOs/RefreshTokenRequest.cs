using System.ComponentModel.DataAnnotations;

namespace AuthService.Models.DTOs;

/// <summary>
/// DTO para renovar el Access Token usando el Refresh Token
/// </summary>
public class RefreshTokenRequest
{
    /// <summary>
    /// Refresh Token que se obtuvo en el login
    /// </summary>
    [Required(ErrorMessage = "El refresh token es requerido")]
    public string RefreshToken { get; set; } = string.Empty;
}
