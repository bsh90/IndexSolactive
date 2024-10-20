package service.index.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import service.index.dto.IndexDto;
import service.index.entity.IndexEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IndexMapper {

    @Mapping(source="indexshares", target="indexshares")
    @Mapping(target="id", ignore = true)
    IndexEntity to(IndexDto indexDto);

    @Mapping(source="indexshares", target="indexshares")
    IndexDto from(IndexEntity indexEntity);
}
