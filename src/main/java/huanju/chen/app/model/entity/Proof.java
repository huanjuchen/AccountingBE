package huanju.chen.app.model.entity;

import huanju.chen.app.model.vo.ProofVo;

import java.util.Date;
import java.util.List;

/**
 * 记账凭证实体
 *
 * @author HuanJu
 */
public class Proof {

    private Integer id;

    /**
     * 凭证创建时间
     */
    private Date createTime;

    /**
     * 凭证制作人Id
     */
    private Integer recorderId;

    /**
     * 凭证制作人
     */
    private User recorder;

    /**
     * 凭证类别
     * 1.收款记账凭证
     * 2.付款记账凭证
     * 3.转账记账凭证
     */
    private Integer category;


    /**
     * 审核
     */
    private Integer examinationId;

    private Examination examination;

    private List<ProofItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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

    public List<ProofItem> getItems() {
        return items;
    }

    public void setItems(List<ProofItem> items) {
        this.items = items;
    }


    public ProofVo covert() {
        ProofVo proofVo = new ProofVo();
        proofVo.setId(this.id);
        proofVo.setCreateTime(this.createTime);
        proofVo.setCategory(this.category);
        proofVo.setItems(this.items);

        if (this.recorder != null) {
            proofVo.setRecorder(this.recorder.covert());

        }
        if (this.examination != null) {
            proofVo.setExamination(this.examination.covert());
        }

        return proofVo;
    }
}
