import { useState } from "react";
import { PanelProps } from "../types/PanelProps";

export function YearPanel({ title, children, isOpen, onToggle }: PanelProps) {

  return (
    <div className="border rounded-md p-2 mb-2">
      <button
        className="w-full text-left font-semibold"
        onClick={onToggle}
      >
        {title}
      </button>
      {isOpen && <div className="mt-2">{children}</div>}
    </div>
  );
}

export function GenrePanel({ title, children, isOpen, onToggle }: PanelProps) {

  return (
    <div className="border rounded-md p-2 mb-2">
      <button
        className="w-full text-left font-semibold"
        onClick={onToggle}
      >
        {title}
      </button>
      {isOpen && <div className="mt-2">{children}</div>}
    </div>
  );
}

export function PlatformPanel({ title, children, isOpen, onToggle }: PanelProps) {

  return (
    <div className="border rounded-md p-2 mb-2">
      <button
        className="w-full text-left font-semibold"
        onClick={onToggle}
      >
        {title}
      </button>
      {isOpen && <div className="mt-2">{children}</div>}
    </div>
  );
}




