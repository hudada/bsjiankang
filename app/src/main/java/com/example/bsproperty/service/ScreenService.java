package com.example.bsproperty.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.bsproperty.ui.UserMainActivity;
import com.example.bsproperty.utils.SpUtils;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wdxc1 on 2018/5/12.
 */

public class ScreenService extends Service {

    private Timer timer = null;
    private Intent timeIntent = null;
    private long start, end;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        ScreenService.this.registerReceiver(mScreenOReceiver, mScreenOnFilter);

        IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        ScreenService.this.registerReceiver(mScreenOReceiver, mScreenOffFilter);

        start = System.currentTimeMillis();
        timeIntent = new Intent();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeIntent.setAction(UserMainActivity.TIME_CHANGED_ACTION);
                sendBroadcast(timeIntent);
            }
        }, 1000, 1000);
    }



    public void onDestroy() {
        super.onDestroy();

        end = System.currentTimeMillis();
        SpUtils.setTime(ScreenService.this, end - start);
        timer.cancel();
        ScreenService.this.unregisterReceiver(mScreenOReceiver);
    }

    private BroadcastReceiver mScreenOReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals("android.intent.action.SCREEN_ON")) {
                start = System.currentTimeMillis();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        timeIntent.setAction(UserMainActivity.TIME_CHANGED_ACTION);
                        sendBroadcast(timeIntent);
                    }
                }, 1000, 1000);
            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                timer.cancel();
                end = System.currentTimeMillis();
                SpUtils.setTime(ScreenService.this, end - start);
            }
        }

    };

}
