package com.crud.springboot_crud.controllerAdvice;


import com.crud.springboot_crud.exception.EmailAlreadyExistException;
import com.crud.springboot_crud.exception.InvalidPasswordException;
import com.crud.springboot_crud.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorInfo> handleEmailAlreadyExistException(EmailAlreadyExistException ex){
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorInfo> handleInvalidPasswordException(InvalidPasswordException ex){
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleUserNotFoundException(UserNotFoundException ex){
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }



}
