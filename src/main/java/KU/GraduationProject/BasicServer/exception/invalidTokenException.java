package KU.GraduationProject.BasicServer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class invalidTokenException extends RuntimeException{
    public invalidTokenException(String message){
        super(message);
    }
}
