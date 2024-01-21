package by.xCaptin.restapi.repository;


import by.xCaptin.restapi.entity.GroceryStoreEntity;

import java.sql.SQLException;
import java.util.List;

public interface GroceryStoreRepository {
    GroceryStoreEntity insertGroceryStore(GroceryStoreEntity store) throws SQLException;

    GroceryStoreEntity updateGroceryStore(GroceryStoreEntity store) throws SQLException;

    GroceryStoreEntity selectGroceryStore(Long id) throws SQLException;

    boolean deleteGroceryStore(Long id) throws SQLException;

    List<GroceryStoreEntity> selectAllGroceryStore() throws SQLException;
}
