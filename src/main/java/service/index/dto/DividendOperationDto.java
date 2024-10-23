package service.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DividendOperationDto {
    String operationType;
    String shareName;
    Double dividendValue;
}
