package huanju.chen.app.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class CashAccountVO implements Serializable {
    private Integer id;
    private Date date;
    private Integer proofId;
    private String abstraction;
    private SubjectVO subject;
    private BigDecimal debitMoney;
    private BigDecimal creditMoney;
    private BigDecimal money;

}
