package scu.miomin.com.keeper.bean;

/**
 * 描述：诊后随访实体类 创建日期:2015/11/22
 *
 * @author 莫绪旻
 */
public class TreatmentFollowupBean {

    // 诊后随访日期
    private String date;

    public TreatmentFollowupBean(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
