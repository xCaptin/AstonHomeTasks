package by.xCaptin.restapi.repository;

import by.xCaptin.restapi.db.ConnectionPool;
import by.xCaptin.restapi.entity.GroceryStoreEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroceryStoreRepositoryImplTest {

    @BeforeAll
    static void init() {
        ConnectionPool.init();
    }

    @Test
    void insertGroceryStore() {
        GroceryStoreRepository groceryStoreRepository = new GroceryStoreRepositoryImpl();

        GroceryStoreEntity groceryStore = new GroceryStoreEntity();
        groceryStore.setName("Test Store");

        try {
            GroceryStoreEntity insertedStore = groceryStoreRepository.insertGroceryStore(groceryStore);

            assertNotNull(insertedStore.getId());
        } catch (SQLException e) {
            fail("Exception thrown during insertGroceryStore");
        }
    }

    @Test
    void updateGroceryStore() {
        GroceryStoreRepository groceryStoreRepository = new GroceryStoreRepositoryImpl();

        GroceryStoreEntity groceryStore = new GroceryStoreEntity();
        groceryStore.setId(1L);
        groceryStore.setName("Updated Store");

        try {
            GroceryStoreEntity updatedStore = groceryStoreRepository.updateGroceryStore(groceryStore);

            assertNotNull(updatedStore);
            assertEquals("Updated Store", updatedStore.getName());
        } catch (SQLException e) {
            fail("Exception thrown during updateGroceryStore");
        }
    }

    @Test
    void selectGroceryStore() {
        GroceryStoreRepository groceryStoreRepository = new GroceryStoreRepositoryImpl();

        Long storeId = 1L;

        try {
            GroceryStoreEntity selectedStore = groceryStoreRepository.selectGroceryStore(storeId);

            assertNotNull(selectedStore);
            assertEquals(storeId, selectedStore.getId());
        } catch (SQLException e) {
            fail("Exception thrown during selectGroceryStore");
        }
    }

    @Test
    void deleteGroceryStore() {
        GroceryStoreRepository groceryStoreRepository = new GroceryStoreRepositoryImpl();

        Long storeId = 1L;

        try {
            boolean isDeleted = groceryStoreRepository.deleteGroceryStore(storeId);

            assertTrue(isDeleted);
        } catch (SQLException e) {
            fail("Exception thrown during deleteGroceryStore");
        }
    }

    @Test
    void selectAllGroceryStore() {
        GroceryStoreRepository groceryStoreRepository = new GroceryStoreRepositoryImpl();

        try {
            List<GroceryStoreEntity> storeList = groceryStoreRepository.selectAllGroceryStore();

            assertNotNull(storeList);
            assertFalse(storeList.isEmpty());
        } catch (SQLException e) {
            fail("Exception thrown during selectAllGroceryStore");
        }
    }
}
