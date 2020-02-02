package huanju.chen.app.domain.vo;

import java.sql.Timestamp;

public class ExaminationVO {
    private Integer id;

    private Boolean result;

    private Timestamp time;

    private UserVO examiner;

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

    public UserVO getExaminer() {
        return examiner;
    }

    public void setExaminer(UserVO examiner) {
        this.examiner = examiner;
    }


}
