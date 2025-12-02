package br.com.api.client.transaction.controller;

import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.service.ConectClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-transfer")
@RequiredArgsConstructor
public class TransactionController {

    private final ConectClientService conectClientService;

     @GetMapping("/{cpf}")
     public ResponseEntity<ClientResponse> getClientCpf(@PathVariable @Valid String cpf){

        var result = conectClientService.getClient(cpf);

         return ResponseEntity.status(HttpStatus.OK).body(result);
     }
}
