package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.ProofItemVO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HuanJu
 */
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbstraction() {
        return abstraction;
    }

    public void setAbstraction(String abstraction) {
        this.abstraction = abstraction;
    }

    public Integer getDebitSubSubjectId() {
        return debitSubSubjectId;
    }

    public void setDebitSubSubjectId(Integer debitSubSubjectId) {
        this.debitSubSubjectId = debitSubSubjectId;
    }

    public Subject getDebitSubSubject() {
        return debitSubSubject;
    }

    public void setDebitSubSubject(Subject debitSubSubject) {
        this.debitSubSubject = debitSubSubject;
    }

    public Integer getCreditSubSubjectId() {
        return creditSubSubjectId;
    }

    public void setCreditSubSubjectId(Integer creditSubSubjectId) {
        this.creditSubSubjectId = creditSubSubjectId;
    }

    public Subject getCreditSubSubject() {
        return creditSubSubject;
    }

    public void setCreditSubSubject(Subject creditSubSubject) {
        this.creditSubSubject = creditSubSubject;
    }

    public Integer getDebitLedgerSubjectId() {
        return debitLedgerSubjectId;
    }

    public void setDebitLedgerSubjectId(Integer debitLedgerSubjectId) {
        this.debitLedgerSubjectId = debitLedgerSubjectId;
    }

    public Subject getDebitLedgerSubject() {
        return debitLedgerSubject;
    }

    public void setDebitLedgerSubject(Subject debitLedgerSubject) {
        this.debitLedgerSubject = debitLedgerSubject;
    }

    public Integer getCreditLedgerSubjectId() {
        return creditLedgerSubjectId;
    }

    public void setCreditLedgerSubjectId(Integer creditLedgerSubjectId) {
        this.creditLedgerSubjectId = creditLedgerSubjectId;
    }

    public Subject getCreditLedgerSubject() {
        return creditLedgerSubject;
    }

    public void setCreditLedgerSubject(Subject creditLedgerSubject) {
        this.creditLedgerSubject = creditLedgerSubject;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Boolean getCharge() {
        return charge;
    }

    public void setCharge(Boolean charge) {
        this.charge = charge;
    }

    public Integer getProofId() {
        return proofId;
    }

    public void setProofId(Integer proofId) {
        this.proofId = proofId;
    }


    public ProofItemVO covert() {
        ProofItemVO proofItemVo = new ProofItemVO();

        proofItemVo.setId(this.id);
        proofItemVo.setAbstraction(this.abstraction);
        proofItemVo.setCreditLedgerSubject(this.creditLedgerSubject.covert());
        proofItemVo.setCreditSubSubject(this.creditSubSubject.covert());
        proofItemVo.setDebitLedgerSubject(this.debitLedgerSubject.covert());
        proofItemVo.setDebitSubSubject(this.debitSubSubject.covert());
        proofItemVo.setMoney(this.money);
        proofItemVo.setCharge(this.charge);
        return proofItemVo;


    }

}
