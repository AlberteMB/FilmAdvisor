package amb.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Movie {

    private String pk; // Title#Genre
    private String sk; // Platform
    private String title;
    private int year;
    private String releasedDate;
    private List<Genre> genres = new ArrayList<>();
    private String director;
    private Rating rating;
    private List<String> actors = new ArrayList<>();
    private String imdbId; // To generate the link to IMDb
    private String synopsis;
    private String imageUrl;


    @DynamoDbPartitionKey
    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @DynamoDbSortKey
    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "YearIndex")
    public int getYear() {
        return year;
    }

    @DynamoDbSecondarySortKey(indexNames = {"YearIndex", "ReleasedDateIndex"})
    public String getTitle() {
        return title;
    }


    @DynamoDbSecondaryPartitionKey(indexNames = "ReleasedDateIndex")
    public String getReleasedDate() {
        return releasedDate;
    }

    @DynamoDbAttribute("Genres")
    public List<Genre> getGenres() {
        return genres;
    }

    @DynamoDbAttribute("Director")
    public String getDirector() {
        return director;
    }

    @DynamoDbAttribute("Rating")
    public Rating getRating() {
        return rating;
    }

    @DynamoDbAttribute("Actors")
    public List<String> getActors() {
        return actors;
    }

    @DynamoDbAttribute("ImdbId")
    public String getImdbId() {
        return imdbId;
    }

    @DynamoDbAttribute("Synopsis")
    public String getSynopsis(){
        return synopsis;
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
}
