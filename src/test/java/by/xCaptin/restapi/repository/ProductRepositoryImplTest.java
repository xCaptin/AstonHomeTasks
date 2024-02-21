package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.db.ConnectionPool;
import by.xCaptin.restapi.entity.ProductEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryImplTest {

    @BeforeAll
    static void init() {
        ConnectionPool.init();
    }

    @Test
    void insertProducts() {
        ProductRepository productRepository = new ProductRepositoryImpl();

        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setKcal(100);

        try {
            ProductEntity insertedProduct = productRepository.insertProducts(product);

            assertNotNull(insertedProduct.getId());
        } catch (SQLException e) {
            fail("Exception thrown during insertProducts");
        }
    }

    @Test
    void updateProducts() {
        ProductRepository productRepository = new ProductRepositoryImpl();

        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("Updated Product");
        product.setKcal(150);

        try {
            ProductEntity updatedProduct = productRepository.updateProducts(product);

            assertNotNull(updatedProduct);
            assertEquals("Updated Product", updatedProduct.getName());
            assertEquals(150, updatedProduct.getKcal());
        } catch (SQLException e) {
            fail("Exception thrown during updateProducts");
        }
    }

    @Test
    void selectProducts() {
        ProductRepository productRepository = new ProductRepositoryImpl();

        Long productId = 1L;

        try {
            ProductEntity selectedProduct = productRepository.selectProducts(productId);

            assertNotNull(selectedProduct);
            assertEquals(productId, selectedProduct.getId());
        } catch (SQLException e) {
            fail("Exception thrown during selectProducts");
        }
    }

    @Test
    void deleteProducts() {
        ProductRepository productRepository = new ProductRepositoryImpl();

        Long productId = 1L;

        try {
            boolean isDeleted = productRepository.deleteProducts(productId);

            assertTrue(isDeleted);
        } catch (SQLException e) {
            fail("Exception thrown during deleteProducts");
        }
    }

    @Test
    void selectAllProducts() {
        ProductRepository productRepository = new ProductRepositoryImpl();

        try {
            List<ProductEntity> productList = productRepository.selectAllProducts();

            assertNotNull(productList);
            assertFalse(productList.isEmpty());
        } catch (SQLException e) {
            fail("Exception thrown during selectAllProducts");
        }
    }
}
