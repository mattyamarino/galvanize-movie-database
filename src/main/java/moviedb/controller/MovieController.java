package moviedb.controller;

import moviedb.model.MovieDto;
import moviedb.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/movies")
    public List<MovieDto> getAllMovies() {
        return service.getAllMovies();
    }

}
