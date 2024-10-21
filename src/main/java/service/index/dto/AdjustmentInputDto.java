package service.index.dto;

import lombok.Data;

@Data
public class AdjustmentInputDto {
    AdditionOperationDto additionOperation;
    DeletionOperationDto deletionOperation;
    DividendOperationDto dividendOperation;
}
