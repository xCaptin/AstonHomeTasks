package by.xCaptin.restapi.mapper;

public interface Mapper <E, D>{
    E fromDto(D dto);

    D toDto(E entity);
}
