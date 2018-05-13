package com.example.bsproperty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.bean.CiBean;
import com.example.bsproperty.bean.CiObjBean;
import com.example.bsproperty.bean.CiUserBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CiInfoActivity extends BaseActivity {

    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.tv03)
    TextView tv03;
    @BindView(R.id.tv04)
    TextView tv04;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.ibtn_like)
    ImageButton ibtnLike;
    @BindView(R.id.tvnext)
    TextView tvNext;
    private CiBean ciBean;
    private CiUserBean ciUserBean;
    boolean ismy;

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_ci_info;
    }

    @Override
    protected void loadData() {
        ismy = getIntent().getBooleanExtra("ismy", false);
        if (ismy) {
            tvNext.setVisibility(View.INVISIBLE);
            ciUserBean = (CiUserBean) getIntent().getSerializableExtra("data");
            tv01.setText(ciUserBean.getName());
            tv03.setText(ciUserBean.getMsg());
            ibtnLike.setVisibility(View.GONE);
        } else {
            if (getIntent().getBooleanExtra("islike",false)){
                tvNext.setVisibility(View.INVISIBLE);
                ciBean = (CiBean) getIntent().getSerializableExtra("data");
                tv01.setText(ciBean.getTname() + "." + ciBean.getName());
                tv02.setText(TextUtils.isEmpty(ciBean.getPeople()) ? "佚名" : ciBean.getPeople());
                tv03.setText(ciBean.getMsg());
                tv04.setText(TextUtils.isEmpty(ciBean.getInfo()) ? "暂无解析" : ciBean.getInfo());
                if (ciBean.isLike()) {
                    ibtnLike.setImageResource(R.mipmap.ic_favorite_red_300_24dp);
                } else {
                    ibtnLike.setImageResource(R.mipmap.ic_favorite_white_24dp);
                }
            }else{
                OkHttpTools.sendGet(mContext, ApiManager.CI_RANK)
                        .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                        .build()
                        .execute(new BaseCallBack<CiObjBean>(mContext, CiObjBean.class) {
                            @Override
                            public void onResponse(CiObjBean ciObjBean) {
                                ciBean = ciObjBean.getData();
                                tv01.setText(ciBean.getTname() + "." + ciBean.getName());
                                tv02.setText(TextUtils.isEmpty(ciBean.getPeople()) ? "佚名" : ciBean.getPeople());
                                tv03.setText(ciBean.getMsg());
                                tv04.setText(TextUtils.isEmpty(ciBean.getInfo()) ? "暂无解析" : ciBean.getInfo());
                                if (ciBean.isLike()) {
                                    ibtnLike.setImageResource(R.mipmap.ic_favorite_red_300_24dp);
                                } else {
                                    ibtnLike.setImageResource(R.mipmap.ic_favorite_white_24dp);
                                }
                            }
                        });
            }

        }

    }


    @OnClick({R.id.btn1, R.id.ibtn_like,R.id.tvnext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvnext:
                OkHttpTools.sendGet(mContext, ApiManager.CI_RANK)
                        .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                        .build()
                        .execute(new BaseCallBack<CiObjBean>(mContext, CiObjBean.class) {
                            @Override
                            public void onResponse(CiObjBean ciObjBean) {
                                ciBean = ciObjBean.getData();
                                tv01.setText(ciBean.getTname() + "." + ciBean.getName());
                                tv02.setText(TextUtils.isEmpty(ciBean.getPeople()) ? "佚名" : ciBean.getPeople());
                                tv03.setText(ciBean.getMsg());
                                tv04.setText(TextUtils.isEmpty(ciBean.getInfo()) ? "暂无解析" : ciBean.getInfo());
                                if (ciBean.isLike()) {
                                    ibtnLike.setImageResource(R.mipmap.ic_favorite_red_300_24dp);
                                } else {
                                    ibtnLike.setImageResource(R.mipmap.ic_favorite_white_24dp);
                                }
                            }
                        });
                break;
            case R.id.btn1:
                Intent intent = new Intent(CiInfoActivity.this, ShereCiActivity.class);
                if (ismy){
                    intent.putExtra("ismy", true);
                    intent.putExtra("data", ciUserBean);
                }else{
                    intent.putExtra("data", ciBean);
                }
                startActivity(intent);
                break;
            case R.id.ibtn_like:
                if (ciBean.isLike()) {
                    OkHttpTools.sendPost(mContext, ApiManager.UN_LIKE)
                            .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                            .addParams("cid", ciBean.getId() + "")
                            .build()
                            .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                @Override
                                public void onResponse(BaseResponse baseResponse) {
                                    ibtnLike.setImageResource(R.mipmap.ic_favorite_white_24dp);
                                    ciBean.setLike(false);
                                    showToast("取消收藏");
                                }
                            });
                } else {
                    OkHttpTools.sendPost(mContext, ApiManager.DO_LIKE)
                            .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                            .addParams("cid", ciBean.getId() + "")
                            .build()
                            .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                @Override
                                public void onResponse(BaseResponse baseResponse) {
                                    ibtnLike.setImageResource(R.mipmap.ic_favorite_red_300_24dp);
                                    ciBean.setLike(true);
                                    showToast("收藏成功");
                                }
                            });
                }
                break;
        }
    }
}
