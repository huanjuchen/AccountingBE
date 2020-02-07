package huanju.chen.app.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LedgerAccountVO implements Serializable {
    private Integer id;

    private SubjectVO subject;

    private Date date;

    private Integer proofId;

    private String abstraction;

    private BigDecimal debitMoney;

    private BigDecimal creditMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubjectVO getSubject() {
        return subject;
    }

    public void setSubject(SubjectVO subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
