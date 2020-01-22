package huanju.chen.app.domain.vo;

import java.io.Serializable;

public class SubjectVo implements Serializable {

    private Integer id;

    /**
     * 科目代码
     */
    private String code;

    /**
     * 科目名
     */
    private String name;

    /**
     * 科目类别
     * 1、资产类科目
     * 2、负债类科目
     * 3、共同类科目
     * 4、所有者权益类科目
     * 5、成本类科目
     * 6、损益类科目
     */
    private Integer category;


    /**
     * 1：现金类科目
     * 2：银行类科目
     */
    private Integer daysKind;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getDaysKind() {
        return daysKind;
    }

    public void setDaysKind(Integer daysKind) {
        this.daysKind = daysKind;
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
