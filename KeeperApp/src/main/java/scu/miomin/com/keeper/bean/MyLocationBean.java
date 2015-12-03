package scu.miomin.com.keeper.bean;

/**
 * Created by miomin on 15/11/13.
 */
public class MyLocationBean {

    float longitude;
    float latitude;

    public MyLocationBean(float longitude, float latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
