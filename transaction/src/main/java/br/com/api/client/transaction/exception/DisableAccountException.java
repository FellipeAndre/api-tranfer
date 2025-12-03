package br.com.api.client.transaction.exception;

import lombok.Getter;

@Getter
public class DisableAccountException extends RuntimeException{

    private int status = 604;

    public DisableAccountException(){
        super();
    }

    public DisableAccountException(String message){
        super(message);
    }



}
