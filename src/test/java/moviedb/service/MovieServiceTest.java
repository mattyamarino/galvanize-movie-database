package moviedb.service;

import moviedb.exception.InvalidReviewException;
import moviedb.exception.MovieNotFoundException;
import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
import moviedb.model.ReviewDto;
import moviedb.model.ReviewEntity;
import moviedb.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService service;

    @Test
    public void getAllMovies_callsFindAllFromRepository() {
        MovieEntity movie1 = new MovieEntity();
        MovieEntity movie2 = new MovieEntity();
        MovieEntity movie3 = new MovieEntity();
        List<MovieEntity> movies = List.of(movie1, movie2, movie3);

        MovieDto movieDto1 = new MovieDto();
        MovieDto movieDto2 = new MovieDto();
        MovieDto movieDto3 = new MovieDto();
        movieDto1.setReviews(new ArrayList<>());
        movieDto2.setReviews(new ArrayList<>());
        movieDto3.setReviews(new ArrayList<>());
        List<MovieDto> expected = List.of(movieDto1, movieDto2, movieDto3);

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDto> result = service.getAllMovies();

        verify(movieRepository, times(1)).findAll();
        assertEquals(expected, result);
    }

    @Test
    public void getMovieByTitle_callsRepositoryAndReturnsMovie(){
        MovieDto expected = new MovieDto("The Avengers", "Fancy Guy",  "first guy, second guy", "2012", "hulk smash", 0, new ArrayList<>());
        MovieEntity movieEntity = new MovieEntity("The Avengers", "Fancy Guy", "first guy, second guy", "2012", "hulk smash");
        when(movieRepository.findByTitle("The Avengers")).thenReturn(movieEntity);

        MovieDto result = service.getMovieByTitle("The Avengers");

        verify(movieRepository, times(1)).findByTitle("The Avengers");
        assertEquals(expected, result);
    }

    @Test
    public void getMovieByTitle_callsRepositoryAndThrowsException(){
        when(movieRepository.findByTitle("The Avengers")).thenReturn(null);

        assertThrows(MovieNotFoundException.class, ()->service.getMovieByTitle("The Avengers"), "We are Sorry! Movie not found");
        verify(movieRepository, times(1)).findByTitle("The Avengers");

    }

    @Test
    public void addReviewToMovie_callsGetMovieByTitle_updatesStarRatingWithNoPriorReviews_andSavesReview() {
        ReviewDto reviewDto = new ReviewDto(4, "I liked the guy with the bow and arrow");
        ReviewEntity reviewEntity = new ReviewEntity(4, "I liked the guy with the bow and arrow");
        MovieEntity movieEntity = new MovieEntity("The Avengers", "Fancy Guy", "first guy, second guy", "2012", "hulk smash");

        MovieEntity updatedEntity = new MovieEntity("The Avengers", "Fancy Guy", "first guy, second guy", "2012", "hulk smash");
        updatedEntity.getReviews().add(reviewEntity);
        updatedEntity.setStarRating(4);


        when(movieRepository.findByTitle("The Avengers")).thenReturn(movieEntity);
        when(movieRepository.save(updatedEntity)).thenReturn(updatedEntity);

        service.addReviewToMovie("The Avengers", reviewDto);

        verify(movieRepository, times(1)).findByTitle("The Avengers");
        verify(movieRepository, times(1)).save(updatedEntity);
        assertEquals(updatedEntity, movieEntity);
    }

    @Test
    public void addReviewToMovie_throwsExceptionWhenNoRatingIsProvided(){
        ReviewDto reviewDto = new ReviewDto(0, "I liked the guy with the bow and arrow");
        assertThrows(InvalidReviewException.class, ()->service.addReviewToMovie("The Avengers", reviewDto), "Please resubmit review with valid star rating");
    }

    @Test
    public void addReviewToMovie_throwsExceptionWhenGivenRatingBelow0(){
        ReviewDto reviewDto = new ReviewDto(-1, "I liked the guy with the bow and arrow");
        assertThrows(InvalidReviewException.class, ()->service.addReviewToMovie("The Avengers", reviewDto), "Please resubmit review with valid star rating");
    }

    @Test
    public void addReviewToMovie_throwsExceptionWhenGivenRatingAbove5(){
        ReviewDto reviewDto = new ReviewDto(6, "I liked the guy with the bow and arrow");
        assertThrows(InvalidReviewException.class, ()->service.addReviewToMovie("The Avengers", reviewDto), "Please resubmit review with valid star rating");
    }

    @Test
    public void getStarRatingAverage_averagesTwoRatings() {
        ReviewEntity review1 = new ReviewEntity(3, "some description");
        ReviewEntity review2 = new ReviewEntity(4, "some description");
        assertEquals(3.5, service.getStarRatingAverage(List.of(review1, review2)));
    }


}