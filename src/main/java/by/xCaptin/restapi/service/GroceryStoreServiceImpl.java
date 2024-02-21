package by.xCaptin.restapi.service;

import by.xCaptin.restapi.dto.GroceryStoreDto;
import by.xCaptin.restapi.entity.GroceryStoreEntity;
import by.xCaptin.restapi.mapper.Mapper;
import by.xCaptin.restapi.repository.GroceryStoreRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GroceryStoreServiceImpl implements GroceryStoreService {

    private final GroceryStoreRepositoryImpl groceryStoreRepositoryImpl;
    private final Mapper<GroceryStoreEntity, GroceryStoreDto> mapper;

    public GroceryStoreServiceImpl(GroceryStoreRepositoryImpl groceryStoreRepositoryImpl, Mapper<GroceryStoreEntity, GroceryStoreDto> mapper) {
        this.groceryStoreRepositoryImpl = groceryStoreRepositoryImpl;
        this.mapper = mapper;
    }

    @Override
    public GroceryStoreDto getByIdProducts(Long id) throws SQLException {
        GroceryStoreEntity groceryStore = groceryStoreRepositoryImpl.selectGroceryStore(id);
        return mapper.toDto(groceryStore);
    }

    @Override
    public GroceryStoreDto saveProducts(GroceryStoreDto storeDto) throws SQLException {
        GroceryStoreEntity groceryStore = mapper.fromDto(storeDto);
        GroceryStoreEntity savedGroceryStore = groceryStoreRepositoryImpl.insertGroceryStore(groceryStore);
        return mapper.toDto(savedGroceryStore);
    }

    @Override
    public GroceryStoreDto updateProducts(GroceryStoreDto storeDto) throws SQLException {
        GroceryStoreEntity groceryStore = mapper.fromDto(storeDto);
        GroceryStoreEntity updatedGroceryStore = groceryStoreRepositoryImpl.updateGroceryStore(groceryStore);
        return mapper.toDto(updatedGroceryStore);
    }


    @Override
    public boolean removeProducts(Long id) throws SQLException {
        return groceryStoreRepositoryImpl.deleteGroceryStore(id);
    }

    @Override
    public List<GroceryStoreDto> getAllProducts() throws SQLException {
        List<GroceryStoreEntity> groceryStoreList = groceryStoreRepositoryImpl.selectAllGroceryStore();
        return groceryStoreList.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
