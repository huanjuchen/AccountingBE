package huanju.chen.app.domain.dto;


import huanju.chen.app.domain.vo.CashAccountVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行日记账
 */
public class CashAccount {

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
        cashAccountVO.setId(this.id);
        cashAccountVO.setDate(this.date);
        cashAccountVO.setProofId(this.proofId);
        cashAccountVO.setAbstraction(this.abstraction);
//        cashAccountVO.setSubject(thi);
        if (this.subject != null) {
            cashAccountVO.setSubject(this.subject.covert());
        }
        cashAccountVO.setDebitMoney(this.debitMoney);
        cashAccountVO.setCreditMoney(this.creditMoney);
        return cashAccountVO;
    }


    public Integer getProofId() {
        return proofId;
    }

    public void setProofId(Integer proofId) {
        this.proofId = proofId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
