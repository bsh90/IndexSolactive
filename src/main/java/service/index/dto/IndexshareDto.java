package service.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexshareDto {
    String shareName;
    Double sharePrice;
    Double numberOfshares;
}
