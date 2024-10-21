package service.index.dto;

import lombok.Data;

@Data
public class DeletionOperationDto {
    String operationType;
    String shareName;
    String indexName;
}
