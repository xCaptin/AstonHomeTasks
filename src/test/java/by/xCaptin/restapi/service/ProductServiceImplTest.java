package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.ProductDto;
import by.xCaptin.restapi.entity.ProductEntity;
import by.xCaptin.restapi.mapper.Mapper;
import by.xCaptin.restapi.repository.ProductRepositoryImpl;
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

public class ProductServiceImplTest {
    @Mock
    private ProductRepositoryImpl productRepositoryImpl;

    @Mock
    private Mapper<ProductEntity, ProductDto> mapper;

    private ProductServiceImpl productServiceImpl;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productServiceImpl = new ProductServiceImpl(productRepositoryImpl, mapper);
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(new ProductEntity(1L, "Product 1", 100));
        productList.add(new ProductEntity(2L, "Product 2", 200));
        when(productRepositoryImpl.selectAllProducts()).thenReturn(productList);

        List<ProductDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new ProductDto(1L, "Product 1", 100));
        expectedDtoList.add(new ProductDto(2L, "Product 2", 200));
        when(mapper.toDto(any(ProductEntity.class))).thenAnswer(invocation -> {
            ProductEntity entity = invocation.getArgument(0);
            return new ProductDto(entity.getId(), entity.getName(), entity.getKcal());
        });

        List<ProductDto> result = productServiceImpl.getAllProducts();

        assertEquals(expectedDtoList, result);

        verify(productRepositoryImpl, times(1)).selectAllProducts();
        verify(mapper, times(2)).toDto(any(ProductEntity.class));
    }

    @Test
    public void testGetByIdProducts() throws SQLException {
        Long productId = 1L;
        ProductEntity product = new ProductEntity(productId, "Product 1", 100);
        when(productRepositoryImpl.selectProducts(productId)).thenReturn(product);

        ProductDto expectedDto = new ProductDto(productId, "Product 1", 100);
        when(mapper.toDto(product)).thenReturn(expectedDto);

        ProductDto result = productServiceImpl.getByIdProducts(productId);

        assertEquals(expectedDto, result);

        verify(productRepositoryImpl, times(1)).selectProducts(productId);
        verify(mapper, times(1)).toDto(product);
    }

    @Test
    public void testSaveProducts() throws SQLException {
        ProductDto inputDto = new ProductDto(1L, "Product 1", 100);
        ProductEntity product = new ProductEntity(1L, "Product 1", 100);

        when(mapper.fromDto(inputDto)).thenReturn(product);
        when(mapper.toDto(any(ProductEntity.class))).thenReturn(inputDto);

        ProductEntity savedProduct = new ProductEntity(1L, "Product 1", 100);
        when(productRepositoryImpl.insertProducts(product)).thenReturn(savedProduct);

        ProductDto result = productServiceImpl.saveProducts(inputDto);

        assertEquals(inputDto, result);

        verify(mapper, times(1)).fromDto(inputDto);
        verify(mapper, times(1)).toDto(savedProduct); // Проверка вызова метода toDto с сохраненным продуктом
        verify(productRepositoryImpl, times(1)).insertProducts(product);
    }

    @Test
    public void testUpdateProducts() throws SQLException {
        ProductDto inputDto = new ProductDto(1L, "Product 1", 100);
        ProductEntity product = new ProductEntity(1L, "Product 1", 100);

        when(mapper.fromDto(inputDto)).thenReturn(product);
        when(mapper.toDto(any(ProductEntity.class))).thenReturn(inputDto);

        ProductEntity updatedProduct = new ProductEntity(1L, "Product 1", 100);
        when(productRepositoryImpl.updateProducts(product)).thenReturn(updatedProduct);

        ProductDto result = productServiceImpl.updateProducts(inputDto);

        assertEquals(inputDto, result);

        verify(mapper, times(1)).fromDto(inputDto);
        verify(productRepositoryImpl, times(1)).updateProducts(product);
    }

    @Test
    public void testRemoveProducts() throws SQLException {
        Long id = 1L;

        when(productRepositoryImpl.deleteProducts(id)).thenReturn(true);

        boolean result = productServiceImpl.removeProducts(id);

        assertTrue(result);

        verify(productRepositoryImpl, times(1)).deleteProducts(id);
    }
}
