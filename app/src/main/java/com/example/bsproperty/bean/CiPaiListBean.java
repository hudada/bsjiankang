package com.example.bsproperty.bean;

import java.util.ArrayList;

/**
 * Created by wdxc1 on 2018/4/9.
 */

public class CiPaiListBean extends BaseResponse {

    private ArrayList<CiPaiBean> data;

    public ArrayList<CiPaiBean> getData() {
        return data;
    }

    public void setData(ArrayList<CiPaiBean> data) {
        this.data = data;
    }
}
