package com.tbd.common.exceptions.handler;

import com.tbd.common.dto.ErrorResponse;
import com.tbd.common.exceptions.PageSizeLimitExceedException;
import com.tbd.common.exceptions.ResourceNotFoundInDbException;
import com.tbd.common.exceptions.UserSubNotFoundInHeaderException;
import com.tbd.common.exceptions.ValidationException;
import com.tbd.common.utils.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.StringJoiner;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private final Translator translator;

    @ExceptionHandler(ResourceNotFoundInDbException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundInDbException(ResourceNotFoundInDbException ex) {
        return getErrorResponse(ex.getMessage(), HttpStatus.NO_CONTENT.value());
    }

    @ExceptionHandler(PageSizeLimitExceedException.class)
    public ResponseEntity<ErrorResponse> handlePageSizeLimitExceedException(PageSizeLimitExceedException ex) {
        return getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        return getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(UserSubNotFoundInHeaderException.class)
    public ResponseEntity<ErrorResponse> handleUserSubNotFoundInHeaderException(UserSubNotFoundInHeaderException ex) {
        return getErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodValidationExceptions(MethodArgumentNotValidException ex) {

        StringJoiner errors = new StringJoiner(", ");

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(translator.translate(errorMessage));
        });

        ErrorResponse errorResponse = new ErrorResponse(
                errors.toString(),
                HttpStatus.BAD_REQUEST.value(), Instant.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .statusCode(500)
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(String error, Integer statusCode) {

        ErrorResponse errorResponse = new ErrorResponse(
                error,
                statusCode,
                Instant.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }
}
