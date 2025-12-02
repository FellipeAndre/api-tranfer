package br.com.api.client.transaction.service;

import br.com.api.client.transaction.exception.DisableAccountException;
import br.com.api.client.transaction.exception.LimiteIndisponivelException;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContaService {

    public DadosTransferencia Transferir(ContaResponse contaResponse, Double valorEnviado){

        var activeAccount = contaResponse.getContaAtiva();

        var dadosContaDestino = new DadosTransferencia();

        if(activeAccount){

            var limitDisponible = contaResponse.getSaldo() >= valorEnviado;

            if (!limitDisponible){
                throw new LimiteIndisponivelException("Saldo Insulficiente para Transferencia");
            }

        } else {
            throw new DisableAccountException("Conta Desativada !");
        }

        if(valorEnviado > contaResponse.getLimiteDiario()){
            throw new LimiteIndisponivelException("O seu limite diario é de 1000 reais ");
        }

        dadosContaDestino.setNumeroConta("0000001");
        dadosContaDestino.setNome("Luiz Henrique");
        dadosContaDestino.setSaida(valorEnviado);

        var valorAtualizado = contaResponse.getSaldo() - valorEnviado;
        contaResponse.setSaldo(valorAtualizado);

        return dadosContaDestino;
    }

    private List<DadosTransferencia> transferencias(DadosTransferencia contaDestino, ContaResponse response){
        var list = response.getTransferencias();
        list.add(contaDestino);
        return list;
    };
}
