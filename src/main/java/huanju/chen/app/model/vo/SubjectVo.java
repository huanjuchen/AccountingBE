package huanju.chen.app.model.vo;

import java.io.Serializable;

public class SubjectVo implements Serializable {

    private Integer id;

    /**
     * 科目名
     */
    private String name;

    /**
     * 科目类型
     * 0：非日记账科目
     * 1：现金类科目
     * 2：银行类科目
     */
    private Integer subjectType;

    /**
     * 科目备注
     */
    private String remark;


    /**
     * 科目标识，标注科目是否可用
     */
    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }


}
