package scu.miomin.com.keeper.bean;

/**
 * Created by miomin on 15/11/11.
 */
public class DateBean {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public DateBean(int year, int month, int day, int hour, int minute, int second) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.month = month;
        this.second = second;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStringUtilMinute() {
        String result = "" + year;

        if (month < 10)
            result = result + "-" + "0" + month;
        else
            result = result + "-" + month;

        if (day < 10)
            result = result + "-" + "0" + day;
        else
            result = result + "-" + day;

        if (hour < 10)
            result = result + " " + "0" + hour;
        else
            result = result + " " + hour;

        if (minute < 10)
            result = result + ":" + "0" + minute;
        else
            result = result + ":" + minute;

        return result;
    }

    public String getStringUtilSecond() {

        String result = "" + year;

        if (month < 10)
            result = result + "-" + "0" + month;
        else
            result = result + "-" + month;

        if (day < 10)
            result = result + "-" + "0" + day;
        else
            result = result + "-" + day;

        if (hour < 10)
            result = result + " " + "0" + hour;
        else
            result = result + " " + hour;

        if (minute < 10)
            result = result + ":" + "0" + minute;
        else
            result = result + ":" + minute;

        if (second < 10)
            result = result + ":" + "0" + second;
        else
            result = result + ":" + second;

        return result;
    }
}
