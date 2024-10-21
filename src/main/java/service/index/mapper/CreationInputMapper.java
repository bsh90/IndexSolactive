package service.index.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import service.index.dto.CreationInputDto;
import service.index.entity.CreationInputEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreationInputMapper {

    @Mapping(source="index", target="index")
    @Mapping(target="id", ignore = true)
    CreationInputEntity to(CreationInputDto creationInputDto);

    @Mapping(source="index", target="index")
    CreationInputDto from(CreationInputEntity creationInputEntity);
}
