package br.com.api.client.transaction.exception;

import br.com.api.client.transaction.model.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(FeignException.class)
//    public ResponseEntity<ErrorResponse> handleFeignStatusException(FeignException ex){
//        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.status());
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }

    @ExceptionHandler(LimiteIndisponivelException.class)
    public ResponseEntity<ErrorResponse> handleFeignStatusException(LimiteIndisponivelException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getStatus());

        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
