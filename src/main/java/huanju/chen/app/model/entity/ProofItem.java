package huanju.chen.app.model.entity;

import java.math.BigDecimal;

/**
 * @author HuanJu
 */
public class  ProofItem {

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
     * 贷方明细账科目
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
     * 明细账科目ID
     */
    private Integer subSubjectId;

    /**
     * 明细账科目
     */
    private Subject subSubject;

    /**
     * 总账科目ID
     */
    private Integer ledgerSubjectId;

    /**
     * 总账科目
     */
    private Subject ledgerSubject;

    /**
     * 金额
     */
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

    public Integer getSubSubjectId() {
        return subSubjectId;
    }

    public void setSubSubjectId(Integer subSubjectId) {
        this.subSubjectId = subSubjectId;
    }

    public Subject getSubSubject() {
        return subSubject;
    }

    public void setSubSubject(Subject subSubject) {
        this.subSubject = subSubject;
    }

    public Integer getLedgerSubjectId() {
        return ledgerSubjectId;
    }

    public void setLedgerSubjectId(Integer ledgerSubjectId) {
        this.ledgerSubjectId = ledgerSubjectId;
    }

    public Subject getLedgerSubject() {
        return ledgerSubject;
    }

    public void setLedgerSubject(Subject ledgerSubject) {
        this.ledgerSubject = ledgerSubject;
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
}
