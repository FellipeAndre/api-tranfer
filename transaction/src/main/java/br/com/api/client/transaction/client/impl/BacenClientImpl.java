package br.com.api.client.transaction.client.impl;

import br.com.api.client.transaction.client.BacenClient;
import br.com.api.client.transaction.client.BacenFeignClient;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.BacenResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BacenClientImpl implements BacenClient  {

    private final BacenFeignClient bacenFeingClient;

    @Retry(name = "default")
    @CircuitBreaker(name = "default", fallbackMethod = "bacenFallback")
    @Override
    public BacenResponse notificarBacen(DadosTransferencia requestBacen) {

        BacenResponse response = bacenFeingClient.notificar(requestBacen);

        return response;
    }

    public BacenResponse bacenFallback(DadosTransferencia requestBacen, Throwable throwable){
        return new BacenResponse("Sistema Indisponível", 429);
    }
}
