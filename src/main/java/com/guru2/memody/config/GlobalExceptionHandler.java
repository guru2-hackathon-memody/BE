package com.guru2.memody.config;

import com.guru2.memody.Exception.*;
import com.guru2.memody.dto.ErrorResponse;
import com.guru2.memody.entity.Region;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // User Already Exists
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(
            UserAlreadyExistsException e
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(new ErrorResponse(
                        "USER_ALREADY_EXISTS",
                        e.getMessage()
                ));
    }

    // User Name Alreay Exist
    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserNameAlreadyExists(
            UserNameAlreadyExistsException e
    ){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "USERNAME_ALREADY_EXIST",
                        e.getMessage()
                ));
    }

    // Region Wrong Exception
    @ExceptionHandler(RegionWrongException.class)
    public ResponseEntity<ErrorResponse> handleRegionWrongException(
            RegionWrongException e
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "REGION_WRONG",
                        e.getMessage()
                ));
    }

    // User Not Found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException e
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "USER_NOT_FOUND",
                        e.getMessage()
                ));
    }

    // Record Not Found
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(
            RecordNotFoundException e
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "RECORD_NOT_FOUND",
                        e.getMessage()
                ));
    }

    // Custom Not Allowed Exception
    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleNotAllowedException(
            NotAllowedException e
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "NOT_ALLOWED_REQUEST",
                        e.getMessage()
                ));
    }

    // Custom Not Found Exception
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException e
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "REQUEST_NOT_FOUND",
                        e.getMessage()
                ));
    }
}
