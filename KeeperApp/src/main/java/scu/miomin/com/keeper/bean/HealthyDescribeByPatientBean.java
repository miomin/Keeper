package scu.miomin.com.keeper.bean;

/**
 * Created by miomin on 15/11/11.
 */
public class HealthyDescribeByPatientBean {

    private boolean XIONGMEN;
    private boolean XINHUANG;
    private boolean HUXIKUNNAN;
    private boolean TOUYUN;
    private boolean QUANSHENFALI;
    private boolean SHUIJIAOBIEMEN;

    public HealthyDescribeByPatientBean(boolean HUXIKUNNAN, boolean QUANSHENFALI, boolean SHUIJIAOBIEMEN,
                                        boolean TOUYUN, boolean XINHUANG, boolean XIONGMEN) {
        this.HUXIKUNNAN = HUXIKUNNAN;
        this.QUANSHENFALI = QUANSHENFALI;
        this.SHUIJIAOBIEMEN = SHUIJIAOBIEMEN;
        this.TOUYUN = TOUYUN;
        this.XINHUANG = XINHUANG;
        this.XIONGMEN = XIONGMEN;
    }

    public boolean isHUXIKUNNAN() {
        return HUXIKUNNAN;
    }

    public void setHUXIKUNNAN(boolean HUXIKUNNAN) {
        this.HUXIKUNNAN = HUXIKUNNAN;
    }

    public boolean isQUANSHENFALI() {
        return QUANSHENFALI;
    }

    public void setQUANSHENFALI(boolean QUANSHENFALI) {
        this.QUANSHENFALI = QUANSHENFALI;
    }

    public boolean isSHUIJIAOBIEMEN() {
        return SHUIJIAOBIEMEN;
    }

    public void setSHUIJIAOBIEMEN(boolean SHUIJIAOBIEMEN) {
        this.SHUIJIAOBIEMEN = SHUIJIAOBIEMEN;
    }

    public boolean isTOUYUN() {
        return TOUYUN;
    }

    public void setTOUYUN(boolean TOUYUN) {
        this.TOUYUN = TOUYUN;
    }

    public boolean isXINHUANG() {
        return XINHUANG;
    }

    public void setXINHUANG(boolean XINHUANG) {
        this.XINHUANG = XINHUANG;
    }

    public boolean isXIONGMEN() {
        return XIONGMEN;
    }

    public void setXIONGMEN(boolean XIONGMEN) {
        this.XIONGMEN = XIONGMEN;
    }
}
