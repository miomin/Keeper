package scu.miomin.com.keeper.bean;

/**
 * Created by miomin on 15/11/11.
 */
public class ECGRecordBean {

    private DateBean date;
    private boolean isAtPhone;
    private String ecgUrl;
    private HealthyDescribeByMyselfBean healthyDescribeByMyself;

    public ECGRecordBean(DateBean date, String ecgUrl, HealthyDescribeByMyselfBean healthyDescribeByMyself, boolean isAtPhone) {
        this.date = date;
        this.ecgUrl = ecgUrl;
        this.healthyDescribeByMyself = healthyDescribeByMyself;
        this.isAtPhone = isAtPhone;
    }

    public DateBean getDate() {
        return date;
    }

    public void setDate(DateBean date) {
        this.date = date;
    }

    public String getEcgUrl() {
        return ecgUrl;
    }

    public void setEcgUrl(String ecgUrl) {
        this.ecgUrl = ecgUrl;
    }

    public HealthyDescribeByMyselfBean getHealthyDescribeByMyself() {
        return healthyDescribeByMyself;
    }

    public void setHealthyDescribeByMyself(HealthyDescribeByMyselfBean healthyDescribeByMyself) {
        this.healthyDescribeByMyself = healthyDescribeByMyself;
    }

    public boolean isAtPhone() {
        return isAtPhone;
    }

    public void setIsAtPhone(boolean isAtPhone) {
        this.isAtPhone = isAtPhone;
    }
}
