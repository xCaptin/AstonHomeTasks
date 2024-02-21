package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.GroceryStoreDto;

import java.sql.SQLException;
import java.util.List;
public interface GroceryStoreService {

    GroceryStoreDto getByIdProducts(Long id) throws SQLException;

    GroceryStoreDto saveProducts(GroceryStoreDto storeDto) throws SQLException;

    GroceryStoreDto updateProducts(GroceryStoreDto storeDto) throws SQLException;

    boolean removeProducts(Long id) throws SQLException;

    List<GroceryStoreDto> getAllProducts() throws SQLException;
}
