import React, { useState } from "react";

type Props = {
  onAdd: (title: string, description: string) => void;
};

export default function TaskForm({ onAdd }: Props) {
  const [title, setTitle] = useState("");
  const [desc, setDesc] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onAdd(title, desc);
    setTitle("");
    setDesc("");
  };

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-2 mb-4">
      <input value={title} onChange={e => setTitle(e.target.value)} placeholder="Titre" className="border p-2" />
      <input value={desc} onChange={e => setDesc(e.target.value)} placeholder="Description" className="border p-2" />
      <button type="submit" className="bg-green-500 text-white p-2">Ajouter</button>
    </form>
  );
} 