import { useState } from "react";
import axios from "axios";
export default function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await axios.post("http://localhost:8080/auth/register", { username, password });
    alert("Inscription réussie !");
  };
  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-2 max-w-xs mx-auto mt-10">
      <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Nom d'utilisateur" className="border p-2" />
      <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Mot de passe" className="border p-2" />
      <button type="submit" className="bg-blue-500 text-white p-2">S'inscrire</button>
    </form>
  );
} 