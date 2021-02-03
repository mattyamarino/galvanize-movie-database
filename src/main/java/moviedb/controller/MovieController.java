package moviedb.controller;

import moviedb.model.MovieDto;
import moviedb.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/{title}")
    public MovieDto getMovieByTitle(@PathVariable String title){
        return service.getMovieByTitle(title);
    }

}
