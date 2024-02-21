package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.db.ConnectionPool;
import by.xCaptin.restapi.entity.ClientEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {

    @Deprecated
    private final Connection connection;

    private static final String INSERT_CLIENT_SQL = "INSERT INTO client (name, groseryStoreID) VALUES (?, ?)";
    private static final String SELECT_CLIENT_BY_ID = "SELECT * FROM client WHERE id = ?";
    private static final String SELECT_ALL_CLIENTS = "SELECT * FROM client";
    private static final String DELETE_CLIENT_SQL = "DELETE FROM client WHERE id = ?";
    private static final String UPDATE_CLIENT_SQL = "UPDATE client SET name = ?,groceryStoreID = ? WHERE id = ?";

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String STORE_COLUMN = "groceryStoreID";

    public ClientRepositoryImpl() {
        try {
            connection = ConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ClientEntity insertClient(ClientEntity client) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT_SQL);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setLong(2, client.getGroceryStoreID());

            preparedStatement.executeUpdate();

            return client;
        }
    }

    @Override
    public ClientEntity updateClient(ClientEntity client) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL);

            preparedStatement.setString(1, client.getName());
            preparedStatement.setLong(2, client.getGroceryStoreID());
            preparedStatement.setLong(3, client.getId());

            preparedStatement.executeUpdate();

            return client;
        }
    }

    @Override
    public ClientEntity selectClient(Long id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            ClientEntity client = null;

            PreparedStatement statement = connection.prepareStatement(SELECT_CLIENT_BY_ID);

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String name = rs.getString(NAME_COLUMN);
                long groceryStoreID = rs.getLong(STORE_COLUMN);
                client = new ClientEntity();
                client.setId(id);
                client.setName(name);
                client.setGroceryStoreID(groceryStoreID);
            }

            return client;
        }
    }

    @Override
    public boolean deleteClient(Long id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement deleteStatement = connection.prepareStatement(DELETE_CLIENT_SQL);
            deleteStatement.setLong(1, id);
            int result = deleteStatement.executeUpdate();
            return result > 0;
        }
    }

    @Override
    public List<ClientEntity> selectAllClients() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            List<ClientEntity> clientList = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CLIENTS);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(ID_COLUMN);
                String name = rs.getString(NAME_COLUMN);
                Long groceryStoreID = rs.getLong(STORE_COLUMN);
                ClientEntity client = new ClientEntity();
                client.setId(id);
                client.setName(name);
                client.setGroceryStoreID(groceryStoreID);
                clientList.add(client);
            }

            return clientList;
        }
    }
}