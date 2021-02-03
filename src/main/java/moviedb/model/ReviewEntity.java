package moviedb.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rating;
    private String description;

    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public ReviewEntity(int rating, String description) {
        this.rating = rating;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewEntity)) return false;
        ReviewEntity that = (ReviewEntity) o;
        return rating == that.rating &&
                Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, description);
    }
}
