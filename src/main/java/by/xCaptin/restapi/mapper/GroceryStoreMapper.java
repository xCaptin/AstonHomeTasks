package by.xCaptin.restapi.mapper;


import by.xCaptin.restapi.dto.GroceryStoreDto;
import by.xCaptin.restapi.entity.GroceryStoreEntity;

public class GroceryStoreMapper implements Mapper<GroceryStoreEntity, GroceryStoreDto>{
    @Override
    public GroceryStoreEntity fromDto(GroceryStoreDto dto) {
        return GroceryStoreEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
    @Override
    public GroceryStoreDto toDto(GroceryStoreEntity entity) {
        return GroceryStoreDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
