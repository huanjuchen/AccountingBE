package huanju.chen.app.model.vo;


import huanju.chen.app.model.entity.Proof;
import huanju.chen.app.model.entity.ProofItem;

import java.util.Date;
import java.util.List;

public class ProofVo {
    private Integer id;

    private Date createTime;

    private UserVo recorder;

    private Integer category;

    private ExaminationVo examination;

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

    public UserVo getRecorder() {
        return recorder;
    }

    public void setRecorder(UserVo recorder) {
        this.recorder = recorder;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public ExaminationVo getExamination() {
        return examination;
    }

    public void setExamination(ExaminationVo examination) {
        this.examination = examination;
    }

    public List<ProofItem> getItems() {
        return items;
    }

    public void setItems(List<ProofItem> items) {
        this.items = items;
    }

    public Proof covert() {
        Proof proof = new Proof();
        proof.setId(this.id);
        proof.setCreateTime(this.createTime);
        proof.setCategory(this.category);
        proof.setItems(this.items);
        if (this.recorder != null) {
            proof.setRecorder(this.recorder.covert());
        }
        if (this.examination != null) {
            proof.setExamination(this.examination.covert());
        }
        return proof;

    }

}
