package amb.user;

import amb.userMovieStatus.UserMovieStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }

    //@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //private UserPreferences preferences;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<UserMovieStatus> watchedMovies = new ArrayList<>();;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Watchlist watchlist;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<DiscardedMovie> discardedMovies = new ArrayList<>();;


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
