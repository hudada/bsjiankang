package com.example.bsproperty.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.bsproperty.ui.UserMainActivity;

/**
 * Created by wdxc1 on 2018/5/12.
 */

public class SlptServiceInstalledReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent intent_n = new Intent(context,
                    UserMainActivity.class);

            intent_n.setAction("android.intent.action.MAIN");
            intent_n.addCategory("android.intent.category.LAUNCHER");
            intent_n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent_n);
        }
    }
}
