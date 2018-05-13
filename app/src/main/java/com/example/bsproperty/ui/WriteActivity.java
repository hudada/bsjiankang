package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteActivity extends BaseActivity {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_info)
    EditText etInfo;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_write;
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                finish();
                break;
            case R.id.btn2:
                if (TextUtils.isEmpty(etName.getText().toString().trim())
                        ||TextUtils.isEmpty(etInfo.getText().toString().trim())){
                    showToast("提交失败，尚有内容为空！");
                    return;
                }
                OkHttpTools.sendGet(mContext, ApiManager.CI_USER_ADD)
                        .addParams("uid", MyApplication.getInstance().getUserBean().getId()+"")
                        .addParams("name",etName.getText().toString().trim())
                        .addParams("msg",etInfo.getText().toString().trim())
                        .build()
                        .execute(new BaseCallBack<BaseResponse>(mContext,BaseResponse.class) {
                            @Override
                            public void onResponse(BaseResponse baseResponse) {
                                showToast("提交成功！");
                                finish();
                            }
                        });
                break;
        }
    }
}
