import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

// Rutas que requieren autenticación
const protectedRoutes = ['/dashboard', '/projects', '/calendar', '/expenses', '/meals'];

// Rutas que solo pueden acceder usuarios NO autenticados
const authRoutes = ['/login', '/register'];

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;
  
  // Obtener token de las cookies
  const token = request.cookies.get('accessToken')?.value;
  const isAuthenticated = !!token;

  // Si intenta acceder a una ruta protegida sin estar autenticado
  if (protectedRoutes.some(route => pathname.startsWith(route)) && !isAuthenticated) {
    const loginUrl = new URL('/login', request.url);
    loginUrl.searchParams.set('redirect', pathname);
    return NextResponse.redirect(loginUrl);
  }

  // Si intenta acceder a login/register estando autenticado
  if (authRoutes.some(route => pathname.startsWith(route)) && isAuthenticated) {
    return NextResponse.redirect(new URL('/dashboard', request.url));
  }

  return NextResponse.next();
}

// Configurar en qué rutas se ejecuta el middleware
export const config = {
  matcher: [
    '/dashboard/:path*',
    '/projects/:path*',
    '/calendar/:path*',
    '/expenses/:path*',
    '/meals/:path*',
    '/login',
    '/register',
  ],
};
