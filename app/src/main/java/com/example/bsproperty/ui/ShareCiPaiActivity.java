package com.example.bsproperty.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.CiPaiBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import javax.crypto.Cipher;

import butterknife.BindView;
import butterknife.OnClick;

public class ShareCiPaiActivity extends BaseActivity {

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.sv_01)
    ScrollView sv01;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    private CiPaiBean ciBean;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ciBean = (CiPaiBean) getIntent().getSerializableExtra("data");
        tv01.setText(ciBean.getName());
        tv02.setText(ciBean.getInfo());
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_share_cipai;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                sv01.setBackgroundColor(0xbbFFFFFF);
                break;
            case R.id.btn2:
                sv01.setBackgroundColor(0xbb869aed);
                break;
            case R.id.btn3:
                sv01.setBackgroundColor(0xbbf9e18a);
                break;
            case R.id.btn4:
                this.finish();
                break;
            case R.id.btn5:
                Bitmap bitmap = getViewBitmap(sv01);
                UMImage image = new UMImage(mContext, bitmap);//资源文件
                new ShareAction((Activity) mContext)
                        .withMedia(image)
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                showProgress(mContext);
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                dismissDialog();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                dismissDialog();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                showToast("取消分享");
                                dismissDialog();
                            }
                        }).open();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dismissDialog();
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

}
