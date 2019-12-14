package huanju.chen.app.model.vo;

import java.math.BigDecimal;

public class ProofItemVo {

    private Integer id;

    /**
     * 摘要
     */
    private String abstraction;

    /**
     * 借方明细账科目
     */
    private SubjectVo debitSubSubject;


    /**
     * 贷方明细账科目
     */
    private SubjectVo creditSubSubject;

    /**
     * 借方总账科目
     */
    private SubjectVo debitLedgerSubject;


    /**
     * 贷方总账 科目
     */
    private SubjectVo creditLedgerSubject;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 记账标识
     */
    private Boolean charge;


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

    public SubjectVo getDebitSubSubject() {
        return debitSubSubject;
    }

    public void setDebitSubSubject(SubjectVo debitSubSubject) {
        this.debitSubSubject = debitSubSubject;
    }

    public SubjectVo getCreditSubSubject() {
        return creditSubSubject;
    }

    public void setCreditSubSubject(SubjectVo creditSubSubject) {
        this.creditSubSubject = creditSubSubject;
    }

    public SubjectVo getDebitLedgerSubject() {
        return debitLedgerSubject;
    }

    public void setDebitLedgerSubject(SubjectVo debitLedgerSubject) {
        this.debitLedgerSubject = debitLedgerSubject;
    }

    public SubjectVo getCreditLedgerSubject() {
        return creditLedgerSubject;
    }

    public void setCreditLedgerSubject(SubjectVo creditLedgerSubject) {
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
}
