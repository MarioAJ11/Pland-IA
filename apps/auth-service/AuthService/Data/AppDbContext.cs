using Microsoft.EntityFrameworkCore;
using AuthService.Models.Entities;

namespace AuthService.Data;

/// <summary>
/// Contexto de base de datos para el Auth Service
/// Maneja la conexión y configuración de Entity Framework Core con PostgreSQL
/// </summary>
public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    /// <summary>
    /// DbSet de usuarios - Representa la tabla "users" en auth_schema
    /// </summary>
    public DbSet<User> Users { get; set; }

    /// <summary>
    /// Configuración avanzada del modelo usando Fluent API
    /// </summary>
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        // Configuración del schema por defecto
        modelBuilder.HasDefaultSchema("auth_schema");

        // Configuración de la entidad User
        modelBuilder.Entity<User>(entity =>
        {
            // Índice único en Email (no pueden haber emails duplicados)
            entity.HasIndex(u => u.Email)
                .IsUnique()
                .HasDatabaseName("idx_user_email");

            // Índice en RefreshToken para búsquedas rápidas
            entity.HasIndex(u => u.RefreshToken)
                .HasDatabaseName("idx_user_refresh_token");

            // Configuración de propiedades con valores por defecto
            entity.Property(u => u.CreatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP");

            entity.Property(u => u.UpdatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP");

            entity.Property(u => u.IsActive)
                .HasDefaultValue(true);

            // Configuración de tipos de columna específicos de PostgreSQL
            entity.Property(u => u.Id)
                .HasColumnType("uuid");
        });
    }

    /// <summary>
    /// Sobrescribir SaveChanges para actualizar automáticamente UpdatedAt
    /// </summary>
    public override int SaveChanges()
    {
        UpdateTimestamps();
        return base.SaveChanges();
    }

    /// <summary>
    /// Sobrescribir SaveChangesAsync para actualizar automáticamente UpdatedAt
    /// </summary>
    public override Task<int> SaveChangesAsync(CancellationToken cancellationToken = default)
    {
        UpdateTimestamps();
        return base.SaveChangesAsync(cancellationToken);
    }

    /// <summary>
    /// Actualiza automáticamente el campo UpdatedAt antes de guardar
    /// </summary>
    private void UpdateTimestamps()
    {
        var entries = ChangeTracker.Entries()
            .Where(e => e.Entity is User && e.State == EntityState.Modified);

        foreach (var entry in entries)
        {
            ((User)entry.Entity).UpdatedAt = DateTime.UtcNow;
        }
    }
}
