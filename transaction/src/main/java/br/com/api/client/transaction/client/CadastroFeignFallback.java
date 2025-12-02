package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.ClientResponse;
import org.springframework.stereotype.Component;

@Component
public class CadastroFeignFallback implements CadastroFeignClient{

    @Override
    public ClientResponse consultar(String cpf) {
        return null;
    }
}
