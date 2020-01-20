package huanju.chen.app.security.token;

public class Token {

    private Integer userId;

    private Integer role;

    public Token() {
    }

    public Token(Integer userId, Integer role) {
        this.userId = userId;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
