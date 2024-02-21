package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.ProductDto;

import java.sql.SQLException;
import java.util.List;
public interface ProductService {

    ProductDto getByIdProducts(Long id) throws SQLException;

    ProductDto saveProducts(ProductDto productDto) throws SQLException;

    ProductDto updateProducts(ProductDto productDto) throws SQLException;

    boolean removeProducts(Long id) throws SQLException;

    List<ProductDto> getAllProducts() throws SQLException;
}

