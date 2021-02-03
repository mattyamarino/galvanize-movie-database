package moviedb.service;

import moviedb.exception.InvalidReviewException;
import moviedb.exception.MovieNotFoundException;
import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
import moviedb.model.ReviewDto;
import moviedb.model.ReviewEntity;
import moviedb.repository.MovieRepository;
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
        if(reviewDto.getRating() == 0)
            throw new InvalidReviewException("Please resubmit review with star rating");
        ReviewEntity reviewEntity = mapReviewEntity(reviewDto);
        MovieEntity movieToUpdate = movieRepository.findByTitle(movieTitle);
        movieToUpdate.getReviews().add(reviewEntity);
        movieToUpdate.setStarRating(getStarRatingAverage(movieToUpdate.getReviews()));
        movieRepository.save(movieToUpdate);
    }

    private MovieDto mapMovieDto(MovieEntity movieEntity) {
        return MovieDto.builder()
                .actors(movieEntity.getActors())
                .description(movieEntity.getDescription())
                .releaseYear(movieEntity.getReleaseYear())
                .director(movieEntity.getDirector())
                .starRating(movieEntity.getStarRating())
                .title(movieEntity.getTitle())
                .reviews(mapReviewDtos(movieEntity.getReviews()))
                .build();
    }

    private ReviewEntity mapReviewEntity(ReviewDto reviewDto) {
        return new ReviewEntity(reviewDto.getRating(), reviewDto.getDescription());
    }

    private List<ReviewDto> mapReviewDtos(List<ReviewEntity> reviewEntities) {
        if(reviewEntities == null) {
            return new ArrayList<>();
        }

        List<ReviewDto> returnList = new ArrayList<>();

        for(ReviewEntity reviewEntity: reviewEntities) {
            returnList.add(mapReviewDto(reviewEntity));
        }

        return returnList;
    }

    private ReviewDto mapReviewDto(ReviewEntity reviewEntity) {
        return ReviewDto.builder()
                .rating(reviewEntity.getRating())
                .description(reviewEntity.getDescription())
                .build();
    }

    double getStarRatingAverage(List<ReviewEntity> reviews) {
        double totalRating = 0.0;

        for(ReviewEntity review : reviews) {
            totalRating += review.getRating();
        }

        return totalRating / reviews.size();
    }
}
