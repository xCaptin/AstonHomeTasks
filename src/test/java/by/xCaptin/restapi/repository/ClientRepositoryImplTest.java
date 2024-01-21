package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.db.ConnectionPool;
import by.xCaptin.restapi.entity.ClientEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryImplTest {

    @BeforeAll
    static void init() {
        ConnectionPool.init();
    }

    @Test
    void insertClient() {
        ClientRepository clientRepository = new ClientRepositoryImpl();

        ClientEntity client = new ClientEntity();
        client.setName("Test Client");
        client.setGroceryStoreID(1L);

        try {
            ClientEntity insertedClient = clientRepository.insertClient(client);

            assertNotNull(insertedClient.getId());
        } catch (SQLException e) {
            fail("Exception thrown during insertClient");
        }
    }

    @Test
    void updateClient() {
        ClientRepository clientRepository = new ClientRepositoryImpl();

        ClientEntity client = new ClientEntity();
        client.setId(1L);
        client.setName("Updated Client");
        client.setGroceryStoreID(2L);

        try {
            ClientEntity updatedClient = clientRepository.updateClient(client);

            assertNotNull(updatedClient);
            assertEquals("Updated Client", updatedClient.getName());
            assertEquals(2L, updatedClient.getGroceryStoreID());
        } catch (SQLException e) {
            fail("Exception thrown during updateClient");
        }
    }

    @Test
    void selectClient() {
        ClientRepository clientRepository = new ClientRepositoryImpl();

        Long clientId = 1L;

        try {
            ClientEntity selectedClient = clientRepository.selectClient(clientId);

            assertNotNull(selectedClient);
            assertEquals(clientId, selectedClient.getId());
        } catch (SQLException e) {
            fail("Exception thrown during selectClient");
        }
    }

    @Test
    void deleteClient() {
        ClientRepository clientRepository = new ClientRepositoryImpl();

        Long clientId = 1L;

        try {
            boolean isDeleted = clientRepository.deleteClient(clientId);

            assertTrue(isDeleted);
        } catch (SQLException e) {
            fail("Exception thrown during deleteClient");
        }
    }

    @Test
    void selectAllClients() {
        ClientRepository clientRepository = new ClientRepositoryImpl();

        try {
            List<ClientEntity> clientList = clientRepository.selectAllClients();

            assertNotNull(clientList);
            assertFalse(clientList.isEmpty());
        } catch (SQLException e) {
            fail("Exception thrown during selectAllClients");
        }
    }
}
