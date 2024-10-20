package service.index.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import service.index.dto.InputDto;
import service.index.entity.InputEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InputMapper {

    @Mapping(source="index", target="index")
    @Mapping(target="id", ignore = true)
    InputEntity to(InputDto inputDto);

    @Mapping(source="index", target="index")
    InputDto from(InputEntity inputEntity);
}
