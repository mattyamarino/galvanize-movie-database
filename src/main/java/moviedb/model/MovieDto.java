package moviedb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private String title;
    private String director;
    private String actors;
    private String releaseYear;
    private String description;
    private double starRating;
    private List<ReviewDto> reviews;
}
