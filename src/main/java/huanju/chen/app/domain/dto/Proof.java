package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.ProofItemVO;
import huanju.chen.app.domain.vo.ProofVO;

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


    public ProofVO covert() {
        ProofVO proofVo = new ProofVO();
        proofVo.setId(this.id);
        proofVo.setDate(this.date);
        proofVo.setInvoiceCount(this.invoiceCount);
        proofVo.setManager(this.manager);
        proofVo.setCollection(this.collection);
        if (this.recorder != null) {
            proofVo.setRecorder(this.recorder.covert());

        }
        proofVo.setCashier(this.cashier);
        proofVo.setPayer(this.payer);
        List<ProofItemVO> itemVos = null;
        if (this.items != null) {
            itemVos = new ArrayList<>(this.items.size());
            for (int i = 0; i < this.items.size(); i++) {
                itemVos.add(i, this.items.get(i).covert());
            }
            proofVo.setItems(itemVos);
        }

        proofVo.setVerify(this.verify);
        proofVo.setVerifyTime(this.verifyTime);
        if (this.verifyUser != null) {
            proofVo.setVerifyUser(this.verifyUser.covert());
        }
        return proofVo;
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

    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Integer getRecorderId() {
        return recorderId;
    }

    public void setRecorderId(Integer recorderId) {
        this.recorderId = recorderId;
    }

    public User getRecorder() {
        return recorder;
    }

    public void setRecorder(User recorder) {
        this.recorder = recorder;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public List<ProofItem> getItems() {
        return items;
    }

    public void setItems(List<ProofItem> items) {
        this.items = items;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public Integer getVerifyUserId() {
        return verifyUserId;
    }

    public void setVerifyUserId(Integer verifyUserId) {
        this.verifyUserId = verifyUserId;
    }

    public User getVerifyUser() {
        return verifyUser;
    }

    public void setVerifyUser(User verifyUser) {
        this.verifyUser = verifyUser;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }


}
