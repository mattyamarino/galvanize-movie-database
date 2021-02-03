package moviedb.controller;

import moviedb.model.MovieDto;
import moviedb.model.ReviewDto;
import moviedb.model.ReviewEntity;
import moviedb.service.MovieService;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{title}")
    public void addReviewToMoview(@PathVariable String title, @RequestBody ReviewDto reviewDto) {
        service.addReviewToMovie(title, reviewDto);
    }

}
