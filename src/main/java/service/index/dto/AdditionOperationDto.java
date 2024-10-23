package service.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionOperationDto {
    String shareName;
    Double sharePrice;
    Double numberOfshares;
    String indexName;
}
