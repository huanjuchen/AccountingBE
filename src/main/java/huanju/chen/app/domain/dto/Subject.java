package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.SubjectVo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 科目实体
 *
 * @author HuanJu
 */
public class Subject implements Serializable {


    private Integer id;

    /**
     * 科目代码
     */

    @NotNull(message = "科目代码不能为空")
    @Size(min = 4,max = 6,message = "科目代码的长度为${min}至${max}位")
    private String code;

    /**
     * 科目名
     */
    @NotNull(message = "科目名不可为空")
    @Size(max = 20,min = 1,message = "长度不能超过${max}")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public SubjectVo covert() {
        SubjectVo subjectVo = new SubjectVo();
        subjectVo.setId(this.id);
        subjectVo.setCode(this.code);
        subjectVo.setName(this.name);
        subjectVo.setCategory(this.category);
        subjectVo.setDaysKind(this.daysKind);
        subjectVo.setRemark(this.remark);
        subjectVo.setValid(this.valid);
        return subjectVo;

    }
}
