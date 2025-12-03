package br.com.api.client.transaction.client.impl;

import br.com.api.client.transaction.client.CadastroFeignClient;
import br.com.api.client.transaction.mapper.ClientResponseMapper;
import br.com.api.client.transaction.model.ClientResponse;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CadastroClientImplTest {

    @Autowired
    CadastroClientImpl cadastroClientImpl;

    @MockBean
    CadastroFeignClient cadastroClient;

    @MockBean
    ClientResponseMapper mapper;

    final String TEST_CPF = "12345678900";

    @Test
    void deveRetornarClientResponseEmCasoDeSucesso() {
        ClientResponse mockSuccessResponse = new ClientResponse();
        mockSuccessResponse.setNome("Teste Sucesso");

        Mockito.when(cadastroClient.consultar(TEST_CPF))
                .thenReturn(mockSuccessResponse);

        ClientResponse resultado = cadastroClientImpl.consultarSaldo(TEST_CPF);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Teste Sucesso", resultado.getNome());

        Mockito.verify(cadastroClient, Mockito.times(1)).consultar(TEST_CPF);

        Mockito.verify(mapper, Mockito.never()).getClientResponse();
    }

    @Test
    void deveTentar4VezesEChamarFallbackAposFalha() {

        Request feignRequestMock = Mockito.mock(Request.class);

        ClientResponse fallbackResponse = new ClientResponse();
        fallbackResponse.setNome("Sistema Indisponível");

        Mockito.when(mapper.getClientResponse()).thenReturn(fallbackResponse);

        Mockito.doThrow(
                        new FeignException.ServiceUnavailable("Simulando erro de rede/503", feignRequestMock, null, Collections.emptyMap())
                )
                .when(cadastroClient).consultar(Mockito.anyString());

        ClientResponse resultado = cadastroClientImpl.consultarSaldo(TEST_CPF);

        Assertions.assertEquals("Sistema Indisponível", resultado.getNome());

        Mockito.verify(cadastroClient, Mockito.times(1)).consultar(TEST_CPF);

        Mockito.verify(mapper, Mockito.times(1)).getClientResponse();
    }
}