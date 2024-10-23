package service.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentInputDto {
    AdditionOperationDto additionOperation;
    DeletionOperationDto deletionOperation;
    DividendOperationDto dividendOperation;
}
