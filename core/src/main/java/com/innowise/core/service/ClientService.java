package com.innowise.core.service;

import com.innowise.core.controller.util.GetClientsFilterParams;
import com.innowise.core.dto.client.request.*;
import com.innowise.core.dto.client.response.*;

import java.util.List;

public interface ClientService {
    GetClientResponse getClientById(Integer id);
    Integer createClient(PostClientRequest clientRequest);
    GetClientsResponse getAllClientsByFilterParams(GetClientsFilterParams params);
    void deleteClientsById(List<Integer> ids);
    void updateClient(PutClientRequest clientRequest, Integer id);
}
