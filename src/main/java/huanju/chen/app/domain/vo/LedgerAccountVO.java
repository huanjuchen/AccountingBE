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
public class LedgerAccountVO implements Serializable {
    private Integer id;

    private SubjectVO subject;

    private Date date;

    private String abstraction;

    private BigDecimal debitMoney;

    private BigDecimal creditMoney;

    private Integer mark;

    private BigDecimal money;
}
