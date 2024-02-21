package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.GroceryStoreDto;
import by.xCaptin.restapi.entity.GroceryStoreEntity;
import by.xCaptin.restapi.mapper.Mapper;
import by.xCaptin.restapi.repository.GroceryStoreRepositoryImpl;
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

public class GroceryStoreServiceImplTest {

    @Mock
    private GroceryStoreRepositoryImpl groceryStoreRepositoryImpl;

    @Mock
    private Mapper<GroceryStoreEntity, GroceryStoreDto> mapper;

    private GroceryStoreServiceImpl groceryStoreServiceImpl;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        groceryStoreServiceImpl = new GroceryStoreServiceImpl(groceryStoreRepositoryImpl, mapper);
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        List<GroceryStoreEntity> storeList = new ArrayList<>();
        storeList.add(new GroceryStoreEntity(1L, "Store 1"));
        storeList.add(new GroceryStoreEntity(2L, "Store 2"));
        when(groceryStoreRepositoryImpl.selectAllGroceryStore()).thenReturn(storeList);

        List<GroceryStoreDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new GroceryStoreDto(1L, "Store 1"));
        expectedDtoList.add(new GroceryStoreDto(2L, "Store 2"));
        when(mapper.toDto(any(GroceryStoreEntity.class))).thenAnswer(invocation -> {
            GroceryStoreEntity entity = invocation.getArgument(0);
            return new GroceryStoreDto(entity.getId(), entity.getName());
        });

        List<GroceryStoreDto> result = groceryStoreServiceImpl.getAllProducts();

        assertEquals(expectedDtoList, result);

        verify(groceryStoreRepositoryImpl, times(1)).selectAllGroceryStore();
        verify(mapper, times(2)).toDto(any(GroceryStoreEntity.class));
    }

    @Test
    public void testGetByIdProducts() throws SQLException {
        Long storeId = 1L;
        GroceryStoreEntity store = new GroceryStoreEntity(storeId, "Store 1");
        when(groceryStoreRepositoryImpl.selectGroceryStore(storeId)).thenReturn(store);

        GroceryStoreDto expectedDto = new GroceryStoreDto(storeId, "Store 1");
        when(mapper.toDto(store)).thenReturn(expectedDto);

        GroceryStoreDto result = groceryStoreServiceImpl.getByIdProducts(storeId);

        assertEquals(expectedDto, result);

        verify(groceryStoreRepositoryImpl, times(1)).selectGroceryStore(storeId);
        verify(mapper, times(1)).toDto(store);
    }

    @Test
    public void testSaveProducts() throws SQLException {
        GroceryStoreDto inputDto = new GroceryStoreDto(1L, "Store 1");
        GroceryStoreEntity store = new GroceryStoreEntity(1L, "Store 1");

        when(mapper.fromDto(inputDto)).thenReturn(store);
        when(mapper.toDto(any(GroceryStoreEntity.class))).thenReturn(inputDto);

        GroceryStoreEntity savedStore = new GroceryStoreEntity(1L, "Store 1");
        when(groceryStoreRepositoryImpl.insertGroceryStore(store)).thenReturn(savedStore);

        GroceryStoreDto result = groceryStoreServiceImpl.saveProducts(inputDto);

        assertEquals(inputDto, result);

        verify(mapper, times(1)).fromDto(inputDto);
        verify(mapper, times(1)).toDto(savedStore);
        verify(groceryStoreRepositoryImpl, times(1)).insertGroceryStore(store);
    }

    @Test
    public void testUpdateProducts() throws SQLException {
        GroceryStoreDto inputDto = new GroceryStoreDto(1L, "Store 1");
        GroceryStoreEntity store = new GroceryStoreEntity(1L, "Store 1");

        when(mapper.fromDto(inputDto)).thenReturn(store);
        when(mapper.toDto(any(GroceryStoreEntity.class))).thenReturn(inputDto);

        GroceryStoreEntity updatedStore = new GroceryStoreEntity(1L, "Store 1");
        when(groceryStoreRepositoryImpl.updateGroceryStore(store)).thenReturn(updatedStore);

        GroceryStoreDto result = groceryStoreServiceImpl.updateProducts(inputDto);

        assertEquals(inputDto, result);

        verify(mapper, times(1)).fromDto(inputDto);
        verify(groceryStoreRepositoryImpl, times(1)).updateGroceryStore(store);
    }

    @Test
    public void testRemoveProducts() throws SQLException {
        Long id = 1L;

        when(groceryStoreRepositoryImpl.deleteGroceryStore(id)).thenReturn(true);

        boolean result = groceryStoreServiceImpl.removeProducts(id);

        assertTrue(result);

        verify(groceryStoreRepositoryImpl, times(1)).deleteGroceryStore(id);
    }
}
