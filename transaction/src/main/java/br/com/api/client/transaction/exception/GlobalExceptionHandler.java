package br.com.api.client.transaction.exception;

import br.com.api.client.transaction.model.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErroBacenIndisponivelException.class)
    public ResponseEntity<ErrorResponse> handleFeignStatusException(ErroBacenIndisponivelException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getStatus());

        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(LimiteIndisponivelException.class)
    public ResponseEntity<ErrorResponse> handleFeignStatusException(LimiteIndisponivelException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getStatus());

        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
