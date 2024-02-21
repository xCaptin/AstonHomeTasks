package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.ClientDto;
import by.xCaptin.restapi.entity.ClientEntity;
import by.xCaptin.restapi.mapper.Mapper;
import by.xCaptin.restapi.repository.ClientRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {
    private final ClientRepositoryImpl clientRepositoryImpl;
    private final Mapper<ClientEntity, ClientDto> mapper;

    public ClientServiceImpl(ClientRepositoryImpl clientRepositoryImpl, Mapper<ClientEntity, ClientDto> mapper) {
        this.clientRepositoryImpl = clientRepositoryImpl;
        this.mapper = mapper;
    }

    @Override
    public ClientDto getClientById(Long id) throws SQLException {
        ClientEntity client = clientRepositoryImpl.selectClient(id);
        return mapper.toDto(client);
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) throws SQLException {
        ClientEntity client = mapper.fromDto(clientDto);
        ClientEntity savedClient = clientRepositoryImpl.insertClient(client);
        return mapper.toDto(savedClient);
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto) throws SQLException {
        ClientEntity client = mapper.fromDto(clientDto);
        ClientEntity updatedClient = clientRepositoryImpl.updateClient(client);
        return mapper.toDto(updatedClient);
    }

    @Override
    public boolean removeClient(Long id) throws SQLException {
        return clientRepositoryImpl.deleteClient(id);
    }

    @Override
    public List<ClientDto> getAllClients() throws SQLException {
        List<ClientEntity> clientList = clientRepositoryImpl.selectAllClients();
        return clientList.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}