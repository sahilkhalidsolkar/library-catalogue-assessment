package com.example.libraryBookCatalogue.exception;

import com.example.libraryBookCatalogue.Dto.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Errorobj> handlebookNotFoundError(BookNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        Errorobj errorObj = new Errorobj();
        errors.put("message", ex.getMessage());
        errorObj.setErrors(errors);
        return new ResponseEntity<Errorobj>(errorObj, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errorobj> handleMethodArgumentNotvalid(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        Errorobj errorObj = new Errorobj();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        errorObj.setErrors(errors);
        return new ResponseEntity<Errorobj>(errorObj,HttpStatus.BAD_REQUEST);
    }


}
