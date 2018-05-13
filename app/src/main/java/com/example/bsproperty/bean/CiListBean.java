package com.example.bsproperty.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wdxc1 on 2018/4/9.
 */

public class CiListBean extends BaseResponse {
    private ArrayList<CiBean> data;

    public ArrayList<CiBean> getData() {
        return data;
    }

    public void setData(ArrayList<CiBean> data) {
        this.data = data;
    }
}
