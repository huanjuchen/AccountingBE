package huanju.chen.app.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 总分类账实体
 *
 * @author HuanJu
 */
public class LedgerAccount implements Serializable {

    private Integer id;


    /**
     * 日期
     */
    private Date dates;


    /**
     * 科目ID
     */
    private Integer subjectId;

    /**
     * 科目
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


    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 借或贷，true为借，false为贷
     */
    private Boolean debit;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getProofId() {
        return proofId;
    }

    public void setProofId(Integer proofId) {
        this.proofId = proofId;
    }

    public String getAbstraction() {
        return abstraction;
    }

    public void setAbstraction(String abstraction) {
        this.abstraction = abstraction;
    }

    public BigDecimal getDebitMoney() {
        return debitMoney;
    }

    public void setDebitMoney(BigDecimal debitMoney) {
        this.debitMoney = debitMoney;
    }

    public BigDecimal getCreditMoney() {
        return creditMoney;
    }

    public void setCreditMoney(BigDecimal creditMoney) {
        this.creditMoney = creditMoney;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getDebit() {
        return debit;
    }

    public void setDebit(Boolean debit) {
        this.debit = debit;
    }
}
