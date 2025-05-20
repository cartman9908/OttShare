package seohan.ottshare.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import seohan.ottshare.dto.error.ErrorResponse;
import seohan.ottshare.exception.NotOttLeaderException;
import seohan.ottshare.exception.OttLeaderNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OttLeaderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLeaderNotFoundException(OttLeaderNotFoundException e) {
        log.warn("Not Found Leader {}", e.getMessage());
        ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(NotOttLeaderException.class)
    public ResponseEntity<ErrorResponse> handleNotOttLeaderException(NotOttLeaderException e) {
        log.warn("Not Ott Leader : {}", e.getMessage());
        ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(404).body(response);
    }
}