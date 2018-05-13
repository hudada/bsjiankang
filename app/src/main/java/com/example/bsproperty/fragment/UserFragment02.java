package com.example.bsproperty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wdxc1 on 2018/3/21.
 */

public class UserFragment02 extends BaseFragment {
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_addr)
    TextView tvAddr;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.btn_out)
    Button btnOut;

    @Override
    public void onResume() {
        super.onResume();
//        tvName.setText(MyApplication.getInstance().getUserBean().getUserName());
//        tvAddr.setText(MyApplication.getInstance().getUserBean().getAddr());
//        tvTel.setText(MyApplication.getInstance().getUserBean().getTel());
    }

    @Override
    protected void loadData() {
//        tvName.setText(MyApplication.getInstance().getUserBean().getUserName());
//        tvAddr.setText(MyApplication.getInstance().getUserBean().getAddr());
//        tvTel.setText(MyApplication.getInstance().getUserBean().getTel());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("我的");
        btnRight.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_user02;
    }

    @OnClick({R.id.btn_right, R.id.btn_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                // TODO 修改不修改
                break;
            case R.id.btn_out:
                if (SpUtils.cleanUserBean(mContext)) {
                    System.exit(0);
                }
                break;
        }
    }
}
