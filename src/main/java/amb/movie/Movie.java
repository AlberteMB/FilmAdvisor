package amb.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Movie {

    private String platform; // Partition key
    private String movieId; // Sort Key. Title#Genre
    private String title;
    private String director;
    private int year;
    private LocalDate releasedDate;
    private Set<Genre> genres = new HashSet<>(); // Use Set to avoid duplicates
    private List<String> actors = new ArrayList<>();
    private String synopsis;
    private Rating rating;
    private String imdbId; // Use this to generate the link to IMDb
    private double imdbRating;
    private String imageUrl;

    @DynamoDbPartitionKey
    public String getPlatform() {
        return platform;
    }

    @DynamoDbSortKey
    public String getMovieId() {
        return movieId;
    }

    // GSI search by genre
    @DynamoDbSecondaryPartitionKey(indexNames = "GenreIndex")
    public Set<Genre> getGenres() {
        return genres;
    }

    // GSI search by year
    @DynamoDbSecondaryPartitionKey(indexNames = "YearIndex")
    public int getYear() {
        return year;
    }

    @DynamoDbAttribute("Title")
    public String getTitle() {
        return title;
    }

    @DynamoDbAttribute("Director")
    public String getDirector() {
        return director;
    }

    @DynamoDbAttribute("ReleasedDate")
    public LocalDate getReleasedDate() {
        return releasedDate;
    }


    @DynamoDbAttribute("Actors")
    public List<String> getActors() {
        return actors;
    }

    @DynamoDbAttribute("Synopsis")
    public String getSynopsis(){
        return synopsis;
    }

    @DynamoDbAttribute("AgeRating")
    public Rating getAgeRating() {
        return rating;
    }

    @DynamoDbAttribute("ImdbId")
    public String getImdbId() {
        return imdbId;
    }

    @DynamoDbAttribute("ImdbRating")
    public double getImdbRating() {
        return imdbRating;
    }

    @DynamoDbAttribute("ImageUrl")
    public String getImageUrl(){
        return imageUrl;
    }

    public enum Genre {
        ACTION, ADVENTURE, SCI_FI, COMEDY, DRAMA, FANTASY, HORROR, ROMANCE,
        THRILLER, ANIMATION, FAMILY, HISTORICAL, WAR,
        WESTERN, SPORTS, BIOPIC, MYSTERY, MUSICAL, ANIME, LGBT
    }

    public enum Rating {
        G, PG, PG_13, R
    }

    public String generateMovieId() {
        return this.title.toLowerCase().replace(" ", "-") + "#" + this.year;
    }
}
