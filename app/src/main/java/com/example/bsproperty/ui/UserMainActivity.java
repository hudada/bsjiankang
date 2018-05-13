package com.example.bsproperty.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.eventbus.LoginEvent;
import com.example.bsproperty.service.ScreenService;
import com.example.bsproperty.utils.SpUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserMainActivity extends BaseActivity implements SensorEventListener {

    public static String TIME_CHANGED_ACTION = "com.example.bsjk.TIME_CHANGED_ACTION";
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.rl_1_click)
    RelativeLayout rl1Click;
    @BindView(R.id.rl_2_click)
    RelativeLayout rl2Click;
    @BindView(R.id.rl_3_click)
    LinearLayout rl3Click;
    @BindView(R.id.tv_score)
    TextView tvScore;
    private long backTime;
    private long currTime;
    private long add;

    private SensorManager mSensorManager;
    private Sensor mStepCount;
    private Sensor mStepDetector;
    private float mCount;//步行总数
    private float mDetector;//步行探测器
    private static final int sensorTypeD = Sensor.TYPE_STEP_DETECTOR;
    private static final int sensorTypeC = Sensor.TYPE_STEP_COUNTER;
    private boolean isAct;

    @Override
    protected void initView(Bundle savedInstanceState) {
        MyApplication.getInstance().setUserBean(SpUtils.getUserBean(this));


        IntentFilter mScreenOnFilter = new IntentFilter(TIME_CHANGED_ACTION);
        registerReceiver(UITimeReceiver, mScreenOnFilter);

        currTime = SpUtils.getTime(mContext);
        tvTime.setText(MyApplication.formatTime.format(currTime));
        startService(new Intent(mContext, ScreenService.class));

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mStepCount = mSensorManager.getDefaultSensor(sensorTypeC);
        mStepDetector = mSensorManager.getDefaultSensor(sensorTypeD);
        if (mSensorManager != null && mStepCount != null) {
            isAct = true;
            register(mStepCount, SensorManager.SENSOR_DELAY_FASTEST);
            register(mStepDetector, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    public void unRegister() {
        SpUtils.setStep(mContext, mCount);
        mSensorManager.unregisterListener(this);
    }

    private void register(Sensor sensor, int rateUs) {
        mSensorManager.registerListener(this, sensor, rateUs);
    }

    private BroadcastReceiver UITimeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (TIME_CHANGED_ACTION.equals(action)) {
                currTime += 1000;
                add += 1000;
                showStatus();
                tvTime.setText(MyApplication.formatTime.format(currTime));
            }
        }

    };

    private void showStatus() {
        if (currTime > 5 * 1000) {
            tvStatus.setText("轻度");
            tvStatus.setTextColor(getResources().getColor(R.color.white));
            tvTip.setText("轻微感到眼睛疲劳");
        }
        if (currTime > 10 * 1000) {
            tvStatus.setText("中度");
            tvStatus.setTextColor(getResources().getColor(R.color.yyy));
            tvTip.setText("眼睛干涩、胀痛");
        }
        if (currTime > 15 * 1000) {
            tvStatus.setText("重度");
            tvStatus.setTextColor(getResources().getColor(R.color.red));
            tvTip.setText("已经造成视力损伤");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(UITimeReceiver);
        unRegister();
        super.onDestroy();
    }


    @Override
    protected int getRootViewId() {
        return R.layout.activity_user_main;
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        int score = SpUtils.getScore(mContext);
        if (score != -1) {
            tvScore.setVisibility(View.VISIBLE);
            tvScore.setText("得分：" + score);
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backTime < 2000) {
            super.onBackPressed();
        } else {
            showToast(this, "再按一次，退出程序");
            backTime = System.currentTimeMillis();
        }
        backTime = System.currentTimeMillis();
    }


    @OnClick({R.id.rl_1_click, R.id.rl_2_click, R.id.rl_3_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_1_click:
                startActivity(new Intent(mContext, BMIActivity.class));
                break;
            case R.id.rl_2_click:
                if (isAct) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("运动")
                            .setMessage("步数：" + SpUtils.getStep(mContext))
                            .setPositiveButton("确定", null).show();
                } else {
                    showToast("不支持步数记录");
                }
                break;
            case R.id.rl_3_click:
                int score = SpUtils.getScore(mContext);
                if (score == -1) {
                    startActivity(new Intent(mContext, WebActivity.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("提示")
                            .setItems(new String[]{"查看结果", "重新答卷"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            showEnd();
                                            break;
                                        case 1:
                                            startActivity(new Intent(mContext, WebActivity.class));
                                            break;
                                    }
                                }
                            }).show();
                }

                break;
        }
    }

    private void showEnd() {
        int score = SpUtils.getScore(mContext);
        String end = "";
        if (score >= 90) {
            end = "健康，请继续保持";
        } else if (60 <= score && score < 90) {
            end = "正常，请适当运动";
        } else if (40 <= score && score < 60) {
            end = "缺乏锻炼，请参加定期的健身运动";
        } else if (score < 40) {
            end = "严重缺乏锻炼，游泳健身了解一下";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("问卷得分：" + SpUtils.getScore(mContext))
                .setMessage(end)
                .setPositiveButton("知道了", null)
                .show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == sensorTypeC) {
            mCount = event.values[0];
            SpUtils.setStep(mContext, mCount);
        }
        if (event.sensor.getType() == sensorTypeD) {
            if (event.values[0] == 1.0) {
                mDetector++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
