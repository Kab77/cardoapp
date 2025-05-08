import Link from "next/link";
export default function Home() {
  return (
    <div className="flex flex-col items-center mt-20 gap-4">
      <h1 className="text-3xl">Bienvenue sur l'application de gestion de tâches</h1>
      <Link href="/register" className="text-blue-500 underline">S'inscrire</Link>
      <Link href="/login" className="text-blue-500 underline">Se connecter</Link>
    </div>
  );
} 