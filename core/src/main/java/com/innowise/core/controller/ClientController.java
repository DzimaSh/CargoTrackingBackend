package com.innowise.core.controller;

import com.innowise.core.controller.util.GetClientsFilterParams;
import com.innowise.core.dto.client.request.PostClientRequest;
import com.innowise.core.dto.client.request.PutClientRequest;
import com.innowise.core.dto.client.response.GetClientResponse;
import com.innowise.core.dto.client.response.GetClientsResponse;
import com.innowise.core.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
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
        return clientService.getAllClientsByFilterParams(clientsFilterParams);
    }

    @PostMapping
    private void postClient(@RequestBody @Valid PostClientRequest clientRequest,
                            HttpServletResponse response) throws IOException {
        Integer currentId = clientService.createClient(clientRequest);
        response.sendRedirect("/api/clients/" + currentId);
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
}
