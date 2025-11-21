import type { Metadata } from "next";
import { Inter, VT323 } from "next/font/google";
import "./globals.css";
import { Providers } from "./providers";
import LoadingScreen from "@/components/LoadingScreen";

const inter = Inter({ 
  subsets: ["latin"],
  variable: '--font-inter',
});

const vt323 = VT323({ 
  weight: '400',
  subsets: ["latin"],
  variable: '--font-vt323',
});

export const metadata: Metadata = {
  title: "BatPlan - Organiza tu vida con inteligencia",
  description: "Sistema completo para gestionar proyectos, tareas, gastos y salud con IA",
  icons: {
    icon: '/batplan-icon.png',
    shortcut: '/batplan-icon.png',
    apple: '/batplan-icon.png',
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="es" suppressHydrationWarning>
      <body className={`${inter.variable} ${vt323.variable} font-sans`} suppressHydrationWarning>
        <LoadingScreen />
        <Providers>{children}</Providers>
      </body>
    </html>
  );
}
