package huanju.chen.app.domain.dto;

import huanju.chen.app.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户实体
 *
 * @author HuanJu
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;
    @NotNull(message = "用户名不能为空")
    private String username;
    private String password;
    /**
     * 姓名
     */
    private String name;
    /**
     * 1：超级管理员
     * 2：主管会计
     * 3：普通会计
     */
    private Integer role;
    private String phone;
    private Timestamp joinTime;
    private Boolean valid;
    public UserVO covert() {
        UserVO userVo = new UserVO();
        userVo.setId(this.id).setUsername(this.username)
                .setName(this.name)
                .setPhone(this.phone)
                .setRole(this.role)
                .setValid(this.valid)
                .setJoinTime(this.joinTime);
        return userVo;
    }
}
