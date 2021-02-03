package moviedb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
import moviedb.model.ReviewDto;
import moviedb.model.ReviewEntity;
import moviedb.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    MovieDto movieDto1, movieDto2, movieDto3;
    MovieEntity movieEntity1, movieEntity2, movieEntity3;
    @BeforeEach
    public void setUp(){
        String actors = "first guy, second guy";
        movieDto1 = new MovieDto("The Avengers", "Fancy Guy", actors, "2012", "hulk smash", 0, new ArrayList<>());
        movieDto2 = new MovieDto("That Other Movie", "Less Fancy Guy", actors, "2019", "its a movie", 0, new ArrayList<>());
        movieDto3 = new MovieDto("Back to the Future", "Forgot the guy", actors, "1985", "it has a flying car", 0, new ArrayList<>());
        movieEntity1 = new MovieEntity("The Avengers", "Fancy Guy", actors, "2012", "hulk smash");
        movieEntity2 = new MovieEntity("That Other Movie", "Less Fancy Guy", actors, "2019", "its a movie");
        movieEntity3 = new MovieEntity("Back to the Future", "Forgot the guy", actors, "1985", "it has a flying car");
    }

    @Test
    public void getAllMovies() throws Exception {
        List<MovieDto> expected = List.of(movieDto1, movieDto2, movieDto3);
        String expectedString = objectMapper.writeValueAsString(expected);

        movieRepository.save(movieEntity1);
        movieRepository.save(movieEntity2);
        movieRepository.save(movieEntity3);

        mockMvc.perform(get("/movies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedString));
    }

    @Test
    public void getMovieByTitle_callsRepositoryAndReturnsMovie() throws Exception {
        movieRepository.save(movieEntity1);
        movieRepository.save(movieEntity2);
        String expected = objectMapper.writeValueAsString(movieDto1);

        mockMvc.perform(get("/movies/"+movieDto1.getTitle())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void getMovieByTitle_ThrowsMovieNotFoundException() throws Exception {

        mockMvc.perform(get("/movies/"+movieDto1.getTitle())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void addReviewToMovie_withoutExistingReview() throws Exception {
        movieRepository.save(movieEntity2);
        ReviewDto reviewDto = new ReviewDto(4, "awesome");
        ReviewEntity reviewEntity = new ReviewEntity(4, "awesome");
        String reviewString = objectMapper.writeValueAsString(reviewDto);

        mockMvc.perform(patch("/movies/" + movieEntity2.getTitle())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewString))
                .andExpect(status().isOk());

        MovieEntity result = movieRepository.findByTitle(movieEntity2.getTitle());
        assertEquals(reviewEntity.getRating(), result.getReviews().get(0).getRating());
        assertEquals(reviewEntity.getDescription(), result.getReviews().get(0).getDescription());
        assertEquals(4.0, result.getStarRating());
    }


    @Test
    public void addReviewToMovie_withExistingReview() throws Exception {
        ReviewEntity existingReview = new ReviewEntity(3, "its ok");
        movieEntity2.getReviews().add(existingReview);
        String title = movieEntity2.getTitle();
        movieRepository.save(movieEntity2);
        ReviewDto reviewDto = new ReviewDto(4, "awesome");
        ReviewEntity reviewEntity = new ReviewEntity(4, "awesome");
        String reviewString = objectMapper.writeValueAsString(reviewDto);

        mockMvc.perform(patch("/movies/" + title)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewString))
                .andExpect(status().isOk());

        MovieEntity result = movieRepository.findByTitle(title);
        assertEquals(title, result.getTitle());
        assertEquals(reviewEntity.getRating(), result.getReviews().get(1).getRating());
        assertEquals(reviewEntity.getDescription(), result.getReviews().get(1).getDescription());
        assertEquals(3.5, result.getStarRating());
    }

    @Test
    public void getMovieByTitle_callsRepositoryAndReturnsMovieWithRating() throws Exception {
        ReviewDto reviewDto1 = new ReviewDto(2, "bad movie");
        ReviewDto reviewDto2 = new ReviewDto(3, "ok movie");
        ReviewDto reviewDto3 = new ReviewDto(4, "pretty good movie");

        ReviewEntity reviewEntity1 = new ReviewEntity(2, "bad movie");
        ReviewEntity reviewEntity2 = new ReviewEntity(3, "ok movie");
        ReviewEntity reviewEntity3 = new ReviewEntity(4, "pretty good movie");

        movieEntity1.getReviews().add(reviewEntity1);
        movieEntity1.getReviews().add(reviewEntity2);
        movieEntity1.getReviews().add(reviewEntity3);

        movieDto1.getReviews().add(reviewDto1);
        movieDto1.getReviews().add(reviewDto2);
        movieDto1.getReviews().add(reviewDto3);

        movieDto1.setStarRating(3);
        movieEntity1.setStarRating(3);

        movieRepository.save(movieEntity1);
        movieRepository.save(movieEntity2);

        String expected = objectMapper.writeValueAsString(movieDto1);

        mockMvc.perform(get("/movies/"+movieDto1.getTitle())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void addReviewToMovie_Bad_request() throws Exception {
        ReviewDto reviewDto = new ReviewDto(0, "awesome");
        String reviewString = objectMapper.writeValueAsString(reviewDto);

        mockMvc.perform(patch("/movies/" + movieEntity2.getTitle())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewString))
                .andExpect(status().isBadRequest());

    }
}