export const genreList = [
  "ACTION",
  "COMEDY",
  "DRAMA",
  "HORROR",
  "SCI_FI",
  "ROMANCE",
  "THRILLER",
  "ANIMATION"
] as const;

export type Genre = (typeof genreList)[number];