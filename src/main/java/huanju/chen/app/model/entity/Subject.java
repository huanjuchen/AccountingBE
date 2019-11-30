package huanju.chen.app.model.entity;

/**
 *
 * 科目实体
 *
 * @author HuanJu
 */
public class Subject {

    private Integer id;

    /**
     * 科目名
     */
    private String name;

    /**
     * 科目描述
     */
    private String describe;

    /**
     * 科目标识，标注科目是否可用
     */
    private Boolean valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
