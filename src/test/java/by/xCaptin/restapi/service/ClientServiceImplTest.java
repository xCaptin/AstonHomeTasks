package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.ClientDto;
import by.xCaptin.restapi.entity.ClientEntity;
import by.xCaptin.restapi.mapper.Mapper;
import by.xCaptin.restapi.repository.ClientRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ClientServiceImplTest {

    @Mock
    private ClientRepositoryImpl clientRepositoryImpl;

    @Mock
    private Mapper<ClientEntity, ClientDto> mapper;

    private ClientServiceImpl clientServiceImpl;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        clientServiceImpl = new ClientServiceImpl(clientRepositoryImpl, mapper);
    }

    @Test
    public void testGetAllClients() throws SQLException {
        List<ClientEntity> clientList = new ArrayList<>();
        clientList.add(new ClientEntity(1L, "Client 1", 1L));
        clientList.add(new ClientEntity(2L, "Client 2", 2L));
        when(clientRepositoryImpl.selectAllClients()).thenReturn(clientList);

        List<ClientDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new ClientDto(1L, "Client 1", 1L));
        expectedDtoList.add(new ClientDto(2L, "Client 2", 2L));
        when(mapper.toDto(any(ClientEntity.class))).thenAnswer(invocation -> {
            ClientEntity entity = invocation.getArgument(0);
            return new ClientDto(entity.getId(), entity.getName(), entity.getGroceryStoreID());
        });

        List<ClientDto> result = clientServiceImpl.getAllClients();

        assertEquals(expectedDtoList, result);

        verify(clientRepositoryImpl, times(1)).selectAllClients();
        verify(mapper, times(2)).toDto(any(ClientEntity.class));
    }

    @Test
    public void testGetClientById() throws SQLException {
        Long clientId = 1L;
        ClientEntity client = new ClientEntity(clientId, "Client 1", 1L);
        when(clientRepositoryImpl.selectClient(clientId)).thenReturn(client);

        ClientDto expectedDto = new ClientDto(clientId, "Client 1", 1L);
        when(mapper.toDto(client)).thenReturn(expectedDto);

        ClientDto result = clientServiceImpl.getClientById(clientId);

        assertEquals(expectedDto, result);

        verify(clientRepositoryImpl, times(1)).selectClient(clientId);
        verify(mapper, times(1)).toDto(client);
    }

    @Test
    public void testSaveClient() throws SQLException {
        ClientDto inputDto = new ClientDto(1L, "Client 1", 1L);
        ClientEntity client = new ClientEntity(1L, "Client 1", 1L);

        when(mapper.fromDto(inputDto)).thenReturn(client);
        when(mapper.toDto(any(ClientEntity.class))).thenReturn(inputDto);

        ClientEntity savedClient = new ClientEntity(1L, "Client 1", 1L);
        when(clientRepositoryImpl.insertClient(client)).thenReturn(savedClient);

        ClientDto result = clientServiceImpl.saveClient(inputDto);

        assertEquals(inputDto, result);

        verify(mapper, times(1)).fromDto(inputDto);
        verify(mapper, times(1)).toDto(savedClient);
        verify(clientRepositoryImpl, times(1)).insertClient(client);
    }

    @Test
    public void testUpdateClient() throws SQLException {
        ClientDto inputDto = new ClientDto(1L, "Client 1", 1L);
        ClientEntity client = new ClientEntity(1L, "Client 1", 1L);

        when(mapper.fromDto(inputDto)).thenReturn(client);
        when(mapper.toDto(any(ClientEntity.class))).thenReturn(inputDto);

        ClientEntity updatedClient = new ClientEntity(1L, "Client 1", 1L);
        when(clientRepositoryImpl.updateClient(client)).thenReturn(updatedClient);

        ClientDto result = clientServiceImpl.updateClient(inputDto);

        assertEquals(inputDto, result);

        verify(mapper, times(1)).fromDto(inputDto);
        verify(clientRepositoryImpl, times(1)).updateClient(client);
    }

    @Test
    public void testRemoveClient() throws SQLException {
        Long id = 1L;

        when(clientRepositoryImpl.deleteClient(id)).thenReturn(true);

        boolean result = clientServiceImpl.removeClient(id);

        assertTrue(result);

        verify(clientRepositoryImpl, times(1)).deleteClient(id);
    }
}
