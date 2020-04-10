package huanju.chen.app.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class SubjectVO implements Serializable {

    private Integer id;

    /**
     * 科目代码
     */
    private String code;

    /**
     * 科目名
     */
    private String name;

    private Integer category;

    private SubjectVO parent;

    private String remark;

    private Boolean valid;

}
