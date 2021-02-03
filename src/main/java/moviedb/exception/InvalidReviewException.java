package moviedb.exception;

public class InvalidReviewException extends RuntimeException{
    public InvalidReviewException(String message) {
        super(message);
    }
}
