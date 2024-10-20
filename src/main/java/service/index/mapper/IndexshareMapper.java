package service.index.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import service.index.dto.IndexshareDto;
import service.index.entity.IndexshareEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IndexshareMapper {

    @Mapping(target="index", ignore = true)
    @Mapping(target="id", ignore = true)
    IndexshareEntity to(IndexshareDto indexDto);

    IndexshareDto from(IndexshareEntity indexEntity);
}
