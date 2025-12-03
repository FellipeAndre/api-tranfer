package br.com.api.client.transaction.client.impl;

import br.com.api.client.transaction.client.BacenFeignClient;
import br.com.api.client.transaction.model.BacenResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.dto.DadosTransferenciaDTO;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BacenClientImplTest {

    @Autowired
    BacenClientImpl bacenClientImpl;

    @MockBean
    BacenFeignClient bacenFeingClient;

    @Test
    void deveRetornarSucessoESeguirFluxoNormal() {
        BacenResponse sucessoEsperado = new BacenResponse("OK", 200 );

        Mockito.when(bacenFeingClient.notificar(Mockito.any()))
                .thenReturn(sucessoEsperado);

        BacenResponse resultado = bacenClientImpl.notificarBacen(new DadosTransferencia());

        Assertions.assertEquals(200, resultado.getCod());
        Assertions.assertEquals("OK", resultado.getMessage());
    }


    @Test
    void deveTentar4VezesEAtivarFallbackAposFalha() {

        Request feignRequestMock = Mockito.mock(Request.class);

        Map<String, Collection<String>> headersMap = Map.of(
                "Host", List.of("api.bacen.com.br"),
                "Content-Type", List.of("application/json"),
                "X-Request-ID", List.of("trx-923a-4f5c-b1d6-7e8f9a01b2c3")
        );

        var dados = new DadosTransferencia();
        dados.setNome("teste");
        dados.setSaida(1000.54);
        dados.setNumeroConta("0001");

        Mockito.doThrow(
                        new FeignException.FeignClientException(
                                429,
                                "Sistema Indisponivel",
                                feignRequestMock,
                                null,
                                headersMap
                        )
                )
                .when(bacenFeingClient)
                .notificar(Mockito.any());

        BacenResponse resultado = bacenClientImpl.notificarBacen(dados);

        Assertions.assertEquals(429, resultado.getCod());
        Assertions.assertEquals("Sistema Indisponível", resultado.getMessage());

        Mockito.verify(bacenFeingClient, Mockito.times(1)).notificar(Mockito.any());
    }


}