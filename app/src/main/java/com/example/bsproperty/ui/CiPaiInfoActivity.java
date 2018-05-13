package com.example.bsproperty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.CiBean;
import com.example.bsproperty.bean.CiObjBean;
import com.example.bsproperty.bean.CiPaiBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CiPaiInfoActivity extends BaseActivity {


    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.tv_04)
    TextView tv04;
    @BindView(R.id.tv_05)
    TextView tv05;
    @BindView(R.id.tv_06)
    TextView tv06;
    @BindView(R.id.btn_01)
    Button btn01;
    private Intent intent;
    private CiPaiBean ciPaiBean;
    private CiBean ciBean;
    private long id;

    @Override
    protected void initView(Bundle savedInstanceState) {
        intent = getIntent();
        ciPaiBean = (CiPaiBean) getIntent().getSerializableExtra("data");
        id = ciPaiBean.getId();

        tv01.setText(ciPaiBean.getName());
        tv02.setText(ciPaiBean.getInfo());
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_ci_pai_info;
    }

    @Override
    protected void loadData() {
        OkHttpTools.sendGet(mContext, ApiManager.CI_ONE)
                .addParams("nid",ciPaiBean.getId()+"")
                .addParams("uid",MyApplication.getInstance().getUserBean().getId()+"")
                .build()
                .execute(new BaseCallBack<CiObjBean>(mContext,CiObjBean.class) {
                    @Override
                    public void onResponse(CiObjBean ciObjBean) {
                        ciBean = ciObjBean.getData();
                        tv03.setText(ciBean.getName());
                        tv04.setText(ciBean.getPeople());
                        tv05.setText(ciBean.getMsg());
                        tv06.setText(ciBean.getInfo());
                    }
                });
    }


    @OnClick(R.id.btn_01)
    public void onViewClicked(View view) {
        Intent intent = new Intent(CiPaiInfoActivity.this, ShareCiPaiActivity.class);
        intent.putExtra("data", ciPaiBean);
        startActivity(intent);
    }
}
