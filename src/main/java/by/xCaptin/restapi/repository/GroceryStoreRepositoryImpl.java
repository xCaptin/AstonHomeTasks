package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.db.ConnectionPool;
import by.xCaptin.restapi.entity.GroceryStoreEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroceryStoreRepositoryImpl implements GroceryStoreRepository {

    @Deprecated
    Connection connection;
    static String INSERT_STORE_SQL = "INSERT INTO product (name) VALUES  (?)";
    static String SELECT_STORE_BY_ID = "SELECT id,name FROM product WHERE id =?";
    static String SELECT_ALL_STORE = "SELECT * FROM product";
    static String DELETE_STORE_SQL = "DELETE FROM product WHERE id = ?";
    static String UPDATE_STORE_SQL = "UPDATE product SET name = ? WHERE id = ?";

    static String ID_COLUMN = "id";
    static String NAME_COLUMN = "name";

    public GroceryStoreRepositoryImpl() {
        try {
            connection = ConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GroceryStoreRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public GroceryStoreEntity insertGroceryStore(GroceryStoreEntity groceryStore) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STORE_SQL);
            preparedStatement.setString(1, groceryStore.getName());

            preparedStatement.executeUpdate();

            return groceryStore;
        }
    }

    @Override
    public GroceryStoreEntity updateGroceryStore(GroceryStoreEntity groceryStore) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STORE_SQL);

            preparedStatement.setString(1, groceryStore.getName());
            preparedStatement.setLong(2, groceryStore.getId());

            preparedStatement.executeUpdate();

            return groceryStore;
        }
    }

    @Override
    public GroceryStoreEntity selectGroceryStore(Long id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            GroceryStoreEntity groceryStore = null;

            PreparedStatement statement = connection.prepareStatement(SELECT_STORE_BY_ID);

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String name = rs.getString(NAME_COLUMN);
                groceryStore = new GroceryStoreEntity();
                groceryStore.setId(id);
                groceryStore.setName(name);
            }

            return groceryStore;
        }
    }

    @Override
    public boolean deleteGroceryStore(Long id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            String deleteQuery = DELETE_STORE_SQL;
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setLong(1, id);
            int result = deleteStatement.executeUpdate();
            return result > 0;
        }
    }

    @Override
    public List<GroceryStoreEntity> selectAllGroceryStore() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            List<GroceryStoreEntity> groceryStoreList = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STORE);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(ID_COLUMN);
                String name = rs.getString(NAME_COLUMN);
                GroceryStoreEntity groceryStore = new GroceryStoreEntity();
                groceryStore.setId(id);
                groceryStore.setName(name);
                groceryStoreList.add(groceryStore);
            }

            return groceryStoreList;
        }
    }

}

