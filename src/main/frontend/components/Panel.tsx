import { useState } from "react";
import { PanelProps } from "../types/PanelProps";

export function Panel({ title, children }: PanelProps) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="border rounded-md p-2 mb-2">
      <button
        className="w-full text-left font-semibold"
        onClick={() => setIsOpen(!isOpen)}
      >
        {title}
      </button>
      {isOpen && <div className="mt-2">{children}</div>}
    </div>
  );
}

export function YearPanel({ title, children }: PanelProps) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="border rounded-md p-2 mb-2">
      <button
        className="w-full text-left font-semibold"
        onClick={() => setIsOpen(!isOpen)}
      >
        {title}
      </button>
      {isOpen && <div className="mt-2">{children}</div>}
    </div>
  );
}

export function GenrePanel({ title, children }: PanelProps) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="border rounded-md p-2 mb-2">
      <button
        className="w-full text-left font-semibold"
        onClick={() => setIsOpen(!isOpen)}
      >
        {title}
      </button>
      {isOpen && <div className="mt-2">{children}</div>}
    </div>
  );
}

export function PlatformPanel({ title, children }: PanelProps) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="border rounded-md p-2 mb-2">
      <button
        className="w-full text-left font-semibold"
        onClick={() => setIsOpen(!isOpen)}
      >
        {title}
      </button>
      {isOpen && <div className="mt-2">{children}</div>}
    </div>
  );
}


