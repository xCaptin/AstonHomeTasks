package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.ProductDto;
import by.xCaptin.restapi.entity.ProductEntity;
import by.xCaptin.restapi.mapper.Mapper;
import by.xCaptin.restapi.repository.ProductRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepositoryImpl;
    private final Mapper<ProductEntity, ProductDto> mapper;

    public ProductServiceImpl(ProductRepositoryImpl productRepositoryImpl, Mapper<ProductEntity, ProductDto> mapper) {
        this.productRepositoryImpl = productRepositoryImpl;
        this.mapper = mapper;
    }

    @Override
    public ProductDto getByIdProducts(Long id) throws SQLException {
        ProductEntity product = productRepositoryImpl.selectProducts(id);
        return mapper.toDto(product);
    }

    @Override
    public ProductDto saveProducts(ProductDto marketDto) throws SQLException {
        ProductEntity product = mapper.fromDto(marketDto);
        ProductEntity savedProduct = productRepositoryImpl.insertProducts(product);
        return mapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProducts(ProductDto marketDto) throws SQLException {
        ProductEntity product = mapper.fromDto(marketDto);
        ProductEntity updatedMarket = productRepositoryImpl.updateProducts(product);
        return mapper.toDto(updatedMarket);
    }

    @Override
    public boolean removeProducts(Long id) throws SQLException {
        return productRepositoryImpl.deleteProducts(id);
    }

    @Override
    public List<ProductDto> getAllProducts() throws SQLException {
        List<ProductEntity> productList = productRepositoryImpl.selectAllProducts();
        return productList.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}

