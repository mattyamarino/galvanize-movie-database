package moviedb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
import moviedb.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        movieDto1 = new MovieDto("The Avengers", "Fancy Guy", actors, "2012", "hulk smash", 4, null);
        movieDto2 = new MovieDto("That Other Movie", "Less Fancy Guy", actors, "2019", "its a movie", 5, null);
        movieDto3 = new MovieDto("Back to the Future", "Forgot the guy", actors, "1985", "it has a flying car", 4, null);
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


}