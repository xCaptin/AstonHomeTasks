package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.ClientDto;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    ClientDto getClientById(Long id) throws SQLException;

    ClientDto saveClient(ClientDto clientDto) throws SQLException;

    ClientDto updateClient(ClientDto clientDto) throws SQLException;

    boolean removeClient(Long id) throws SQLException;

    List<ClientDto> getAllClients() throws SQLException;
}