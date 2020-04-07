package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.LedgerAccountVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 总分类账实体
 *
 * @author HuanJu
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class LedgerAccount implements Serializable {

    private Integer id;
    /**
     * 科目ID
     */
    private Integer subjectId;
    /**
     * 科目
     */
    private Subject subject;
    /**
     * 日期
     */
    private Date date;
    /**
     * 凭证号
     */
    private Integer proofId;
    /**
     * 摘要
     */
    private String abstraction;
    /**
     * 借方金额
     */
    private BigDecimal debitMoney;
    /**
     * 贷方金额
     */
    private BigDecimal creditMoney;

    public LedgerAccountVO covert() {
        LedgerAccountVO leda = new LedgerAccountVO();
        leda.setId(this.id)
                .setDate(this.date)
                .setSubject(this.subject == null ? null : this.subject.covert())
                .setAbstraction(this.abstraction)
                .setProofId(this.proofId)
                .setDebitMoney(this.debitMoney)
                .setCreditMoney(this.creditMoney);
        return leda;
    }

    public static List<LedgerAccountVO> listCovert(List<LedgerAccount> sourcesList) {
        List<LedgerAccountVO> vos = new ArrayList<>(sourcesList.size());
        for (LedgerAccount ledgerAccount : sourcesList) {
            vos.add(ledgerAccount.covert());
        }
        return vos;
    }
}
