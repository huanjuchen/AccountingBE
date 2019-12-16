package huanju.chen.app.model.entity;

import huanju.chen.app.model.vo.ProofItemVo;
import huanju.chen.app.model.vo.ProofVo;
import org.hibernate.validator.constraints.Length;

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
    private Date date;

    /**
     * 单据数
     */
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
     * 稽核
     */
    private Integer examinationId;

    private Examination examination;

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

    public Integer getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Integer examinationId) {
        this.examinationId = examinationId;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
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

    public ProofVo covert() {
        ProofVo proofVo = new ProofVo();
        proofVo.setId(this.id);
        proofVo.setDate(this.date);
        proofVo.setInvoiceCount(this.invoiceCount);
        proofVo.setManager(this.manager);
        proofVo.setCollection(this.collection);
        if (this.examination != null) {
            proofVo.setExamination(this.examination.covert());
        }


        if (this.recorder != null) {
            proofVo.setRecorder(this.recorder.covert());

        }

        proofVo.setCashier(this.cashier);
        proofVo.setPayer(this.payer);

        List<ProofItemVo> itemVos = null;
        if (this.items != null) {
            itemVos = new ArrayList<>(this.items.size());
            for (int i = 0; i < this.items.size(); i++) {
                itemVos.add(i, this.items.get(i).covert());
            }
            proofVo.setItems(itemVos);
        }

        return proofVo;
    }
}
