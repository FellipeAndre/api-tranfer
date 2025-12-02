package br.com.api.client.transaction.service;

import br.com.api.client.transaction.client.CadastroClient;
import br.com.api.client.transaction.client.impl.BacenClientImpl;
import br.com.api.client.transaction.client.impl.CadastroClientImpl;
import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConectClientServiceTest {

    @InjectMocks
    ConectClientService conectClientService;

    @Mock
    BacenClientImpl bacenClient;

    @Mock
    CadastroClientImpl cadastroClient;

    @Mock
    ContaService contaService;

    @Test
    void getClient() {

        var cpf = "44653378800";
        var response = new ClientResponse();
        var conta = new ContaResponse();
        var dados = new DadosTransferencia();
        dados.setNome("teste");
        dados.setSaida(40.0);
        dados.setNumeroConta("0001");
        List<DadosTransferencia> dadosTransferencias = new ArrayList<>();
        conta.setSaldo(4000.0);
        conta.setContaAtiva(true);
        conta.setTransferencias(dadosTransferencias);
        response.setNome("teste");
        response.setContaResponse(conta);

        Mockito.when(cadastroClient.consultarCadastro(Mockito.anyString())).thenReturn(response);

        var result = conectClientService.getClient(cpf);

        Assertions.assertNotNull(result);

        Mockito.verify(contaService, Mockito.times(1))
                        .Transferir(conta, 40.0);
        Mockito.verify(cadastroClient, Mockito.times(1))
                .consultarCadastro(cpf);
    }
}