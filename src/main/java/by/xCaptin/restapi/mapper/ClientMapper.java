package by.xCaptin.restapi.mapper;

import by.xCaptin.restapi.dto.ClientDto;
import by.xCaptin.restapi.entity.ClientEntity;


public class ClientMapper implements Mapper<ClientEntity, ClientDto> {

    @Override
    public ClientEntity fromDto(ClientDto dto) {
        return ClientEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .groceryStoreID(dto.getGroceryStoreID())
                .build();
    }

    @Override
    public ClientDto toDto(ClientEntity entity) {
        return ClientDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .groceryStoreID(entity.getGroceryStoreID())
                .build();
    }
}
