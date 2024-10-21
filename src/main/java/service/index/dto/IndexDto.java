package service.index.dto;

import lombok.Data;

import java.util.List;

@Data
public class IndexDto {
    String indexName;
    List<IndexshareDto> indexshares;
}
