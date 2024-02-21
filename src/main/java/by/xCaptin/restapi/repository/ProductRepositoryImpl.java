package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.db.ConnectionPool;
import by.xCaptin.restapi.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRepositoryImpl implements ProductRepository {

    @Deprecated
    Connection connection;
    public static String INSERT_PRODUCTS_SQL = "INSERT INTO product (name, kcal) VALUES  (?, ?)";
    public static String SELECT_PRODUCTS_BY_ID = "SELECT id,name,kcal FROM product WHERE id =?";
    public static String SELECT_ALL_PRODUCTS = "SELECT * FROM product";
    public static String DELETE_PRODUCTS_SQL = "DELETE FROM product WHERE id = ?";
    public static String UPDATE_PRODUCTS_SQL = "UPDATE product SET name = ?,kcal= ? WHERE id = ?";

    public static String ID_COLUMN = "id";
    public static String NAME_COLUMN = "name";
    public static String KCAL_COLUMN = "kcal";

    public ProductRepositoryImpl() {
        try {
            connection = ConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ProductEntity insertProducts(ProductEntity product) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            System.out.println(product.getName());
            System.out.println(product.getKcal());
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getKcal());

            preparedStatement.executeUpdate();

            return product;
        }
    }

    @Override
    public ProductEntity updateProducts(ProductEntity product) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTS_SQL);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getKcal());
            preparedStatement.setLong(3, product.getId());

            preparedStatement.executeUpdate();

            return product;
        }
    }

    @Override
    public ProductEntity selectProducts(Long id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            ProductEntity product = null;

            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTS_BY_ID);

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String name = rs.getString(NAME_COLUMN);
                int kcal = rs.getInt(KCAL_COLUMN);
                product = new ProductEntity();
                product.setId(id);
                product.setName(name);
                product.setKcal(kcal);
            }

            return product;
        }
    }

    @Override
    public boolean deleteProducts(Long id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            String deleteQuery = DELETE_PRODUCTS_SQL;
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setLong(1, id);
            int result = deleteStatement.executeUpdate();
            return result > 0;
        }
    }

    @Override
    public List<ProductEntity> selectAllProducts() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            List<ProductEntity> productList = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(ID_COLUMN);
                String name = rs.getString(NAME_COLUMN);
                int kcal = rs.getInt(KCAL_COLUMN);
                ProductEntity product = new ProductEntity();
                product.setId(id);
                product.setName(name);
                product.setKcal(kcal);
                productList.add(product);
            }

            return productList;
        }
    }

}

