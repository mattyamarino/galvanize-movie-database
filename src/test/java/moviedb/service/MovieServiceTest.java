package moviedb.service;

import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
import moviedb.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        List<MovieDto> expected = List.of(movieDto1, movieDto2, movieDto3);

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDto> result = service.getAllMovies();

        verify(movieRepository, times(1)).findAll();
        assertEquals(expected, result);
    }

    @Test
    public void getMovieByTitle_callsRepositoryAndreturnsMovie(){
        MovieDto expected = new MovieDto("The Avengers", "Fancy Guy",  "first guy, second guy", "2012", "hulk smash", 4);
        MovieEntity movieEntity = new MovieEntity("The Avengers", "Fancy Guy", "first guy, second guy", "2012", "hulk smash", 4);
        when(movieRepository.findByTitle("The Avengers")).thenReturn(movieEntity);

        MovieDto result = service.getMovieByTitle("The Avengers");

        verify(movieRepository, times(1)).findByTitle("The Avengers");
        assertEquals(expected, result);
    }

}