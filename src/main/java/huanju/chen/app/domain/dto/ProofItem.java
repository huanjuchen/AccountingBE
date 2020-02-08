package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.ProofItemVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HuanJu
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ProofItem implements Serializable {

    private Integer id;
    /**
     * 摘要
     */
    private String abstraction;
    /**
     * 借方明细账科目ID
     */
    private Integer debitSubSubjectId;
    /**
     * 借方明细账科目
     */
    private Subject debitSubSubject;
    /**
     * 贷方明细账科目ID
     */
    private Integer creditSubSubjectId;
    /**
     * 贷方明细账科目
     */
    private Subject creditSubSubject;
    /**
     * 借方总账科目ID
     */
    private Integer debitLedgerSubjectId;
    /**
     * 借方总账科目
     */
    private Subject debitLedgerSubject;
    /**
     * 贷方总账科目ID
     */
    private Integer creditLedgerSubjectId;
    /**
     * 贷方总账 科目
     */
    private Subject creditLedgerSubject;
    /**
     * 贷方总账科目ID
     */
    @NotNull
    private BigDecimal money;
    /**
     * 记账标识
     */
    private Boolean charge;
    /**
     * 关联列，凭证号
     */
    private Integer proofId;
    public ProofItemVO covert() {
        ProofItemVO proofItemVo = new ProofItemVO();
        proofItemVo.setId(this.id)
                .setAbstraction(this.abstraction)
                .setCreditLedgerSubject(this.creditLedgerSubject==null?null:this.creditLedgerSubject.covert())
                .setCreditSubSubject(this.creditSubSubject==null?null:this.creditSubSubject.covert())
                .setDebitLedgerSubject(this.debitLedgerSubject==null?null:this.debitLedgerSubject.covert())
                .setDebitSubSubject(this.debitSubSubject==null?null:this.debitSubSubject.covert())
                .setMoney(this.money)
                .setCharge(this.charge);
        return proofItemVo;
    }
}
