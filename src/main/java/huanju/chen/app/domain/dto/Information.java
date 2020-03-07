package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.InformationVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author HuanJu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Information implements Serializable {

    private Integer id;

    private Date time;

    private Integer userId;

    private User user;
    @NotNull
    private String content;

    public InformationVO covert() {
        InformationVO vo = new InformationVO();
        vo.setId(this.id).setTime(this.time).setContent(this.content).setUser(this.user == null ? null : this.user.covert());
        return vo;
    }

}
