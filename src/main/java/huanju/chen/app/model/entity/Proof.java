package huanju.chen.app.model.entity;

import java.util.Date;

/**
 * 记账凭证实体
 *
 * @author HuanJu
 */
public class Proof {

    private Integer id;

    /**
     * 凭证创建时间
     */
    private Date createTime;

    /**
     * 主管会计Id
     */
    private Integer directorId;

    /**
     * 主管会计
     */
    private User director;


    /**
     * 凭证制作人Id
     */
    private Integer recorderId;

    /**
     * 凭证制作人
     */
    private User recorder;


    /**
     * 凭证类别
     */
    private Integer category;

    /**
     * 是否审核
     */
    private Boolean valid;

    /**
     * 审核结果
     */
    private Boolean validResult;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public User getDirector() {
        return director;
    }

    public void setDirector(User director) {
        this.director = director;
    }

    public Integer getRecorderId() {
        return recorderId;
    }

    public void setRecorderId(Integer recorderId) {
        this.recorderId = recorderId;
    }

    public User getRecorder() {
        return recorder;
    }

    public void setRecorder(User recorder) {
        this.recorder = recorder;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getValidResult() {
        return validResult;
    }

    public void setValidResult(Boolean validResult) {
        this.validResult = validResult;
    }


}
