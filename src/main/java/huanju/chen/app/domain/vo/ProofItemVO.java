package huanju.chen.app.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProofItemVO implements Serializable {

    private Integer id;

    /**
     * 摘要
     */
    private String abstraction;

    /**
     * 借方明细账科目
     */
    private SubjectVO debitSubSubject;


    /**
     * 贷方明细账科目
     */
    private SubjectVO creditSubSubject;

    /**
     * 借方总账科目
     */
    private SubjectVO debitLedgerSubject;


    /**
     * 贷方总账 科目
     */
    private SubjectVO creditLedgerSubject;

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

    public SubjectVO getDebitSubSubject() {
        return debitSubSubject;
    }

    public void setDebitSubSubject(SubjectVO debitSubSubject) {
        this.debitSubSubject = debitSubSubject;
    }

    public SubjectVO getCreditSubSubject() {
        return creditSubSubject;
    }

    public void setCreditSubSubject(SubjectVO creditSubSubject) {
        this.creditSubSubject = creditSubSubject;
    }

    public SubjectVO getDebitLedgerSubject() {
        return debitLedgerSubject;
    }

    public void setDebitLedgerSubject(SubjectVO debitLedgerSubject) {
        this.debitLedgerSubject = debitLedgerSubject;
    }

    public SubjectVO getCreditLedgerSubject() {
        return creditLedgerSubject;
    }

    public void setCreditLedgerSubject(SubjectVO creditLedgerSubject) {
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
