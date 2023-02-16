package pl.mantiscrab.budgetr;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BudgetrException extends RuntimeException{
    private final HttpStatus httpStatus;

    public BudgetrException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
