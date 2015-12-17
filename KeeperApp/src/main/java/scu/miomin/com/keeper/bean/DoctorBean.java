package scu.miomin.com.keeper.bean;

/**
 * 描述:医生实体类 创建日期:2015/11/4
 *
 * @author 莫绪旻
 */
public class DoctorBean extends Userbean {

    String professional;
    String administrative;
    HospitalBean hospitalBean;
    String introduction;

    public DoctorBean(String phonenumber, String password, String name, int sex,
                      BirthdayBean birthday, String headUrl, String administrative,
                      HospitalBean hospitalBean, String professional, String introduction) {
        super(phonenumber, password, name, sex, birthday, headUrl);
        this.administrative = administrative;
        this.hospitalBean = hospitalBean;
        this.professional = professional;
        this.introduction = introduction;
    }

    public String getAdministrative() {
        return administrative;
    }

    public void setAdministrative(String administrative) {
        this.administrative = administrative;
    }

    public HospitalBean getHospitalBean() {
        return hospitalBean;
    }

    public void setHospitalBean(HospitalBean hospitalBean) {
        this.hospitalBean = hospitalBean;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
