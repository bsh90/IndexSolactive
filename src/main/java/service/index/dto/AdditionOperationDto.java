package service.index.dto;

import lombok.Data;

@Data
public class AdditionOperationDto {
    String shareName;
    Double sharePrice;
    Double numberOfshares;
    String indexName;
}
