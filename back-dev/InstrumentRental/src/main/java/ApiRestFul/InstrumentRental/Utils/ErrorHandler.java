package ApiRestFul.InstrumentRental.Utils;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerInputValidationError(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors().stream().map(InputDataValidationError::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handlerEnumValidationError(HttpMessageNotReadableException e){
        var errors = e.getMessage();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity handlerTokenExpired(TokenExpiredException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handlerNullPointer(NullPointerException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    private record InputDataValidationError(String field, String error){
        public InputDataValidationError(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
