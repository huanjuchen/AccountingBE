package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.SubjectVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 科目实体
 *
 * @author HuanJu
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Subject implements Serializable {
    private Integer id;
    /**
     * 科目代码
     */
    @NotNull(message = "科目代码不能为空")
    @Size(min = 4, max = 6, message = "科目代码的长度为${min}至${max}位")
    private String code;
    /**
     * 科目名
     */
    @NotNull(message = "科目名不可为空")
    @Size(max = 20, min = 1, message = "长度不能超过${max}")
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

    public SubjectVO covert() {
        SubjectVO subjectVo = new SubjectVO();
        subjectVo.setId(this.id)
                .setCode(this.code)
                .setName(this.name)
                .setCategory(this.category)
                .setDaysKind(this.daysKind)
                .setRemark(this.remark)
                .setValid(this.valid);
        return subjectVo;

    }
}
