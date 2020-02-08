package huanju.chen.app.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class LoginParam implements Serializable {
    @NotNull(message = "用户名或ID不允许为空")
    private String username;

    @NotNull(message = "密码不允许为空")
    private String password;
}
