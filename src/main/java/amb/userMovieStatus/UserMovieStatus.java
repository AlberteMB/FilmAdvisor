package amb.userMovieStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class UserMovieStatus {

    private String pk; // USER#<userId>
    private String sk; // MOVIE#<movieId>
    private String userId;
    private String movieId; // Movie#Year
    private String title;
    private int year;
    private Status status; // WATCHED or DISCARDED
    private Instant timestamp;
    private Rating rating; // LIKE, DISLIKE or null

    public enum Status {
        WATCHED,
        DISCARDED
    }

    public enum Rating {
        LIKE,
        DISLIKE
    }

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

    @DynamoDbAttribute("UserId")
    public String getUserId() {
        return userId;
    }

    @DynamoDbAttribute("MovieId")
    public String getMovieId() {
        return movieId;
    }

    @DynamoDbAttribute("Title")
    public String getTitle(){
        return title;
    }

    @DynamoDbAttribute("Year")
    public int getYear() {
        return year;
    }

    @DynamoDbAttribute("Status")
    public Status getStatus() {
        return status;
    }

    @DynamoDbAttribute("Timestamp")
    public Instant getTimestamp() {
        return timestamp;
    }

    @DynamoDbAttribute("Rating")
    public Rating getRating() {
        return rating;
    }
}

