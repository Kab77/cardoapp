import React from "react";

type Task = {
  id: number;
  title: string;
  description: string;
};

type Props = {
  tasks: Task[];
};

export default function TaskList({ tasks }: Props) {
  return (
    <ul>
      {tasks.map((t) => (
        <li key={t.id} className="border-b py-2">
          {t.title} - {t.description}
        </li>
      ))}
    </ul>
  );
} 