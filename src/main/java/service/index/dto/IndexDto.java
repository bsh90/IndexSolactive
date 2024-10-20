package service.index.dto;

import lombok.Data;

import java.util.List;

@Data
public class IndexDto {
    public String indexName;
    public List<IndexshareDto> indexshares;
}
