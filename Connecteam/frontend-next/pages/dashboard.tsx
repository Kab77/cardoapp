import { useEffect, useState } from "react";
import axios from "axios";
export default function Dashboard() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [desc, setDesc] = useState("");
  useEffect(() => {
    const fetchTasks = async () => {
      const token = localStorage.getItem("token");
      const res = await axios.get("http://localhost:8080/tasks", {
        headers: { Authorization: `Bearer ${token}` }
      });
      setTasks(res.data);
    };
    fetchTasks();
  }, []);
  const addTask = async (e: React.FormEvent) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    await axios.post("http://localhost:8080/tasks", { title, description: desc }, {
      headers: { Authorization: `Bearer ${token}` }
    });
    window.location.reload();
  };
  return (
    <div className="max-w-lg mx-auto mt-10">
      <h1 className="text-2xl mb-4">Tableau de bord</h1>
      <form onSubmit={addTask} className="flex flex-col gap-2 mb-4">
        <input value={title} onChange={e => setTitle(e.target.value)} placeholder="Titre" className="border p-2" />
        <input value={desc} onChange={e => setDesc(e.target.value)} placeholder="Description" className="border p-2" />
        <button type="submit" className="bg-green-500 text-white p-2">Ajouter</button>
      </form>
      <ul>
        {tasks.map((t: any) => <li key={t.id} className="border-b py-2">{t.title} - {t.description}</li>)}
      </ul>
    </div>
  );
} 