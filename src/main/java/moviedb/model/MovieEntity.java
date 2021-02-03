package moviedb.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;

    public MovieEntity(String title, String director, String actors, String releaseYear, String description) {
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.releaseYear = releaseYear;
        this.description = description;
        this.starRating = 0;
        this.reviews = new ArrayList<>();
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

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setStarRating(double starRating) {
        this.starRating = starRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieEntity)) return false;
        MovieEntity that = (MovieEntity) o;
        return Double.compare(that.starRating, starRating) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(director, that.director) &&
                Objects.equals(actors, that.actors) &&
                Objects.equals(releaseYear, that.releaseYear) &&
                Objects.equals(description, that.description) &&
                Objects.equals(reviews, that.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, director, actors, releaseYear, description, starRating, reviews);
    }
}
