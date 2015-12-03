package scu.miomin.com.keeper.bean;

/**
 * 描述:病人端诊后随访实体类 创建日期:2015/11/22
 *
 * @author 莫绪旻
 */
public class TreatmentBean {

    private PatientBean patientBean;
    private DoctorBean doctorBean;
    private String date;
    private String treatmentReason;

    public TreatmentBean(String date, DoctorBean doctorBean,
                         PatientBean patientBean,
                         String treatmentReason) {
        this.date = date;
        this.doctorBean = doctorBean;
        this.patientBean = patientBean;
        this.treatmentReason = treatmentReason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DoctorBean getDoctorBean() {
        return doctorBean;
    }

    public void setDoctorBean(DoctorBean doctorBean) {
        this.doctorBean = doctorBean;
    }

    public PatientBean getPatientBean() {
        return patientBean;
    }

    public void setPatientBean(PatientBean patientBean) {
        this.patientBean = patientBean;
    }

    public String getTreatmentReason() {
        return treatmentReason;
    }

    public void setTreatmentReason(String treatmentReason) {
        this.treatmentReason = treatmentReason;
    }

    @Override
    public String toString() {
        return "TreatmentBean{" +
                "date='" + date + '\'' +
                ", patientBean=" + patientBean +
                ", doctorBean=" + doctorBean +
                ", treatmentReason='" + treatmentReason + '\'' +
                '}';
    }
}
