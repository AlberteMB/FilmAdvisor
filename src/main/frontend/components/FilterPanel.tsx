import { useState } from "react";
import { PanelProps } from "../types/PanelProps";
import genres from "../data/genres.json";
import { Checkbox, FormControlLabel } from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";

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
  const [selectedGenres, setSelectedGenres] = useState<string[]>([]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value, checked } = event.target;
    setSelectedGenres((prev) =>
      checked ? [...prev, value] : prev.filter((genre) => genre !== value)
    );
  };

  return (
    <div>
      <button className="w-full text-left font-semibold" onClick={onToggle}>
        {title}
      </button>
      {isOpen && (
        <div className="mt-2 flex flex-col space-y-1">
          {genres.map((genre) => (
            <FormControlLabel
              key={genre}
              control={
                <Checkbox
                  checked={selectedGenres.includes(genre)}
                  onChange={handleChange}
                  value={genre}
                />
              }
              label={genre}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export { GenrePanel };

const PlatformPanel = ({ title, children, isOpen, onToggle }:
    PanelProps): JSX.Element => {

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

export { PlatformPanel };




