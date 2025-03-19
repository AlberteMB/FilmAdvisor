import { useState } from "react";
import { GenrePanel, YearPanel, PlatformPanel } from "../components/FilterPanel";

export default function MovieFilter() {
  const [openPanel, setOpenPanel] = useState<string | null>(null);

  // Function to manage panel visibility
  const handleToggle = (panelId: string) => {
    setOpenPanel((prev) => (prev === panelId ? null : panelId));
  };

  return (
    <div className="p-4 border bg-gray-100">
      <h2 className="text-lg font-semibold mb-2">Filtro</h2>

      <PlatformPanel
        title="Plataformas"
        isOpen={openPanel === "platformPanel"}
        onToggle={() => handleToggle("platformPanel")}
      >
        Platforms
      </PlatformPanel>

      <GenrePanel
        title="Género"
        isOpen={openPanel === "genrePanel"}
        onToggle={() => handleToggle("genrePanel")}
      >
        Genres
      </GenrePanel>

      <YearPanel
        title="Año"
        isOpen={openPanel === "yearPanel"}
        onToggle={() => handleToggle("yearPanel")}
      >
        Years
      </YearPanel>
    </div>
  );
}