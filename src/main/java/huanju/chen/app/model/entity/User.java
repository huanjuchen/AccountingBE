package huanju.chen.app.model.entity;

import huanju.chen.app.model.vo.UserVo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *用户实体
 *
 * @author HuanJu
 */
public class User implements Serializable {


    private Integer id;

    @NotNull(message = "用户名不能为空")
    private String username;

    private String password;


    /**
     * 1：超级管理员
     * 2：主管会计
     * 3：普通会计
     */
    private Integer role;

    private String phone;

    private Timestamp joinTime;

    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }


    public UserVo covert(){
        UserVo userVo=new UserVo();
        userVo.setId(this.id);
        userVo.setUsername(this.username);
        userVo.setPhone(this.phone);
        userVo.setRole(this.role);
        userVo.setValid(this.valid);
        userVo.setJoinTime(this.joinTime);
        return userVo;
    }
}
