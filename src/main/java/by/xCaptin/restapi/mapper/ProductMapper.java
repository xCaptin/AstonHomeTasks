package by.xCaptin.restapi.mapper;

import by.xCaptin.restapi.dto.ProductDto;
import by.xCaptin.restapi.entity.ProductEntity;

public class ProductMapper implements Mapper<ProductEntity, ProductDto>{
    @Override
    public ProductEntity fromDto(ProductDto dto) {
        return ProductEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .kcal(dto.getKcal())
                .build();
    }
    @Override
    public ProductDto toDto(ProductEntity entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .kcal(entity.getKcal())
                .build();
    }
}
