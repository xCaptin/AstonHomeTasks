package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.ProductDTO;
import by.xCaptin.restapi.entity.ProductEntity;
import by.xCaptin.restapi.mapper.Mapper;
import by.xCaptin.restapi.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;
    private final Mapper<ProductEntity, ProductDTO> mapper;

    public ProductService(ProductRepository productRepository, Mapper<ProductEntity, ProductDTO> mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<ProductDTO> getAllProducts() throws SQLException {
        List<ProductEntity> productList = productRepository.selectAllProducts();
        return productList.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    public ProductDTO getByIdProducts(Long id) throws SQLException {
        ProductEntity product = productRepository.selectProducts(id);
        return mapper.toDto(product);
    }

    public ProductDTO saveProducts(ProductDTO marketDto) throws SQLException {
        ProductEntity product = mapper.fromDto(marketDto);
        ProductEntity savedMarket = productRepository.insertProducts(product);
        return mapper.toDto(savedMarket);
    }

    public ProductDTO updateProducts(ProductDTO marketDto) throws SQLException {
        ProductEntity product = mapper.fromDto(marketDto);
        ProductEntity updatedMarket = productRepository.updateProducts(product);
        return mapper.toDto(updatedMarket);
    }

    public boolean removeProducts(Long id) throws SQLException {
        return productRepository.deleteProducts(id);
    }
}

