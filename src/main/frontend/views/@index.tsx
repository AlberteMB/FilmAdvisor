import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import MovieCard from "../components/MovieCard";
import MovieSelector from "../components/MovieSelector";
import { useAuth } from "react-oidc-context";

export const config: ViewConfig = { menu: { order: 0, icon: 'line-awesome/svg/file.svg' }, title: 'Main' };

export default function MainView() {
  const auth = useAuth();

  if (auth.isLoading) return <div>Cargando autenticación...</div>;
  if (auth.error) return <div>Error de autenticación: {auth.error.message}</div>;

  return (
    <div className="p-4" style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
      <h1 className="text-2xl">Random Movies</h1>
      <MovieSelector />
    </div>
  );
}
