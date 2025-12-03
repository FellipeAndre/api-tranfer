package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.BacenResponse;

public interface BacenClient{

    BacenResponse notificarBacen(DadosTransferencia requestBacen);

}
