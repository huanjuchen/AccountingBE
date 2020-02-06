package huanju.chen.app.domain.dto;


import huanju.chen.app.domain.vo.BankAccountVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行日记账
 */
public class BankAccount {

    private Integer id;
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

    private Integer subjectId;
    /**
     * 对方科目
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

    public BankAccountVO covert() {
        BankAccountVO bankAccountVO = new BankAccountVO();
        bankAccountVO.setId(this.id);
        bankAccountVO.setDate(this.date);
        bankAccountVO.setProofId(this.proofId);
        bankAccountVO.setAbstraction(this.abstraction);
//        bankAccountVO.setSubject(this.subject);
        if (this.subject != null) {
            bankAccountVO.setSubject(this.subject.covert());
        }
        bankAccountVO.setDebitMoney(this.debitMoney);
        bankAccountVO.setCreditMoney(this.creditMoney);
        return bankAccountVO;
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
}
