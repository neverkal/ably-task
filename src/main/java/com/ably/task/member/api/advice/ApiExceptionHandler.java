package com.ably.task.member.api.advice;

import com.ably.task.member.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.Charset;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGlobalException(Exception e) {
        log.error("Global Exception");
        log.error(e.getMessage());

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(
                ApiResponse.fail(),
                headers,
                httpStatus
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Illegal Argument Exception");

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(
                ApiResponse.illegalArgument(e),
                headers,
                httpStatus
        );
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation Exception");

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(
                ApiResponse.validationError(e),
                headers,
                httpStatus
        );
    }

}
