package com.example.bsproperty.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.bsproperty.bean.UserBean;
import com.google.gson.Gson;

/**
 * Created by yezi on 2018/1/29.
 */

public class SpUtils {

    private static final String SP_NAME = "sp_name";
    private static final String ABOUT_USER = "about_user";
    private static final String ABOUT_TIME = "about_time";

    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static void setUserBean(Context context, UserBean userBean) {
        if (userBean != null) {
            getSp(context).edit().putString(ABOUT_USER, new Gson().toJson(userBean)).apply();
        }
    }

    public static UserBean getUserBean(Context context) {
        String user = getSp(context).getString(ABOUT_USER, "");
        if (!TextUtils.isEmpty(user)) {
            return new Gson().fromJson(user, UserBean.class);
        } else {
            return null;
        }
    }

    public static boolean setTime(Context context, long time) {
        return getSp(context).edit().putLong(ABOUT_TIME, getTime(context) + time).commit();
    }

    public static long getTime(Context context) {
        long user = getSp(context).getLong(ABOUT_TIME, 0);
        return user;
    }

    public static boolean setStep(Context context, float step) {
        return getSp(context).edit().putFloat("about_step", step).commit();
    }

    public static float getStep(Context context) {
        float user = getSp(context).getFloat("about_step", 0f);
        return user;
    }

    public static boolean setScore(Context context, int score) {
        return getSp(context).edit().putInt("score", score).commit();
    }

    public static int getScore(Context context) {
        int user = getSp(context).getInt("score", -1);
        return user;
    }

    public static boolean cleanUserBean(Context context) {
        return getSp(context).edit().putString(ABOUT_USER, "").commit();
    }
}
