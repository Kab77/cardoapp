import { useState } from "react";
import axios from "axios";
import { useRouter } from "next/router";
export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const res = await axios.post("http://localhost:8080/auth/login", { username, password });
    localStorage.setItem("token", res.data.token);
    router.push("/dashboard");
  };
  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-2 max-w-xs mx-auto mt-10">
      <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Nom d'utilisateur" className="border p-2" />
      <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Mot de passe" className="border p-2" />
      <button type="submit" className="bg-blue-500 text-white p-2">Se connecter</button>
    </form>
  );
} 