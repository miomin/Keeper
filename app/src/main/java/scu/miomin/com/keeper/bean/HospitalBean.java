package scu.miomin.com.keeper.bean;

/**
 * Created by miomin on 15/11/13.
 */
public class HospitalBean {

    String name;
    String province;
    String city;
    String county;
    MyLocationBean myLocationBean;

    public HospitalBean(String name, String province, String city, String county, MyLocationBean myLocationBean) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.county = county;
        this.myLocationBean = myLocationBean;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public MyLocationBean getMyLocationBean() {
        return myLocationBean;
    }

    public void setMyLocationBean(MyLocationBean myLocationBean) {
        this.myLocationBean = myLocationBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
