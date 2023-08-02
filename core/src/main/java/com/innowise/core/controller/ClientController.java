package com.innowise.core.controller;

import com.innowise.core.controller.util.GetClientsFilterParams;
import com.innowise.core.dto.client.request.PostClientRequest;
import com.innowise.core.dto.client.request.PutClientRequest;
import com.innowise.core.dto.client.response.GetClientResponse;
import com.innowise.core.dto.client.response.GetClientsResponse;
import com.innowise.core.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{clientId}")
    private GetClientResponse getClient(@PathVariable Integer clientId) {
        return clientService.getClientById(clientId);
    }

    @GetMapping
    private GetClientsResponse getClients(GetClientsFilterParams clientsFilterParams) {
        return clientService.getClientsByFilterParams(clientsFilterParams);
    }

    @PostMapping
    private Integer postClient(@RequestBody @Valid PostClientRequest clientRequest){
        return clientService.createClient(clientRequest);
    }

    @DeleteMapping
    private void deleteClients(@RequestBody List<Integer> clientIds) {
        clientService.deleteClientsById(clientIds);
    }

    @PutMapping("/{clientId}")
    private void updateClient(@RequestBody PutClientRequest clientRequest,
                              @PathVariable Integer clientId) {
        clientService.updateClient(clientRequest, clientId);
    }

    @PutMapping("activate/{clientId}")
    private void activateClient(@PathVariable Integer clientId) {
        clientService.activateClient(clientId);
    }
}
