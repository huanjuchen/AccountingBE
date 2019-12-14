package huanju.chen.app.model.entity;

import huanju.chen.app.model.vo.SubjectVo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 科目实体
 *
 * @author HuanJu
 */
public class Subject implements Serializable {


    @NotNull(message = "科目代码不能为空")
    private Integer id;

    /**
     * 科目名
     */
    @NotNull(message = "科目名不可为空")
    @Length(max = 20, message = "长度不能操作${max}")
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


    public SubjectVo covert() {
        SubjectVo subjectVo = new SubjectVo();
        subjectVo.setId(this.id);
        subjectVo.setName(this.name);
        subjectVo.setSubjectType(this.subjectType);
        subjectVo.setRemark(this.remark);
        subjectVo.setValid(this.valid);
        return subjectVo;

    }
}
