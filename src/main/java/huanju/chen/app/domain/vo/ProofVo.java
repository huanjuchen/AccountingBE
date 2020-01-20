package huanju.chen.app.domain.vo;




import java.util.Date;
import java.util.List;

public class ProofVo {

    private Integer id;
    /**
     * 业务发生日期
     */
    private Date date;

    /**
     * 单据数
     */
    private Integer invoiceCount;

    /**
     * 主管人员
     */
    private String manager;

    /**
     * 记账人
     */
    private String collection;

    /**
     * 稽核
     */

    private ExaminationVo examination;

    /**
     * 制单人
     */
    private UserVo recorder;

    /**
     * 出纳人
     */
    private String cashier;

    /**
     * 交款人
     */
    private String payer;


    private List<ProofItemVo> items;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }


    public ExaminationVo getExamination() {
        return examination;
    }

    public void setExamination(ExaminationVo examination) {
        this.examination = examination;
    }


    public UserVo getRecorder() {
        return recorder;
    }

    public void setRecorder(UserVo recorder) {
        this.recorder = recorder;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public List<ProofItemVo> getItems() {
        return items;
    }

    public void setItems(List<ProofItemVo> items) {
        this.items = items;
    }
}
