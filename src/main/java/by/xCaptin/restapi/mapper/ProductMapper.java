package by.xCaptin.restapi.mapper;

import by.xCaptin.restapi.dto.ProductDTO;
import by.xCaptin.restapi.entity.ProductEntity;

public class ProductMapper implements Mapper<ProductEntity, ProductDTO>{

    @Override
    public ProductEntity fromDto(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }

    @Override
    public ProductDTO toDto(ProductEntity entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
