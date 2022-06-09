package com.innowise.core.controller;

import com.innowise.core.dto.client.request.PostClientRequest;
import com.innowise.core.dto.client.response.GetClientResponse;
import com.innowise.core.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{clientId}")
    private GetClientResponse getClient(@PathVariable Integer clientId) {
        GetClientResponse clientResponse = clientService.getClientById(clientId);
        return clientResponse;
    }

    @PostMapping
    private void postClient(@RequestBody @Valid PostClientRequest clientRequest,
                            HttpServletResponse response) throws IOException {
        Integer currentId = clientService.createClient(clientRequest);
        response.sendRedirect("/api/clients/" + currentId);
    }
}
