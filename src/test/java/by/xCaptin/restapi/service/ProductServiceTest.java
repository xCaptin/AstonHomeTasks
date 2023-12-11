package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.ProductDTO;
import by.xCaptin.restapi.entity.ProductEntity;
import by.xCaptin.restapi.mapper.Mapper;
import by.xCaptin.restapi.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private Mapper<ProductEntity, ProductDTO> mapper;

    private ProductService productService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, mapper);
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(new ProductEntity(1L, "Product 1", 100));
        productList.add(new ProductEntity(2L, "Product 2", 200));
        when(productRepository.selectAllProducts()).thenReturn(productList);

        List<ProductDTO> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new ProductDTO(1L, "Product 1", 100));
        expectedDtoList.add(new ProductDTO(2L, "Product 2", 200));
        when(mapper.toDto(any(ProductEntity.class))).thenAnswer(invocation -> {
            ProductEntity entity = invocation.getArgument(0);
            return new ProductDTO(entity.getId(), entity.getName(), entity.getKcal());
        });

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(expectedDtoList, result);

        verify(productRepository, times(1)).selectAllProducts();
        verify(mapper, times(2)).toDto(any(ProductEntity.class));
    }

    @Test
    public void testGetByIdProducts() throws SQLException {
        Long productId = 1L;
        ProductEntity product = new ProductEntity(productId, "Product 1", 100);
        when(productRepository.selectProducts(productId)).thenReturn(product);

        ProductDTO expectedDto = new ProductDTO(productId, "Product 1", 100);
        when(mapper.toDto(product)).thenReturn(expectedDto);

        ProductDTO result = productService.getByIdProducts(productId);

        assertEquals(expectedDto, result);

        verify(productRepository, times(1)).selectProducts(productId);
        verify(mapper, times(1)).toDto(product);
    }

    @Test
    public void testSaveProducts() throws SQLException {
        ProductDTO inputDto = new ProductDTO(1L, "Product 1", 100);
        ProductEntity product = new ProductEntity(1L, "Product 1", 100);

        when(mapper.fromDto(inputDto)).thenReturn(product);
        when(mapper.toDto(any(ProductEntity.class))).thenReturn(inputDto); // Настройка мока для метода toDto

        ProductEntity savedProduct = new ProductEntity(1L, "Product 1", 100);
        when(productRepository.insertProducts(product)).thenReturn(savedProduct);

        ProductDTO result = productService.saveProducts(inputDto);

        assertEquals(inputDto, result);

        verify(mapper, times(1)).fromDto(inputDto);
        verify(mapper, times(1)).toDto(savedProduct); // Проверка вызова метода toDto с сохраненным продуктом
        verify(productRepository, times(1)).insertProducts(product);
    }

    @Test
    public void testUpdateProducts() throws SQLException {
        ProductDTO inputDto = new ProductDTO(1L, "Product 1", 100);
        ProductEntity product = new ProductEntity(1L, "Product 1", 100);

        when(mapper.fromDto(inputDto)).thenReturn(product);
        when(mapper.toDto(any(ProductEntity.class))).thenReturn(inputDto);

        ProductEntity updatedProduct = new ProductEntity(1L, "Product 1", 100);
        when(productRepository.updateProducts(product)).thenReturn(updatedProduct);

        ProductDTO result = productService.updateProducts(inputDto);

        assertEquals(inputDto, result);

        verify(mapper, times(1)).fromDto(inputDto);
        verify(productRepository, times(1)).updateProducts(product);
    }

    @Test
    public void testRemoveProducts() throws SQLException {
        Long id = 1L;

        when(productRepository.deleteProducts(id)).thenReturn(true);

        boolean result = productService.removeProducts(id);

        assertTrue(result);

        verify(productRepository, times(1)).deleteProducts(id);
    }
}
