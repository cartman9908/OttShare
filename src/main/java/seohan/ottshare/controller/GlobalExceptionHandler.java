package seohan.ottshare.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import seohan.ottshare.dto.error.ErrorResponse;
import seohan.ottshare.exception.*;

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

    @ExceptionHandler(SharingUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSharingUserNotFoundException(SharingUserNotFoundException e) {
        log.warn("Not Sharing User : {}", e.getMessage());
        ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(NotFoundSharingUserForRoom.class)
    public ResponseEntity<ErrorResponse> handleNotFoundSharingUserForRoom(NotFoundSharingUserForRoom e) {
        log.warn("Not Found Sharing User For Room");
        ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(SharingUserNotCheckedException.class)
    public ResponseEntity<ErrorResponse> handleSharingUserNotCheckedException(SharingUserNotCheckedException e) {
        log.warn("Sharing User Not Checked : {}", e.getMessage());
        ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(OttShareRoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOttShareRoomNotFoundException(OttShareRoomNotFoundException e) {
        log.warn("Not Ott Share Room : {}", e.getMessage());
        ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        log.warn("Not User : {}", e.getMessage());
        ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(404).body(response);
    }
}