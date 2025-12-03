package br.com.api.client.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LimiteIndisponivelException extends RuntimeException{

    private int status = 401;

    public LimiteIndisponivelException(){
        super();
    }

    public LimiteIndisponivelException(String message){
        super(message);
    }



}
