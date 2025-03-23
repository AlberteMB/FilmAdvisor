import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import MovieCard from "../components/MovieCard.jsx";
import MovieRandomizer from "../components/MovieRandomizer.jsx";

export const config: ViewConfig = { menu: { order: 0, icon: 'line-awesome/svg/file.svg' }, title: 'Main' };

export default function MainView() {
  return (
    <div className="p-4" style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
      <h1 className="text-2xl">Random Movies</h1>
      <MovieRandomizer />
    </div>
  );
}
