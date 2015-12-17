package scu.miomin.com.keeper.bean;

/**
 * Created by miomin on 15/11/11.
 */
public class ECGRecordBean {

    private String date;
    private boolean isLocality;
    private String ecgUrl;
    private HealthyDescribeByMyselfBean healthyDescribeByMyself;

    public ECGRecordBean(String date, String ecgUrl, HealthyDescribeByMyselfBean healthyDescribeByMyself, boolean isLocality) {
        this.date = date;
        this.ecgUrl = ecgUrl;
        this.healthyDescribeByMyself = healthyDescribeByMyself;
        this.isLocality = isLocality;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public boolean isLocality() {
        return isLocality;
    }

    public void setisLocality(boolean isAtPhone) {
        this.isLocality = isLocality;
    }
}
