package com.example.bsproperty.bean;

/**
 * Created by wdxc1 on 2018/1/28.
 */

public class UserBean {
    private Long id;
    private String userName;
    private String pwd;

    private double dh;
    private double dw;
    private String end;

    public double getDh() {
        return dh;
    }

    public void setDh(double dh) {
        this.dh = dh;
    }

    public double getDw() {
        return dw;
    }

    public void setDw(double dw) {
        this.dw = dw;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
