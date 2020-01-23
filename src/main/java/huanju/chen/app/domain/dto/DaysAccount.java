package huanju.chen.app.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 日记账实体
 *
 * @author HuanJu
 */
public class DaysAccount implements Serializable {

    private Integer id;


    /**
     * 日记账类型
     */
    private Boolean category;

    /**
     * 日期
     */
    private Date dates;

    /**
     * 凭证号
     */
    private Integer proofId;

    /**
     * 摘要
     */
    private String abstraction;

    /**
     * 科目Id
     */
    private Integer subjectId;

    /**
     * 科目
     */
    private Subject subject;

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

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
}