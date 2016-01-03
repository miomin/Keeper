package scu.miomin.com.keeper.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：就诊记录实体类 创建日期:2015/11/22
 *
 * @author 莫绪旻
 */
public class TreatmentBean {

    private PatientBean patientBean;
    private DoctorBean doctorBean;
    private String date;
    private String treatmentReason;
    private List<TreatmentFollowupBean> treatmentFollowupList;

    public TreatmentBean(String date, DoctorBean doctorBean,
                         PatientBean patientBean,
                         String treatmentReason) {
        this.date = date;
        this.doctorBean = doctorBean;
        this.patientBean = patientBean;
        this.treatmentReason = treatmentReason;
        treatmentFollowupList = new ArrayList<TreatmentFollowupBean>();
    }

    // 添加一条诊后随访记录
    public void addTreatmentFollowup(TreatmentFollowupBean treatmentFollowupBean) {
        treatmentFollowupList.add(treatmentFollowupBean);
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

    public List<TreatmentFollowupBean> getTreatmentFollowupList() {
        return treatmentFollowupList;
    }

    public void setTreatmentFollowupList(List<TreatmentFollowupBean> treatmentFollowupList) {
        this.treatmentFollowupList = treatmentFollowupList;
    }


}
