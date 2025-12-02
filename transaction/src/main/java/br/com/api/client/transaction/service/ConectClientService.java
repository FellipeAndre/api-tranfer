package br.com.api.client.transaction.service;

import br.com.api.client.transaction.client.impl.BacenClientImpl;
import br.com.api.client.transaction.client.impl.CadastroClientImpl;
import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.RequestBacen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConectClientService{

    private final BacenClientImpl bacenClient;
    private final CadastroClientImpl cadastroClient;
    private final ContaService contaService;

    public ClientResponse getClient(String cpf){
        var response = cadastroClient.consultarCadastro(cpf);

        var contaDestino = contaService.Transferir(response.getContaResponse(), 40.0);
        var list = response.getContaResponse().getTransferencias();
        list.add(contaDestino);

        RequestBacen requestBacen = new RequestBacen();
        //this.bacenNotify(requestBacen);

        return response;
    }

    private void bacenNotify(RequestBacen requestBacen){
        bacenClient.notificarBacen(requestBacen);
    }

}
