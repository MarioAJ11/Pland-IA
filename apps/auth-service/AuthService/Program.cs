using Microsoft.EntityFrameworkCore;
using AuthService.Data;
using AuthService.Services;
using AuthService.Middleware;
using Serilog;
using DotNetEnv; // Para cargar variables de entorno desde .env

// 🔐 CARGAR VARIABLES DE ENTORNO desde archivo .env (buscar en directorio padre)
var envPath = Path.Combine(Directory.GetCurrentDirectory(), "..", ".env");
if (File.Exists(envPath))
{
    Env.Load(envPath);
    Log.Logger = new LoggerConfiguration().WriteTo.Console().CreateLogger();
    Log.Information("📁 Variables de entorno cargadas desde {Path}", envPath);
}
else
{
    Log.Logger = new LoggerConfiguration().WriteTo.Console().CreateLogger();
    Log.Warning("⚠️ Archivo .env no encontrado en {Path}, usando valores por defecto", envPath);
}

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
        path: Environment.GetEnvironmentVariable("LOG_PATH") ?? "logs/auth-service-.log",
        rollingInterval: RollingInterval.Day,
        outputTemplate: "{Timestamp:yyyy-MM-dd HH:mm:ss.fff zzz} [{Level:u3}] {SourceContext} - {Message:lj}{NewLine}{Exception}",
        retainedFileCountLimit: int.Parse(Environment.GetEnvironmentVariable("LOG_RETENTION_DAYS") ?? "30")
    )
    .CreateLogger();

try
{
    Log.Information("🚀 Iniciando Auth Service...");

var builder = WebApplication.CreateBuilder(args);

// Reemplazar el logging por defecto con Serilog
builder.Host.UseSerilog();

// 🗄️ Configurar DbContext con PostgreSQL usando variables de entorno
builder.Services.AddDbContext<AppDbContext>(options =>
{
    // Construir connection string desde variables de entorno
    var dbHost = Environment.GetEnvironmentVariable("DB_HOST") ?? "localhost";
    var dbPort = Environment.GetEnvironmentVariable("DB_PORT") ?? "5432";
    var dbName = Environment.GetEnvironmentVariable("DB_NAME") ?? "plandiadb";
    var dbUser = Environment.GetEnvironmentVariable("DB_USER") ?? "postgres";
    var dbPassword = Environment.GetEnvironmentVariable("DB_PASSWORD") ?? throw new InvalidOperationException("DB_PASSWORD no configurada");
    var dbSchema = Environment.GetEnvironmentVariable("DB_SCHEMA") ?? "auth_schema";
    
    var connectionString = $"Host={dbHost};Port={dbPort};Database={dbName};Username={dbUser};Password={dbPassword};SearchPath={dbSchema}";
    
    Log.Information("📊 Conectando a PostgreSQL: {Host}:{Port}/{Database} (schema: {Schema})", dbHost, dbPort, dbName, dbSchema);
    options.UseNpgsql(connectionString);
});

// Registrar AuthService para inyección de dependencias
builder.Services.AddScoped<IAuthService, AuthService.Services.AuthService>();

// 🌐 Configurar CORS dinámicamente desde variables de entorno
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowFrontend", policy =>
    {
        var allowedOrigins = Environment.GetEnvironmentVariable("CORS_ALLOWED_ORIGINS")
            ?.Split(',', StringSplitOptions.RemoveEmptyEntries)
            ?? new[] { "http://localhost:3000", "http://localhost:5173", "https://localhost:1420" };
            
        Log.Information("🔓 CORS configurado para: {Origins}", string.Join(", ", allowedOrigins));
        
        policy.WithOrigins(allowedOrigins)
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

// Health check endpoint
app.MapGet("/health", () => Results.Ok(new { status = "healthy", service = "auth-service" }));

// Mapear los controllers
app.MapControllers();

    Log.Information("✅ Auth Service iniciado correctamente en {Environment}", app.Environment.EnvironmentName);
    Log.Information("🌐 Escuchando en: {Urls}", app.Urls.FirstOrDefault() ?? "http://localhost:5012");
    
    app.Run();
    
    Log.Information("📴 app.Run() ha finalizado - el servidor se ha detenido");
}
catch (Exception ex)
{
    Log.Fatal(ex, "❌ Error fatal al iniciar Auth Service");
    Log.Fatal("📍 StackTrace: {StackTrace}", ex.StackTrace);
    throw;
}
finally
{
    Log.Information("🛑 Cerrando Auth Service...");
    Log.CloseAndFlush();
}
