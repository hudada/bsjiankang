package com.example.bsproperty;

import android.app.Application;

import com.example.bsproperty.bean.CiBean;
import com.example.bsproperty.bean.CiPaiBean;
import com.example.bsproperty.bean.UserBean;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by yezi on 2018/1/27.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    private UserBean userBean;
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");

    public static final int CURR_ADMIN = 0;
    public static final int CURR_USER = 1;
    public static final int CURR_MERCHANT = 2;
    public static ArrayList<CiBean> ciBeans = new ArrayList<>();
    public static ArrayList<CiPaiBean> ciPaiBeans = new ArrayList<>();

    public static ArrayList<CiBean> getCiBeans() {
        return ciBeans;
    }

    public static ArrayList<CiPaiBean> getCiPaiBeans() {
        return ciPaiBeans;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("hdd"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();


        OkHttpUtils.initClient(okHttpClient);
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();

        }
        return instance;
    }


    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public static void loadCiPai() {
    }

    public static void loadCi() {

//        CiBean ci7 = new CiBean(6
//                , "明月别枝惊鹊，清风半夜鸣蝉。稻花香里说丰年，听取蛙声一片。\n" +
//                "七八个星天外，两三点雨山前。旧时茅店社林边，路转溪桥忽见。"
//                , "暂无解析"
//                , "西江月·夜行黄沙道中"
//                , "辛弃疾"
//                , 6);
//        CiBean ci8 = new CiBean(7
//                , "少年不识愁滋味，爱上层楼。爱上层楼。为赋新词强说愁。\n" +
//                "而今识尽愁滋味，欲说还休。欲说还休。却道天凉好个秋。"
//                , "暂无解析"
//                , "丑奴儿·书博山道中壁"
//                , "辛弃疾"
//                , 7);
//        CiBean ci9 = new CiBean(8
//                , "千古江山，英雄无觅，孙仲谋处。舞榭歌台，风流总被雨打风吹去。斜阳草树，寻常巷陌，人道寄奴曾住。想当年，金戈铁马，气吞万里如虎。\n" +
//                "元嘉草草，封狼居胥，赢得仓皇北顾。四十三年，望中犹记，烽火扬州路。可堪回首，佛狸祠下，一片神鸦社鼓。凭谁问，廉颇老矣，尚能饭否？"
//                , "暂无解析"
//                , "永遇乐·京口北固亭怀古"
//                , "辛弃疾"
//                , 8);
//        CiBean ci10 = new CiBean(9
//                , "何处望神州？满眼风光北固楼。千古兴亡多少事？悠悠。不尽长江滚滚流。\n" +
//                "年少万兜鍪，坐断东南战未休。天下英雄谁敌手？曹刘。生子当如孙仲谋。"
//                , "暂无解析"
//                , "南乡子·登京口北固亭有怀"
//                , "辛弃疾"
//                , 9);
//        CiBean ci11 = new CiBean(10
//                , "寻寻觅觅，冷冷清清，凄凄惨惨戚戚。乍暖还寒时候，最难将息。三杯两盏淡酒，怎敌他、晚来风急？雁过也，正伤心，却是旧时相识。\n" +
//                "满地黄花堆积。憔悴损，如今有谁堪摘？守着窗儿，独自怎生得黑？梧桐更兼细雨，到黄昏、点点滴滴。这次第，怎一个愁字了得！"
//                , "暂无解析"
//                , "声声慢·寻寻觅觅"
//                , "李清照"
//                , 10);
//        CiBean ci12 = new CiBean(11
//                , "风住尘香花已尽，日晚倦梳头。物是人非事事休，欲语泪先流。\n" +
//                "闻说双溪春尚好，也拟泛轻舟。只恐双溪舴艋舟，载不动许多愁。"
//                , "暂无解析"
//                , "武陵春·春晚"
//                , "李清照"
//                , 11);
//        CiBean ci13 = new CiBean(12
//                , "塞下秋来风景异，衡阳雁去无留意。四面边声连角起，千嶂里，长烟落日孤城闭。\n" +
//                "浊酒一杯家万里，燕然未勒归无计。羌管悠悠霜满地，人不寐，将军白发征夫泪。"
//                , "暂无解析"
//                , "渔家傲·秋思"
//                , "范仲淹"
//                , 12);
//        CiBean ci14 = new CiBean(13
//                , "碧云天，黄叶地。秋色连波，波上寒烟翠。山映斜阳天接水。芳草无情，更在斜阳外。\n" +
//                "黯乡魂，追旅思。夜夜除非，好梦留人睡。明月楼高休独倚。酒入愁肠，化作相思泪。\n"
//                , "暂无解析"
//                , "苏幕遮·怀旧"
//                , "范仲淹"
//                , 13);
//        CiBean ci15 = new CiBean(14
//                , "驿外断桥边，寂寞开无主。已是黄昏独自愁，更著风和雨。\n" +
//                "无意苦争春，一任群芳妒。零落成泥碾作尘，只有香如故。"
//                , "暂无解析"
//                , "卜算子·咏梅"
//                , "陆游"
//                , 14);
    }

}
