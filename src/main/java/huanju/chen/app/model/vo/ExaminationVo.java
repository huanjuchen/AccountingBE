package huanju.chen.app.model.vo;

import huanju.chen.app.model.entity.Examination;

import java.sql.Timestamp;

public class ExaminationVo {
    private Integer id;

    private Boolean result;

    private Timestamp time;

    private UserVo examiner;

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

    public UserVo getExaminer() {
        return examiner;
    }

    public void setExaminer(UserVo examiner) {
        this.examiner = examiner;
    }


}
