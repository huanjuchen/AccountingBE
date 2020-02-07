package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.SubAccountVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 明细账实体
 *
 * @author HuanJu
 */
public class SubAccount implements Serializable {

    private Integer id;
    /**
     * 科目Id
     */
    private Integer subjectId;
    /**
     * 科目
     */
    private Subject subject;
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
    /**
     * 借方金额
     */
    private BigDecimal debitMoney;
    /**
     * 贷方金额
     */
    private BigDecimal creditMoney;


    public SubAccountVO covert(){
        SubAccountVO vo=new SubAccountVO();
        vo.setId(this.id);
        if (this.subject!=null){
            vo.setSubject(this.subject.covert());
        }
        vo.setDate(this.date);
        vo.setProofId(this.proofId);
        vo.setAbstraction(this.abstraction);
        vo.setDebitMoney(this.debitMoney);
        vo.setCreditMoney(this.creditMoney);
        return vo;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
