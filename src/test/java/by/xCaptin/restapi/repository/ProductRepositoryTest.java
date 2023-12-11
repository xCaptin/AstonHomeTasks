package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.db.DbConnect;
import by.xCaptin.restapi.entity.ProductEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductRepositoryTest {

    private Connection connection;
    private ProductRepository productRepository;

    @Before
    public void setup() throws SQLException {
        connection = DbConnect.getConnection();
        productRepository = new ProductRepository(connection);
    }

    @After
    public void tearDown() throws SQLException {
        String deleteQuery = "DELETE FROM shop";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteQuery);
        }
        connection.close();
    }

    @Test
    public void testInsertProducts() throws SQLException {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setKcal(100);

        ProductEntity insertedProduct = productRepository.insertProducts(product);

        assertNotNull(insertedProduct);
        assertNotNull(insertedProduct.getId());
        assertEquals("Test Product", insertedProduct.getName());
        assertEquals(100, insertedProduct.getKcal());
    }

    @Test
    public void testUpdateProducts() throws SQLException {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setKcal(100);

        ProductEntity insertedProduct = productRepository.insertProducts(product);

        insertedProduct.setName("Updated Product");
        insertedProduct.setKcal(200);

        ProductEntity updatedProduct = productRepository.updateProducts(insertedProduct);

        assertNotNull(updatedProduct);
        assertEquals(insertedProduct.getId(), updatedProduct.getId());
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(200, updatedProduct.getKcal());
    }

    @Test
    public void testSelectProducts() throws SQLException {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setKcal(100);

        ProductEntity insertedProduct = productRepository.insertProducts(product);

        ProductEntity selectedProduct = productRepository.selectProducts(insertedProduct.getId() + 1);

        assertNotNull(selectedProduct);
        assertEquals(insertedProduct.getId() + 1, selectedProduct.getId());
        assertEquals("Test Product", selectedProduct.getName());
        assertEquals(100, selectedProduct.getKcal());
    }

    @Test
    public void testDeleteProducts() throws SQLException {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setKcal(100);

        ProductEntity insertedProduct = productRepository.insertProducts(product);

        boolean result = productRepository.deleteProducts(insertedProduct.getId() + 1);

        assertEquals(true, result);
    }
}