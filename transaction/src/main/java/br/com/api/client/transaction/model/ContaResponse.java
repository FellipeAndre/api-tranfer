package br.com.api.client.transaction.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponse {

    private Boolean contaAtiva;

    private Double saldo;

    private Integer limiteDiario = 1000;

    private List<DadosTransferencia> transferencias = new ArrayList<>();

}
