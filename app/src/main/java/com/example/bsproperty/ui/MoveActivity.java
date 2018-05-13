package com.example.bsproperty.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bsproperty.R;

public class MoveActivity extends BaseActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mStepCount;
    private Sensor mStepDetector;
    private float mCount;//步行总数
    private float mDetector;//步行探测器
    private static final int sensorTypeD=Sensor.TYPE_STEP_DETECTOR;
    private static final int sensorTypeC=Sensor.TYPE_STEP_COUNTER;


    @Override
    protected void initView(Bundle savedInstanceState) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCount = mSensorManager.getDefaultSensor(sensorTypeC);
        mStepDetector = mSensorManager.getDefaultSensor(sensorTypeD);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_move;
    }

    @Override
    protected void loadData() {
        register();
    }

    @Override
    protected void onDestroy() {
        unRegister();
        super.onDestroy();
    }

    public void register(){

        register(mStepCount, SensorManager.SENSOR_DELAY_FASTEST);
        register(mStepDetector, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unRegister(){
        mSensorManager.unregisterListener(this);
    }

    private void register(Sensor sensor,int rateUs) {
        mSensorManager.registerListener(this, sensor, rateUs);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==sensorTypeC) {
            setStepCount(event.values[0]);
        }
        if (event.sensor.getType()==sensorTypeD) {
            if (event.values[0]==1.0) {
                mDetector++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getStepCount() {
        return mCount;
    }

    private void setStepCount(float count) {
        this.mCount = count;

    }

    public float getmDetector() {
        return mDetector;
    }
}
