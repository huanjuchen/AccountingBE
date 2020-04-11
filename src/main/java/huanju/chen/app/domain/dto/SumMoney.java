package huanju.chen.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author HuanJu
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class SumMoney {
    private BigDecimal debitMoney;

    private BigDecimal creditMoney;


}
