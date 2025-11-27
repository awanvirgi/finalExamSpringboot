package com.finalexam.trabea.error;

import com.finalexam.trabea.error.dto.ErrorMessageResponse;
import com.finalexam.trabea.error.exception.BadRequestException;
import com.finalexam.trabea.error.exception.ConflictException;
import com.finalexam.trabea.error.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ErrorMessageResponse<Object> dto = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors(e.getCause())
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;

        var errorsMap = new HashMap<String, String>();

        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        ErrorMessageResponse<Map<String, String>> dto = ErrorMessageResponse.<Map<String, String>>builder()
                .status(httpStatus)
                .message("Input invalid")
                .errors(errorsMap)
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleBadRequest(BadRequestException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(status)
                .errors(ex.getCause())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorMessage,status);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleNotFound(ConflictException ex){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .errors(ex.getCause())
                .message(ex.getMessage())
                .status(status)
                .build();
        return new ResponseEntity<>(errorMessage,status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleNotFound(NoHandlerFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .errors(ex.getCause())
                .message(ex.getMessage())
                .status(status)
                .build();
        return new  ResponseEntity<>(errorMessage,status);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleAuthBadCredintial(BadCredentialsException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .errors(ex.getCause())
                .message(ex.getMessage())
                .status(status)
                .build();
        return new  ResponseEntity<>(errorMessage,status);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleAuthException(AuthenticationException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .errors(ex.getCause())
                .message(ex.getMessage())
                .status(status)
                .build();
        return new  ResponseEntity<>(errorMessage,status);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .errors(ex.getCause())
                .message(ex.getMessage())
                .status(status)
                .build();
        return new  ResponseEntity<>(errorMessage,status);
    }

}
