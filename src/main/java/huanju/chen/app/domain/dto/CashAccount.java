package huanju.chen.app.domain.dto;


import huanju.chen.app.domain.vo.CashAccountVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行日记账
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class CashAccount implements Serializable {

    private Integer id;

    private Date date;

    private Integer proofId;

    private String abstraction;

    private Integer subjectId;

    private Subject subject;

    private BigDecimal debitMoney;

    private BigDecimal creditMoney;

    public CashAccountVO covert() {
        CashAccountVO cashAccountVO = new CashAccountVO();
        cashAccountVO.setId(this.id)
                .setDate(this.date)
                .setProofId(this.proofId)
                .setAbstraction(this.abstraction)
                .setSubject(this.subject != null ? this.subject.covert() : null)
                .setDebitMoney(this.debitMoney)
                .setCreditMoney(this.creditMoney);
        return cashAccountVO;
    }
}
