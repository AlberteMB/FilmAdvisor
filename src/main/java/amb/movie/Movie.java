package amb.movie;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDate;
import java.util.*;

@DynamoDbBean
public class Movie {

    private UUID id;
    private String platform; // Partition key
    private String movieId; // Sort Key. Title#Genre
    private String title;
    private String director;
    private int year;
    private int duration;
    private LocalDate releasedDate;
    private Genre genre;
    private List<String> actors = new ArrayList<>();
    private String synopsis;
    private Rating rating;
    private String imdbId; // Use this to generate the link to IMDb
    private double imdbRating;
    private String imageUrl;
    private List<String> platforms = new ArrayList<>();

    @DynamoDbPartitionKey
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {        this.platform = platform;   }

    @DynamoDbSortKey
    public String getMovieId() {
        return movieId;
    }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    // GSI search by genre
    @DynamoDbAttribute("Genre")
    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genres) { this.genre = genres; }

    // GSI search by year
    @DynamoDbAttribute("Year")
    public int getYear() {
        return year;
    }
    public void setYear(int year) { this.year = year; }

    @DynamoDbAttribute("Id")
    public UUID getId() {        return id; }
    public void setId(UUID id) { this.id = id; }

    @DynamoDbAttribute("Duration")
    public int getDuration() {        return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    @DynamoDbAttribute("Title")
    public String getTitle() {        return title; }
    public void setTitle(String title) { this.title = title; }

    @DynamoDbAttribute("Director")
    public String getDirector() {return director;}
    public void setDirector(String director) { this.director = director; }

    @DynamoDbAttribute("ReleasedDate")
    public LocalDate getReleasedDate() {return releasedDate; }
    public void setReleasedDate(LocalDate releasedDate) { this.releasedDate = releasedDate; }


    @DynamoDbAttribute("Actors")
    public List<String> getActors() {return actors; }
    public void setActors(List<String> actors) { this.actors = actors; }

    @DynamoDbAttribute("Synopsis")
    public String getSynopsis(){return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    @DynamoDbAttribute("AgeRating")
    public Rating getAgeRating() {return rating; }
    public void setRating(Rating rating) { this.rating = rating; }

    @DynamoDbAttribute("ImdbId")
    public String getImdbId() {return imdbId; }
    public void setImdbId(String imdbId) { this.imdbId = imdbId; }

    @DynamoDbAttribute("ImdbRating")
    public double getImdbRating() {return imdbRating; }
    public void setImdbRating(double imdbRating) { this.imdbRating = imdbRating; }

    @DynamoDbAttribute("ImageUrl")
    public String getImageUrl(){return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @DynamoDbAttribute("Platforms")
    public List<String> getPlatforms() {return platforms; }
    public void setPlatforms(List<String> platforms) { this.platforms = platforms; }

    public enum Rating {
        G, PG, PG_13, R
    }

    public String generateMovieId() {
        return this.title.toLowerCase().replace(" ", "-") + "#" + this.year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
