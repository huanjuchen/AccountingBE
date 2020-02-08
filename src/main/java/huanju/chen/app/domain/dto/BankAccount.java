package huanju.chen.app.domain.dto;


import huanju.chen.app.domain.vo.BankAccountVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 银行日记账
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class BankAccount implements Serializable {
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
        bankAccountVO.setId(this.id)
                .setDate(this.date)
                .setProofId(this.proofId)
                .setAbstraction(this.abstraction)
                .setSubject(this.subject != null ? this.subject.covert() : null)
                .setDebitMoney(this.debitMoney)
                .setCreditMoney(this.creditMoney);
        return bankAccountVO;
    }

    public static List<BankAccountVO> listCovert(List<BankAccount> bankAccountList){
        List<BankAccountVO> vos=new ArrayList<>(bankAccountList.size());
        for (int i = 0; i < bankAccountList.size(); i++) {
            vos.add(i,bankAccountList.get(i).covert());
        }
        return vos;
    }


}
