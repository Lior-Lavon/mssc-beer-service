package guru.sfg.msscbeerservice.controller.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

// This class will be added to all Controller Classes to support Exception handling
@ControllerAdvice
public class MvcExceptionHandler {

    // Exception handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List> validateArgumentHandler(MethodArgumentNotValidException ex){

        List<String> errors = new ArrayList<>(ex.getBindingResult().getFieldErrors().size());
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validateErrorHandler(ConstraintViolationException ex){

        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());

        ex.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List> handleBindException(BindException ex){
        return new ResponseEntity<>(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}
