package br.com.api.client.transaction.mapper;

import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientResponseMapper {

    public ClientResponse getClientResponse(){

        var client = new ClientResponse();
        var accountClient = new ContaResponse();
        accountClient.setContaAtiva(Boolean.TRUE);
        accountClient.setSaldo(3000.0);
        client.setContaResponse(accountClient);
        client.setNome("Felipe Tobias");

        return client;
    }
}
