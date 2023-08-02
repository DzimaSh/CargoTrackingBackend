package com.innowise.core.service;

import com.innowise.core.controller.util.GetClientsFilterParams;
import com.innowise.core.dto.client.request.*;
import com.innowise.core.dto.client.response.*;
import com.innowise.core.entity.client.Client;

import java.util.List;

public interface ClientService {
    GetClientResponse getClientById(Integer id);
    Integer createClient(PostClientRequest clientRequest);
    GetClientsResponse getClientsByFilterParams(GetClientsFilterParams params);
    void deleteClientsById(List<Integer> ids);
    void updateClient(PutClientRequest clientRequest, Integer id);
    void activateClient(Integer id);
    void activateClient(Client client);
}
