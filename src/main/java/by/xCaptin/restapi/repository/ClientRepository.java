package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.entity.ClientEntity;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {
    ClientEntity insertClient(ClientEntity client) throws SQLException;

    ClientEntity updateClient(ClientEntity client) throws SQLException;

    ClientEntity selectClient(Long id) throws SQLException;

    boolean deleteClient(Long id) throws SQLException;

    List<ClientEntity> selectAllClients() throws SQLException;
}