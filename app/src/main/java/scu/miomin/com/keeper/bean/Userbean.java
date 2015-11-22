package scu.miomin.com.keeper.bean;

import scu.miomin.com.keeper.Enum.UserTypeEnum;

/**
 * 描述:医生和病人的父类 创建日期:2015/11/2
 *
 * @author 莫绪旻
 */
public class Userbean {
    // 注册时的手机号，唯一标识
    private String phonenumber;
    // 用户密码
    private String password;
    // 病人姓名
    private String name;
    // 病人性别
    private int sex;
    // 病人生日
    private BirthdayBean birthday;
    // 病人年龄
    private int age;
    // 头像url
    private String headUrl;

    public Userbean(String phonenumber, String password, String name, int sex, BirthdayBean birthday, String headUrl) {
        this.phonenumber = phonenumber;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.age = calAge(birthday);
        this.headUrl = headUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public BirthdayBean getBirthday() {
        return birthday;
    }

    public void setBirthday(BirthdayBean birthday) {
        this.birthday = birthday;
        this.age = calAge(birthday);
    }

    private int calAge(BirthdayBean birthday) {
        return 2015 - birthday.getYear();
    }

    public int getUserType() {
        if (phonenumber.length() == 11)
            return UserTypeEnum.PATIENT;
        else
            return UserTypeEnum.DOCTOR;
    }

    @Override
    public String toString() {
        return "Userbean{" +
                "phonenumber='" + phonenumber + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}
