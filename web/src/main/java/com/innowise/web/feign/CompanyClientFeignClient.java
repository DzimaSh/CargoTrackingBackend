package com.innowise.web.feign;

import com.innowise.web.controller.util.GetClientsFilterParams;
import com.innowise.web.dto.client.request.PostClientRequest;
import com.innowise.web.dto.client.request.PutClientRequest;
import com.innowise.web.dto.client.response.GetClientResponse;
import com.innowise.web.dto.client.response.GetClientsResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ConditionalOnProperty(value = "service.user-core-url")
@FeignClient(
        name = "CompanyClientFeignClient",
        url = "${service.user-core-url}",
        path = "/api/clients",
        configuration = {CustomFeignErrorDecoder.class}
)
public interface CompanyClientFeignClient {
    @GetMapping("/{clientId}")
    GetClientResponse getClientById(@PathVariable Integer clientId);

    @GetMapping
    GetClientsResponse getClientsByFilterParams(@SpringQueryMap(true) GetClientsFilterParams clientsFilterParams);

    @PostMapping
    Integer postClient(@RequestBody PostClientRequest clientRequest);

    @DeleteMapping
    void deleteClientsById(@RequestBody List<Integer> clientIds);

    @PutMapping("/{clientId}")
    void updateClient(@RequestBody PutClientRequest clientRequest, @PathVariable Integer clientId);

    @PutMapping("activate/{clientId}")
    void activateClient(@PathVariable Integer clientId);
}
