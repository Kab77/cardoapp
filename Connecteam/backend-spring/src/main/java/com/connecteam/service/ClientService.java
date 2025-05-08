package com.connecteam.service;

import com.connecteam.model.Client;
import com.connecteam.repository.ClientRepository;
import com.connecteam.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(ClientDTO dto) {
        if (clientRepository.existsBySiret(dto.getSiret())) {
            throw new RuntimeException("Un client avec ce numéro SIRET existe déjà");
        }

        Client client = new Client();
        updateClientFromDTO(client, dto);
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // Vérifier si le SIRET est modifié et s'il existe déjà
        if (!client.getSiret().equals(dto.getSiret()) && 
            clientRepository.existsBySiret(dto.getSiret())) {
            throw new RuntimeException("Un client avec ce numéro SIRET existe déjà");
        }

        updateClientFromDTO(client, dto);
        return clientRepository.save(client);
    }

    private void updateClientFromDTO(Client client, ClientDTO dto) {
        client.setName(dto.getName());
        client.setSiret(dto.getSiret());
        client.setTvaNumber(dto.getTvaNumber());
        client.setStreetAddress(dto.getStreetAddress());
        client.setPostalCode(dto.getPostalCode());
        client.setCity(dto.getCity());
        client.setContactName(dto.getContactName());
        client.setContactEmail(dto.getContactEmail());
        client.setContactPhone(dto.getContactPhone());
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }

    public List<Client> searchClientsByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Client> getClientsByCity(String city) {
        return clientRepository.findByCity(city);
    }

    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client non trouvé");
        }
        clientRepository.deleteById(id);
    }
} 