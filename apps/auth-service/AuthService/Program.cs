using Microsoft.EntityFrameworkCore;
using AuthService.Data;
using AuthService.Services;
using AuthService.Middleware;
using Serilog;

// Configurar Serilog ANTES de crear el builder
Log.Logger = new LoggerConfiguration()
    .MinimumLevel.Information()
    .MinimumLevel.Override("Microsoft", Serilog.Events.LogEventLevel.Warning)
    .MinimumLevel.Override("Microsoft.EntityFrameworkCore", Serilog.Events.LogEventLevel.Warning)
    .Enrich.FromLogContext()
    .WriteTo.Console(
        outputTemplate: "[{Timestamp:HH:mm:ss} {Level:u3}] {SourceContext}{NewLine}    {Message:lj}{NewLine}{Exception}"
    )
    .WriteTo.File(
        path: "logs/auth-service-.log",
        rollingInterval: RollingInterval.Day,
        outputTemplate: "{Timestamp:yyyy-MM-dd HH:mm:ss.fff zzz} [{Level:u3}] {SourceContext} - {Message:lj}{NewLine}{Exception}",
        retainedFileCountLimit: 30
    )
    .CreateLogger();

try
{
    Log.Information("🚀 Iniciando Auth Service...");

var builder = WebApplication.CreateBuilder(args);

// Reemplazar el logging por defecto con Serilog
builder.Host.UseSerilog();

// Configurar DbContext con PostgreSQL
builder.Services.AddDbContext<AppDbContext>(options =>
{
    var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
    options.UseNpgsql(connectionString);
});

// Registrar AuthService para inyección de dependencias
builder.Services.AddScoped<IAuthService, AuthService.Services.AuthService>();

// Configurar CORS para permitir peticiones desde el frontend
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowFrontend", policy =>
    {
        policy.WithOrigins(
            "http://localhost:3000",      // React dev
            "http://localhost:5173",      // Vite dev
            "https://localhost:1420"      // Tauri dev
        )
        .AllowAnyMethod()
        .AllowAnyHeader()
        .AllowCredentials();
    });
});

// Habilitar Controllers
builder.Services.AddControllers();

// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Middleware de manejo global de errores (DEBE ir primero)
app.UseGlobalErrorHandler();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

// Habilitar CORS
app.UseCors("AllowFrontend");

// Mapear los controllers
app.MapControllers();

    Log.Information("✅ Auth Service iniciado correctamente en {Environment}", app.Environment.EnvironmentName);
    app.Run();
}
catch (Exception ex)
{
    Log.Fatal(ex, "❌ Error fatal al iniciar Auth Service");
    throw;
}
finally
{
    Log.Information("🛑 Cerrando Auth Service...");
    Log.CloseAndFlush();
}
