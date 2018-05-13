package com.example.bsproperty.bean;

import java.util.ArrayList;

public class CiUserListBean extends BaseResponse {

    private ArrayList<CiUserBean> data;

    public ArrayList<CiUserBean> getData() {
        return data;
    }

    public void setData(ArrayList<CiUserBean> data) {
        this.data = data;
    }
}
