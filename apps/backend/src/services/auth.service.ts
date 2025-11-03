// --- Servicio de Autenticación ---
// Este archivo maneja la lógica de negocio para el registro y login.

import { hash } from "bcrypt";
import { prisma } from "./prisma.services";
import { sign } from "jsonwebtoken";



// 2. Definición de la Clase AuthService
// Usamos una clase para agrupar toda la lógica de autenticación
export class AuthService {

  // --- Método de Registro ---
  // Objetivo: Crear un nuevo usuario de forma segura
    // Modelo de Usuario (Prisma Schema)
  // model User {
    //   id        String   @id @default(cuid())
    //   email     String   @unique
    //   name      String   @db.VarChar(255)
    //   avatar    String   @db.VarChar(255)
    //   password  String   @db.VarChar(255)
    //   createdAt DateTime @default(now())
    //   updatedAt DateTime @updatedAt
    // }
  //
  // 1. Recibe 'email' y 'password'
  // 2. (Importante) Comprueba si un usuario con ese email ya existe en la BD.
  //    - Si existe, lanza un error (ej: "Email ya registrado")
  // 3. Hashea la contraseña usando bcrypt (ej: con 10 rondas de salt)
  // 4. Crea el nuevo usuario en la base de datos usando prisma.user.create
  //    - Guarda el 'email' y la 'passwordHasheada'
  // 5. (Seguridad) NUNCA devuelvas la contraseña, ni siquiera la hasheada.
  //    - Devuelve el objeto 'user' sin el campo 'password'.
  async register(email: string, password: string) {
    // Comprobar si el email ya está registrado
    const existingUser = await prisma.user.findUnique({ where: { email } });
    if (existingUser) {
      throw new Error("Email ya registrado");
    }
    // Hashear la contraseña
    const hashedPassword = await hash(password, 10);
    const user = await prisma.user.create({
      data: {
        email,
        password: hashedPassword,
        name: email.split('@')[0], // Usar el nombre de usuario del email como nombre por defecto
        avatar: '', // Avatar vacío por defecto
      },
    });
    // No devolver la contraseña
    const { password: _, ...userWithoutPassword } = user;
    return userWithoutPassword;
  }

  // TODO: Método de Login (lo haremos después)
  // async login(email: string, password: string) {
  //   // 1. Encontrar al usuario por email
  //   // 2. Comparar la contraseña con bcrypt.compare
  //   // 3. Si es correcto, generar un JWT
  //   // 4. Devolver el token
  // }
}

// 3. Exportar una instancia única del servicio
// (Singleton pattern) para que toda la app use la misma instancia
export const authService = new AuthService();