package moviedb.service;

import moviedb.model.MovieDto;
import moviedb.model.MovieEntity;
import moviedb.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private ModelMapper modelMapper;

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.modelMapper = new ModelMapper();
        this.movieRepository = movieRepository;
    }

    public List<MovieDto> getAllMovies() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        List<MovieDto> returnList = new ArrayList<>();
        movieEntities.forEach(movieEntity ->
               returnList.add(modelMapper.map(movieEntity, MovieDto.class)));

        return returnList;
    }
}
