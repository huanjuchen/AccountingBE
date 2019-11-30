package huanju.chen.app.model.vo;

import huanju.chen.app.model.entity.User;

import java.sql.Timestamp;

public class UserVo {

    private Integer id;

    private String username;

    private String phone;

    private Boolean valid;

    private Integer role;

    private Timestamp joinTime;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    public User covert() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setValid(this.valid);
        user.setJoinTime(this.joinTime);
        user.setRole(this.role);
        user.setPhone(this.phone);
        return user;
    }
}
