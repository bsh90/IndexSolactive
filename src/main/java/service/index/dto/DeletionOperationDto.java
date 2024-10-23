package service.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletionOperationDto {
    String operationType;
    String shareName;
    String indexName;
}
