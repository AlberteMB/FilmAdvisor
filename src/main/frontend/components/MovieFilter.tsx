import { useState } from "react";
import Movie from "../models/movie.ts";
import { GenrePanel, YearPanel, PlatformPanel } from "../components/Panel.tsx";


export default function MovieFilter() {
  return (
    <div className="p-4 border bg-gray-100">
      <h2 className="text-lg font-semibold mb-2">Filtro</h2>
      <GenrePanel title="Género">
      Genres
      </GenrePanel>
      <YearPanel title="Año">
      Years
      </YearPanel>
      <PlatformPanel title="Plataformas">
      Platforms
      </PlatformPanel>
    </div>
  );
}