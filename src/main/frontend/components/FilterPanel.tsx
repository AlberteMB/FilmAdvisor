import { useState } from "react";
import { PanelProps } from "../types/PanelProps";
import genres from "../data/genres.json";
import { Checkbox, FormControlLabel } from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";
import { useFilterContext } from "../context/FilterContext";

const YearPanel = ({ title, children, isOpen, onToggle }:
    PanelProps): JSX.Element =>  {

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
};

export { YearPanel };

const GenrePanel = ({ title, isOpen, onToggle }: { title: string; isOpen: boolean; onToggle: () => void }) => {
  //const [selectedGenre, setSelectedGenre] = useState<string>('');
  const { selectedGenres, setSelectedGenres } = useFilterContext();



  const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedGenre(event.target.value);
  };

  return (
    <div>
      <button className="w-full text-left font-semibold" onClick={onToggle}>
        {title}
      </button>
      {isOpen && (
        <div className="mt-2">
          <select
            value={selectedGenre}
            onChange={handleChange}
            className="w-full p-2 border rounded"
          >
            <option value="">Selecciona un género</option>
            {genres.map((genre) => (
              <option key={genre} value={genre}>
                {genre}
              </option>
            ))}
          </select>
        </div>
      )}
    </div>
  );
};
export { GenrePanel };


const PlatformPanel = ({ title, isOpen, onToggle }: { title: string; isOpen: boolean; onToggle: () => void }) => {
    //const [selectedPlatforms, setSelectedPlatforms] = useState<string[]>([]);
    const { selectedPlatforms, setSelectedPlatforms } = useFilterContext();


    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { value, checked } = event.target;
        setSelectedPlatforms((prev) =>
            checked
                ? [...prev, value]
                : prev.filter((platform) => platform !== value)
        );
    };

    // Opciones hardcodeadas
    const platformOptions = ["Netflix", "Prime"];

    return (
        <div>
            <button className="w-full text-left font-semibold" onClick={onToggle}>
                {title}
            </button>
            {isOpen && (
                <div className="mt-2 flex flex-col space-y-1">
                    {platformOptions.map((platform) => (
                        <FormControlLabel
                            key={platform}
                            control={
                                <Checkbox
                                    checked={selectedPlatforms.includes(platform)}
                                    onChange={handleChange}
                                    value={platform}
                                />
                            }
                            label={platform}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};

export { PlatformPanel };

const FilterPanel = ({
    title,
    isOpen,
    onToggle
    }: {
        title: string;
        isOpen: boolean;
        onToggle: () => void
        }) => {
    const [selectedPlatforms, setSelectedPlatforms] = useState<string[]>([]);
    const [selectedGenre, setSelectedGenre] = useState<string>('');

    const platformOptions = ["Netflix", "Prime"];

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { value, checked } = event.target;
        setSelectedPlatforms((prev) =>
            checked
                ? [...prev, value]
                : prev.filter((platform) => platform !== value)
        );
    };

    const handleGenreChange = (event: SelectChangeEvent<string>) => {
        setSelectedGenre(event.target.value);
    };

return(
     <div className="border rounded-md p-2 mb-2">
          <button className="w-full text-left font-semibold" onClick={onToggle}>
            {title}
          </button>
          {isOpen && (
            <div className="mt-2 flex flex-col space-y-4">
              <div className="flex flex-col space-y-1">
                <p className="font-medium">Selecciona plataforma(s):</p>
                {platformOptions.map((platform) => (
                  <FormControlLabel
                    key={platform}
                    control={
                      <Checkbox
                        checked={selectedPlatforms.includes(platform)}
                        onChange={handlePlatformChange}
                        value={platform}
                      />
                    }
                    label={platform}
                  />
                ))}
              </div>

              <div>
                <p className="font-medium">Selecciona un género (opcional):</p>
                <select
                  value={selectedGenre}
                  onChange={handleGenreChange}
                  className="w-full p-2 border rounded"
                >
                  <option value="">Todos los géneros</option>
                  {genres.map((genre) => (
                    <option key={genre} value={genre}>
                      {genre}
                    </option>
                  ))}
                </select>
              </div>
            </div>
          )}
        </div>
      );
    };

export { FilterPanel };




