package com.example.bsproperty.ui;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.R;
import com.example.bsproperty.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("问卷调查");
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void loadData() {
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new AndroidtoJs(), "android");


        webView.loadUrl("file:///android_asset/test.html");
    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    public class AndroidtoJs extends Object {

        @JavascriptInterface
        public void save(String msg, boolean isok) {
//            if (isok) {
                int score = (int) Double.parseDouble(msg);
                SpUtils.setScore(mContext, score);
                finish();
//            } else {
//                showToast("请完成所有题目");
//            }
        }
    }
}
