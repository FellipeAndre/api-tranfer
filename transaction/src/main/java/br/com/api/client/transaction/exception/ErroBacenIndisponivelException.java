package br.com.api.client.transaction.exception;

import lombok.Getter;

@Getter
public class ErroBacenIndisponivelException extends RuntimeException {

    private int status = 429;

    public ErroBacenIndisponivelException(){
        super();
    }

    public ErroBacenIndisponivelException(String message){
        super(message);
    }
}
