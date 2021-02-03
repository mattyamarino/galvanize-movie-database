package moviedb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
import moviedb.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import javax.transaction.Transactional;

import java.util.ArrayList;
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

    @Test
    public void getAllMovies() throws Exception {
        String actors = "first guy, second guy";
        MovieDto movieDto1 = new MovieDto("The Avengers", "Fancy Guy", actors, "2012", "hulk smash", 4);
        MovieDto movieDto2 = new MovieDto("That Other Movie", "Less Fancy Guy", actors, "2019", "its a movie", 5);
        MovieDto movieDto3 = new MovieDto("Back to the Future", "Forgot the guy", actors, "1985", "it has a flying car", 4);
        List<MovieDto> expected = List.of(movieDto1, movieDto2, movieDto3);
        String expectedString = objectMapper.writeValueAsString(expected);

        movieRepository.save(new MovieEntity("The Avengers", "Fancy Guy", actors, "2012", "hulk smash", 4));
        movieRepository.save(new MovieEntity("That Other Movie", "Less Fancy Guy", actors, "2019", "its a movie", 5));
        movieRepository.save(new MovieEntity("Back to the Future", "Forgot the guy", actors, "1985", "it has a flying car", 4));

        mockMvc.perform(get("/movies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedString));
    }


}