package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.EntityUtils;
import huanju.chen.app.domain.vo.ProofItemVO;
import huanju.chen.app.domain.vo.ProofVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 记账凭证实体
 *
 * @author HuanJu
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Proof implements Serializable {

    private Integer id;
    /**
     * 业务发生日期
     */
    @NotNull(message = "业务发生日期不允许为空")
    private Date date;
    /**
     * 单据数
     */
    @NotNull(message = "单据数不允许为空")
    private Integer invoiceCount;
    /**
     * 主管人员
     */
    private String manager;
    /**
     * 记账人
     */
    private String collection;
    /**
     * 制单人Id
     */
    private Integer recorderId;
    /**
     * 制单人
     */
    private User recorder;
    /**
     * 出纳人
     */
    private String cashier;
    /**
     * 交款人
     */
    private String payer;

    private List<ProofItem> items;
    /**
     * 稽查人ID
     */
    private Integer verifyUserId;

    private User verifyUser;
    /**
     * 稽查结果
     * 1.通过
     * 0.未审核
     * -1.未通过
     */
    private Integer verify;
    /**
     * 稽查时间
     */
    private Date verifyTime;

    /**
     * 冲账辨识
     */
    private Integer trash;

    public ProofVO covert() {
        ProofVO proofVo = new ProofVO();
        proofVo.setId(this.id)
                .setDate(this.date)
                .setInvoiceCount(this.invoiceCount)
                .setManager(this.manager)
                .setCollection(this.collection)
                .setRecorder(recorder == null ? null : this.recorder.covert())
                .setCashier(this.cashier)
                .setPayer(this.payer)
                .setItems(this.items == null ? null : EntityUtils.covertToProofItemVOList(this.items))
                .setVerify(this.verify)
                .setVerifyUser(this.verifyUser == null ? null : this.verifyUser.covert())
                .setVerifyTime(this.verifyTime)
                .setTrash(this.trash);
        return proofVo;
    }


}
