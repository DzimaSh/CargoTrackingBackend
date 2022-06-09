package com.innowise.core.service.impl;

import com.innowise.core.controller.util.GetClientsFilterParams;
import com.innowise.core.dto.client.request.*;
import com.innowise.core.dto.client.response.*;
import com.innowise.core.entity.client.Client;
import com.innowise.core.entity.user.User;
import com.innowise.core.exceprtion.UserNotFoundException;
import com.innowise.core.repository.ClientRepository;
import com.innowise.core.repository.UserRepository;
import com.innowise.core.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Override
    public GetClientResponse getClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("not found", HttpStatus.NOT_FOUND));
        return new GetClientResponse(client);
    }

    @Override
    public Integer createClient(PostClientRequest clientRequest) {
        Client client = clientRequest.buildClientFromRequest();
        Optional<User> userOptional = userRepository.findOne(Example.of(client.getAdminInfo()));
        if (userOptional.isPresent()) {
            client.setAdminInfo(userOptional.get());
        } else {
            client.setAdminInfo(userRepository.save(client.getAdminInfo()));
        }
        return clientRepository.save(client).getId();
    }

    @Override
    public GetClientsResponse getAllClientsByFilterParams(GetClientsFilterParams params) {
        return null;
    }

    @Override
    public void deleteClientsById(List<Integer> ids) {

    }

    @Override
    public void updateClient(PutClientRequest clientRequest, Integer id) {

    }
}
