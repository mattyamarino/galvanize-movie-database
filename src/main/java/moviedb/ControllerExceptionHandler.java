package moviedb;

import moviedb.exception.InvalidReviewException;
import moviedb.exception.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({InvalidReviewException.class, MovieNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void movieAppExceptionHandler(){

    }


}
