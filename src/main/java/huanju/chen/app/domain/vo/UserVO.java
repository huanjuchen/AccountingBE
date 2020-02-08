package huanju.chen.app.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UserVO implements Serializable {

    private Integer id;

    private String username;

    private String name;

    private String phone;

    private Boolean valid;

    private Integer role;

    private Timestamp joinTime;

}
