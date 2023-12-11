package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.db.DbConnect;
import by.xCaptin.restapi.entity.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    Connection connection;
    private static final String INSERT_PRODUCTS_SQL = "INSERT INTO shop (name, kcal) VALUES  (?, ?);";
    private static final String SELECT_PRODUCTS_BY_ID = "SELECT id,name,kcal FROM shop WHERE id =?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM shop;";
    private static final String DELETE_PRODUCTS_SQL = "DELETE FROM shop WHERE id = ?;";
    private static final String UPDATE_PRODUCTS_SQL = "UPDATE shop SET name = ?,kcal= ? WHERE id = ?;";

    public ProductRepository() {
        try {
            connection = DbConnect.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    public ProductEntity insertProducts(ProductEntity product) throws SQLException {
        String getMaxIdQuery = "SELECT MAX(id) FROM shop";
        Statement getMaxIdStatement = connection.createStatement();
        ResultSet resultSet = getMaxIdStatement.executeQuery(getMaxIdQuery);
        int maxId = 0;
        if (resultSet.next()) {
            maxId = resultSet.getInt(1);
        }
        resultSet.close();
        getMaxIdStatement.close();

        String resetAutoIncrementQuery = "ALTER TABLE shop AUTO_INCREMENT = " + (maxId + 1);
        Statement resetAutoIncrementStatement = connection.createStatement();
        resetAutoIncrementStatement.executeUpdate(resetAutoIncrementQuery);
        resetAutoIncrementStatement.close();

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setInt(2, product.getKcal());
        preparedStatement.executeUpdate();
        return product;
    }

    public ProductEntity updateProducts(ProductEntity product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_SQL);

        statement.setString(1, product.getName());
        statement.setInt(2, product.getKcal());
        statement.setLong(3, product.getId());

        statement.executeUpdate();

       return product;
    }

    public ProductEntity selectProducts(Long id) throws SQLException {
        ProductEntity product = null;

        PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTS_BY_ID);

        statement.setLong(1, id);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String name = rs.getString("name");
            int kcal = rs.getInt("kcal");
            product = new ProductEntity();
            product.setId(id);
            product.setName(name);
            product.setKcal(kcal);
        }

        return product;
    }

    public List<ProductEntity> selectAllProducts() throws SQLException {
        List<ProductEntity> productList = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int kcal = rs.getInt("kcal");
            ProductEntity product = new ProductEntity();
            product.setId(id);
            product.setName(name);
            product.setKcal(kcal);
            productList.add(product);
        }

        return productList;
    }

    public boolean deleteProducts(Long id) throws SQLException {
        String deleteQuery = DELETE_PRODUCTS_SQL;
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        deleteStatement.setLong(1, id);
        int result = deleteStatement.executeUpdate();
        deleteStatement.close();

        String selectQuery = "SELECT id FROM shop ORDER BY id";
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);

        int newId = 1;
        while (resultSet.next()) {
            int currentId = resultSet.getInt("id");
            if (currentId != newId) {
                String updateQuery = "UPDATE shop SET id = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, newId);
                updateStatement.setInt(2, currentId);
                updateStatement.executeUpdate();
                updateStatement.close();
            }
            newId++;
        }

        return result > 0;
    }

}

