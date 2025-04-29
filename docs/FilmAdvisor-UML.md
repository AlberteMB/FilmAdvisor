classDiagram

class User {

+String id

+String username

+String email

+String password

+enum role

}

class Movie {

+String id

+String title

+Integer releaseYear

+List<String> genre

+String director

+List<String> actors

}

class WatchedMovie {

+String id

+User user

+Movie movie

+Boolean liked

}

class Watchlist {

+String id

+String watchlistName

+User user

}

class DiscardedMovie {

+String id

+User user

+Movie movie

}

class WatchlistEntry{

+String id

+LocalDate addedAt

+String note

+Movie movie

}

%% Relaciones

User "1" -- "many" WatchedMovie : has

User "1" -- "many" WatchlistEntry : has

User "1" -- "many" DiscardedMovie : has

WatchedMovie "many" -- "1" Movie : watched

DiscardedMovie "many" -- "1" Movie : discarded

WatchlistEntry "many" -- "1" Movie: refers to

WatchlistEntry "many" -- "1" Watchlist : has
