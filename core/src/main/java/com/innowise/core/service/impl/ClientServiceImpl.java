package com.innowise.core.service.impl;

import com.innowise.core.controller.util.GetClientsFilterParams;
import com.innowise.core.dto.client.request.*;
import com.innowise.core.dto.client.response.*;
import com.innowise.core.entity.client.Client;
import com.innowise.core.entity.client.ClientActivity;
import com.innowise.core.entity.client.Client_;
import com.innowise.core.entity.enums.ClientActivationStatus;
import com.innowise.core.entity.user.User;
import com.innowise.core.exceprtion.ClientException;
import com.innowise.core.exceprtion.UserExistsException;
import com.innowise.core.repository.ClientActivityRepository;
import com.innowise.core.repository.ClientRepository;
import com.innowise.core.repository.UserRepository;
import com.innowise.core.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientActivityRepository clientActivityRepository;
    private final Validator validator;

    @Override
    public GetClientResponse getClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientException("Client with id " + id + " not found" , HttpStatus.NOT_FOUND));
        return new GetClientResponse(client);
    }

    @Override
    public Integer createClient(PostClientRequest clientRequest) {
        Client client = clientRequest.buildClientFromRequest();
        Optional<User> userOptional = userRepository.findOne(Example.of(client.getAdminInfo()));
        User adminUser;
        if (userOptional.isPresent()) {
            adminUser = userOptional.get();
            if (adminUser.getClient() == null) {
                client.setAdminInfo(adminUser);
            } else {
                throw new UserExistsException("This admin is already working with client " + adminUser.getClient().getName(), HttpStatus.CONFLICT);
            }

        } else {
            validator.validate(clientRequest.getAdminInfo());
            adminUser = client.getAdminInfo();
        }
        adminUser.addClient(client);
        client = userRepository.save(adminUser).getClient();

        activateClient(client);
        return client.getId();
    }

    @Override
    public GetClientsResponse getClientsByFilterParams(GetClientsFilterParams params) {
        Page<Client> page = clientRepository.findAll(((root, query, builder) ->
                    filteringClientsToPredicate(root, query, builder, params)
                ),
                PageRequest.of(params.getPageNumber(), params.getPageSize()));
        List<GetClientResponse> clients = page.getContent().stream().
                map(GetClientResponse::new).
                collect(Collectors.toList());
        return new GetClientsResponse(clients, page.getTotalElements());
    }

    @Override
    public void deleteClientsById(List<Integer> ids) {
        ids.forEach(id -> {
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new ClientException("Client with id " + id + " not found" , HttpStatus.NOT_FOUND));

            User user = userRepository.getByClient(client);
            user.removeClient();
            userRepository.save(user);
        });
    }

    @Override
    public void updateClient(PutClientRequest clientRequest, Integer id) {
        Client clientToUpdate = clientRepository.findById(id)
                .orElseThrow(() -> new ClientException("Client with id " + id + " not found" , HttpStatus.NOT_FOUND));
        updateClientFromRequest(clientRequest, clientToUpdate);
        clientRepository.save(clientToUpdate);
    }

    @Override
    public void activateClient(Integer clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientException("Client with id " + clientId + " not found" , HttpStatus.NOT_FOUND));
        activateClient(client);
    }

    @Override
    public void activateClient(Client client) {
        if (client.getDeleteDate() != null) {
            clientActivityRepository.save(buildAction(client, ClientActivationStatus.ACTIVATED));
            client.setDeleteDate(null);
            clientRepository.save(client);
        }
        else
            throw new ClientException("Client with id " + client.getId() + " is already activated", HttpStatus.CONFLICT);
    }


    private Predicate filteringClientsToPredicate(Root<Client> root, CriteriaQuery query, CriteriaBuilder builder, GetClientsFilterParams params) {
        List<Predicate> predicates = new ArrayList<>();
        if (params.getName() != null)
            predicates.add(builder.equal(root.get(Client_.name), params.getName()));
        if (params.getStatus() != null)
            predicates.add(builder.equal(root.get(Client_.subjectStatus), params.getStatus()));
        return builder.and(predicates.toArray(new Predicate[]{}));
    }

    private void updateClientFromRequest(PutClientRequest clientRequest, Client clientToUpdate) {
        clientToUpdate.setName(clientRequest.getName());
        clientToUpdate.setSubjectStatus(clientRequest.getStatus());
    }

    private ClientActivity buildAction(Client client, ClientActivationStatus action) {
        return ClientActivity.builder()
                .activationStatus(action)
                .date(new Timestamp(System.currentTimeMillis()))
                .client(client)
                .build();
    }
}
