import { createContext, useContext, useState, ReactNode } from "react";

type FilterState = {
  selectedGenre: string;
  selectedPlatforms: string[];
  setSelectedGenre: (genre: string) => void;
  setSelectedPlatforms: (platforms: string[]) => void;
};

const FilterContext = createContext<FilterState | undefined>(undefined);

export const FilterProvider = ({ children }: { children: ReactNode }) => {
  const [selectedGenre, setSelectedGenre] = useState<string>();
  const [selectedPlatforms, setSelectedPlatforms] = useState<string[]>([]);

  return (
    <FilterContext.Provider
      value={{ selectedGenre, selectedPlatforms, setSelectedGenre, setSelectedPlatforms }}
    >
      {children}
    </FilterContext.Provider>
  );
};

export const useFilterContext = () => {
  const context = useContext(FilterContext);
  if (!context) {
    throw new Error("useFilterContext must be used within a FilterProvider");
  }
  return context;
};
