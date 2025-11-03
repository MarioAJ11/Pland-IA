// --- Servicio de Autenticación ---
import { hash, compare } from "bcrypt";
import { prisma } from "../services/prisma.services";
import { sign } from "jsonwebtoken";
// 2. Definición de la Clase AuthService
// Usamos una clase para agrupar toda la lógica de autenticación

export class AuthService {

  async register(email: string, password: string) {

    // 1. Validar datos de entrada
    const existingUser = await prisma.user.findUnique({ where: { email } });
    if (existingUser) {
      throw new Error("Email ya registrado");
    }

    // 2. Hashear la contraseña
    const hashedPassword = await hash(password, 10);
    const user = await prisma.user.create({
      data: {
        email,
        password: hashedPassword,
        name: email.split('@')[0],
        avatar: '',
      },
    });
    const { password: _, ...userWithoutPassword } = user;
    return userWithoutPassword;
  }

  // --- Método de Login ---
  // Objetivo: Autenticar a un usuario y devolver un token JWT
  async login(email: string, password: string) {
    // 1. Buscar usuario por email
    const user = await prisma.user.findUnique({
      where: { email }
    });

    // 2. Si no existe, lanzar error
    if (!user) {
      throw new Error('Credenciales inválidas');
    }

    // 3. Comparar la contraseña
    const isValidPassword = await compare(password, user.password);
    if (!isValidPassword) {
      throw new Error('Credenciales inválidas');
    }

    // 4. Generar token JWT
    const token = sign(
      { userId: user.id, email: user.email },
      process.env.JWT_SECRET || 'default_secret_key',
      { expiresIn: '7d' }
    );

    return token;
  }
}

export const authService = new AuthService();