package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.ExaminationVO;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 审核记录实体
 */
public class Examination implements Serializable {

    private Integer id;

    private Boolean result;

    private Timestamp time;

    private Integer examinerId;

    private User examiner;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getExaminerId() {
        return examinerId;
    }

    public void setExaminerId(Integer examinerId) {
        this.examinerId = examinerId;
    }

    public User getExaminer() {
        return examiner;
    }

    public void setExaminer(User examiner) {
        this.examiner = examiner;
    }


    public ExaminationVO covert() {
        ExaminationVO examinationVo = new ExaminationVO();
        examinationVo.setId(this.id);
        examinationVo.setResult(this.result);
        examinationVo.setTime(this.time);
        if (this.examiner != null) {
            examinationVo.setExaminer(this.examiner.covert());
        }
        return examinationVo;
    }

}
