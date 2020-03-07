package huanju.chen.app.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author HuanJu
 * @date 2020/3/6 0:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InformationVO {
    private Integer id;

    private Date time;

    private UserVO user;

    private String content;
}
