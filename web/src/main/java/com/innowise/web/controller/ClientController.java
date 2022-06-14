package com.innowise.web.controller;

import com.innowise.web.controller.util.GetClientsFilterParams;
import com.innowise.web.dto.client.request.PostClientRequest;
import com.innowise.web.dto.client.request.PutClientRequest;
import com.innowise.web.dto.client.response.GetClientResponse;
import com.innowise.web.dto.client.response.GetClientsResponse;
import com.innowise.web.dto.user.request.PostUserRequest;
import com.innowise.web.exception.ValidationException;
import com.innowise.web.feign.CompanyClientFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final CompanyClientFeignClient companyClientFeignClient;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/{clientId}")
    public ResponseEntity<GetClientResponse> getClient(@PathVariable Integer clientId) {
        return new ResponseEntity<>(companyClientFeignClient.getClientById(clientId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GetClientsResponse> getClients(GetClientsFilterParams clientsFilterParams) {
        return new ResponseEntity<>(companyClientFeignClient.getClientsByFilterParams(clientsFilterParams), HttpStatus.OK);
    }

    @PostMapping
    public void postClient(@RequestBody @Valid PostClientRequest clientRequest,
                            BindingResult bindingResult,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult);

        PostUserRequest userRequest = clientRequest.getAdminInfo();
        try {
            passwordEncoder.upgradeEncoding(userRequest.getPassword());
        } catch (IllegalArgumentException ex) {
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        Integer clientId = companyClientFeignClient.postClient(clientRequest);
        response.sendRedirect(request.getRequestURL()
                .append("/")
                .append(clientId)
                .toString());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClients(@RequestBody List<Integer> clientIds) {
        companyClientFeignClient.deleteClientsById(clientIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Void> updateClient(@RequestBody @Valid PutClientRequest clientRequest,
                            BindingResult bindingResult,
                            @PathVariable Integer clientId) {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult);

        companyClientFeignClient.updateClient(clientRequest, clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("activate/{clientId}")
    private ResponseEntity<Void> activateClient(@PathVariable Integer clientId) {
        companyClientFeignClient.activateClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
