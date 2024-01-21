package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.entity.ProductEntity;

import java.sql.SQLException;
import java.util.List;
public interface ProductRepository {
    ProductEntity insertProducts(ProductEntity product) throws SQLException;

    ProductEntity updateProducts(ProductEntity product) throws SQLException;

    ProductEntity selectProducts(Long id) throws SQLException;

    boolean deleteProducts(Long id) throws SQLException;

    List<ProductEntity> selectAllProducts() throws SQLException;
}

