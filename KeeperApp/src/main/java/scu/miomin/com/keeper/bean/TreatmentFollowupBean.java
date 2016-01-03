package scu.miomin.com.keeper.bean;

/**
 * 描述：诊后随访实体类 创建日期:2015/11/22
 *
 * @author 莫绪旻
 */
public class TreatmentFollowupBean {

    // 诊后随访日期
    private String date;
    private float RUSUANTUOQINGMEI = -1;
    private float GUCAOZHUANANMEI = -1;
    private float GUBINGZHUANANMEI = -1;
    private float LINGSUANJISUANJIMEI = -1;
    private float LINGSUANJISUANJIMEITONGGONGMEI = -1;
    private float JIHONGDANBAI = -1;
    private float JIGAIDANBAI = -1;
    private float WAIZHOUXUEHONGXIBAOZONGSHU = -1;
    private float ZHONGXINGLIXIBAOBILV = -1;
    private String describeByDoctor;
    private String describeByPatient;

    public TreatmentFollowupBean(String date) {
        this.date = date;
    }

    public TreatmentFollowupBean(String date, String describeByDoctor, String describeByPatient,
                                 float GUBINGZHUANANMEI, float GUCAOZHUANANMEI, float JIGAIDANBAI,
                                 float JIHONGDANBAI, float LINGSUANJISUANJIMEI,
                                 float LINGSUANJISUANJIMEITONGGONGMEI, float RUSUANTUOQINGMEI,
                                 float WAIZHOUXUEHONGXIBAOZONGSHU, float ZHONGXINGLIXIBAOBILV) {
        this.date = date;
        this.describeByDoctor = describeByDoctor;
        this.describeByPatient = describeByPatient;
        this.GUBINGZHUANANMEI = GUBINGZHUANANMEI;
        this.GUCAOZHUANANMEI = GUCAOZHUANANMEI;
        this.JIGAIDANBAI = JIGAIDANBAI;
        this.JIHONGDANBAI = JIHONGDANBAI;
        this.LINGSUANJISUANJIMEI = LINGSUANJISUANJIMEI;
        this.LINGSUANJISUANJIMEITONGGONGMEI = LINGSUANJISUANJIMEITONGGONGMEI;
        this.RUSUANTUOQINGMEI = RUSUANTUOQINGMEI;
        this.WAIZHOUXUEHONGXIBAOZONGSHU = WAIZHOUXUEHONGXIBAOZONGSHU;
        this.ZHONGXINGLIXIBAOBILV = ZHONGXINGLIXIBAOBILV;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescribeByDoctor() {
        return describeByDoctor;
    }

    public void setDescribeByDoctor(String describeByDoctor) {
        this.describeByDoctor = describeByDoctor;
    }

    public String getDescribeByPatient() {
        return describeByPatient;
    }

    public void setDescribeByPatient(String describeByPatient) {
        this.describeByPatient = describeByPatient;
    }

    public float getGUBINGZHUANANMEI() {
        return GUBINGZHUANANMEI;
    }

    public void setGUBINGZHUANANMEI(float GUBINGZHUANANMEI) {
        this.GUBINGZHUANANMEI = GUBINGZHUANANMEI;
    }

    public float getGUCAOZHUANANMEI() {
        return GUCAOZHUANANMEI;
    }

    public void setGUCAOZHUANANMEI(float GUCAOZHUANANMEI) {
        this.GUCAOZHUANANMEI = GUCAOZHUANANMEI;
    }

    public float getJIGAIDANBAI() {
        return JIGAIDANBAI;
    }

    public void setJIGAIDANBAI(float JIGAIDANBAI) {
        this.JIGAIDANBAI = JIGAIDANBAI;
    }

    public float getJIHONGDANBAI() {
        return JIHONGDANBAI;
    }

    public void setJIHONGDANBAI(float JIHONGDANBAI) {
        this.JIHONGDANBAI = JIHONGDANBAI;
    }

    public float getLINGSUANJISUANJIMEI() {
        return LINGSUANJISUANJIMEI;
    }

    public void setLINGSUANJISUANJIMEI(float LINGSUANJISUANJIMEI) {
        this.LINGSUANJISUANJIMEI = LINGSUANJISUANJIMEI;
    }

    public float getLINGSUANJISUANJIMEITONGGONGMEI() {
        return LINGSUANJISUANJIMEITONGGONGMEI;
    }

    public void setLINGSUANJISUANJIMEITONGGONGMEI(float LINGSUANJISUANJIMEITONGGONGMEI) {
        this.LINGSUANJISUANJIMEITONGGONGMEI = LINGSUANJISUANJIMEITONGGONGMEI;
    }

    public float getRUSUANTUOQINGMEI() {
        return RUSUANTUOQINGMEI;
    }

    public void setRUSUANTUOQINGMEI(float RUSUANTUOQINGMEI) {
        this.RUSUANTUOQINGMEI = RUSUANTUOQINGMEI;
    }

    public float getWAIZHOUXUEHONGXIBAOZONGSHU() {
        return WAIZHOUXUEHONGXIBAOZONGSHU;
    }

    public void setWAIZHOUXUEHONGXIBAOZONGSHU(float WAIZHOUXUEHONGXIBAOZONGSHU) {
        this.WAIZHOUXUEHONGXIBAOZONGSHU = WAIZHOUXUEHONGXIBAOZONGSHU;
    }

    public float getZHONGXINGLIXIBAOBILV() {
        return ZHONGXINGLIXIBAOBILV;
    }

    public void setZHONGXINGLIXIBAOBILV(float ZHONGXINGLIXIBAOBILV) {
        this.ZHONGXINGLIXIBAOBILV = ZHONGXINGLIXIBAOBILV;
    }
}
