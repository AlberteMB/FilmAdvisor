import json
import random
from datetime import date

platforms = ["Netflix", "Prime", "Max"]
directors = ["Christopher Nolan", "Greta Gerwig", "Quentin Tarantino", "Martin Scorsese", "Patty Jenkins", "Denis Villeneuve"]
actors_pool = ["Leonardo DiCaprio", "Emma Stone", "Brad Pitt", "Margot Robbie", "Denzel Washington", "Scarlett Johansson",
               "Tom Hardy", "Florence Pugh", "Christian Bale", "Zendaya", "Timothée Chalamet", "Ryan Gosling"]
genres_pool = ["Action", "Drama", "Comedy", "Sci-Fi", "Thriller", "Romance", "Fantasy", "Horror", "Mystery", "Adventure"]
synopses = [
    "A gripping tale of love and betrayal set in a dystopian future.",
    "An unlikely hero rises against all odds to save their homeland.",
    "A detective races against time to solve a mysterious case.",
    "Two lovers find each other amidst the chaos of war.",
    "A mind-bending journey through alternate realities.",
    "A coming-of-age story with a supernatural twist.",
]
ratings = ["G", "PG", "PG-13", "R", "NC-17"]

def generate_movie(i):
    title = f"Movie Title {i+1}"
    year = random.randint(1990, 2024)
    duration = random.randint(80, 180)
    release_date = date(year, random.randint(1, 12), random.randint(1, 28))
    genres = random.sample(genres_pool, k=random.randint(1, 3))
    actors = random.sample(actors_pool, k=random.randint(2, 5))
    imdb_id = f"tt{random.randint(1000000, 9999999)}"
    imdb_rating = round(random.uniform(5.0, 9.5), 1)
    image_url = f"https://example.com/images/movie{i+1}.jpg"
    available_platforms = random.sample(platforms, k=random.randint(1, 3))
    main_platform = available_platforms[0]
    movie_id = f"{title}#{genres[0]}"

    return {
        "platform": main_platform,
        "movieId": movie_id,
        "title": title,
        "director": random.choice(directors),
        "year": year,
        "duration": duration,
        "releasedDate": release_date.isoformat(),
        "genres": genres,
        "actors": actors,
        "synopsis": random.choice(synopses),
        "rating": random.choice(ratings),
        "imdbId": imdb_id,
        "imdbRating": imdb_rating,
        "imageUrl": image_url,
        "platforms": available_platforms
    }

movies = [generate_movie(i) for i in range(100)]

with open("movies_sample_data.json", "w") as f:
    json.dump(movies, f, indent=2)

print("✅ Archivo 'movies_sample_data.json' generado correctamente.")

