package moviedb.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String director;
    private String actors;
    private String releaseYear;
    private String description;
    private double starRating;

    public MovieEntity(String title, String director, String actors, String releaseYear, String description, double starRating) {
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.releaseYear = releaseYear;
        this.description = description;
        this.starRating = starRating;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public double getStarRating() {
        return starRating;
    }
}
