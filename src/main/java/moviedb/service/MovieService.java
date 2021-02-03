package moviedb.service;

import moviedb.exception.MovieNotFoundException;
import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
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

    public MovieDto getMovieByTitle(String title) {
        MovieEntity movieEntity = movieRepository.findByTitle(title);
        if(movieEntity == null)
            throw new MovieNotFoundException("We are Sorry! Movie not found");

        return mapMovieDto(movieEntity);
    }
}
