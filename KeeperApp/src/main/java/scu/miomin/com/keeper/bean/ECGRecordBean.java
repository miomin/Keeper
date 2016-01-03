package scu.miomin.com.keeper.bean;

/**
 * Created by miomin on 15/11/11.
 */
public class ECGRecordBean {

    private String date;
    private boolean isLocality;
    private String fileName;
    private HealthyDescribeByPatientBean healthyDescribeByMyself;

    public ECGRecordBean(String date, String fileName, HealthyDescribeByPatientBean healthyDescribeByMyself, boolean isLocality) {
        this.date = date;
        this.fileName = fileName;
        this.healthyDescribeByMyself = healthyDescribeByMyself;
        this.isLocality = isLocality;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HealthyDescribeByPatientBean getHealthyDescribeByMyself() {
        return healthyDescribeByMyself;
    }

    public void setHealthyDescribeByMyself(HealthyDescribeByPatientBean healthyDescribeByMyself) {
        this.healthyDescribeByMyself = healthyDescribeByMyself;
    }

    public boolean isLocality() {
        return isLocality;
    }

    public void setisLocality(boolean isLocality) {
        this.isLocality = isLocality;
    }
}
