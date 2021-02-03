package moviedb.service;

import moviedb.exception.MovieNotFoundException;
import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
import moviedb.model.ReviewDto;
import moviedb.model.ReviewEntity;
import moviedb.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;

    }

    public List<MovieDto> getAllMovies() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        List<MovieDto> returnList = new ArrayList<>();
        movieEntities.forEach(movieEntity -> {
            returnList.add(mapMovieDto(movieEntity));
        });

        return returnList;
    }

    public MovieDto getMovieByTitle(String title) {
        MovieEntity movieEntity = movieRepository.findByTitle(title);
        if(movieEntity == null)
            throw new MovieNotFoundException("We are Sorry! Movie not found");

        return mapMovieDto(movieEntity);
    }

    public void addReviewToMovie(String movieTitle, ReviewDto reviewDto) {
        ReviewEntity reviewEntity = mapReviewEntity(reviewDto);
        MovieEntity movieToUpdate = movieRepository.findByTitle(movieTitle);
        movieToUpdate.getReviews().add(reviewEntity);
        movieRepository.save(movieToUpdate);
    }

    ReviewEntity mapReviewEntity(ReviewDto reviewDto) {
        return new ReviewEntity(reviewDto.getRating(), reviewDto.getDescription());
    }

    MovieDto mapMovieDto(MovieEntity movieEntity) {
        return MovieDto.builder()
                .actors(movieEntity.getActors())
                .description(movieEntity.getDescription())
                .releaseYear(movieEntity.getReleaseYear())
                .director(movieEntity.getDirector())
                .starRating(movieEntity.getStarRating())
                .title(movieEntity.getTitle())
                .build();
    }
}
