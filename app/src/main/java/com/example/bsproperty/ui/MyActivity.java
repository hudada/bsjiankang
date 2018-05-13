package com.example.bsproperty.ui;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyActivity extends BaseActivity {


    @BindView(R.id.btn2)
    TextView btn2;
    @BindView(R.id.btn3)
    TextView btn3;
    @BindView(R.id.btn4)
    TextView btn4;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_my;
    }

    @Override
    protected void loadData() {
        btn2.setText(MyApplication.getInstance().getUserBean().getUserName());
    }


    @OnClick({R.id.btn3, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn3:
                startActivity(new Intent(mContext, MyCiActivity.class));
                break;
            case R.id.btn4:
                if (SpUtils.cleanUserBean(mContext)) {
                    EventBus.getDefault().post("exit");
                }
                break;
        }
    }
}
