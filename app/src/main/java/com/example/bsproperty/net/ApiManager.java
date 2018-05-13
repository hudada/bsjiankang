package com.example.bsproperty.net;

/**
 * Created by yezi on 2018/1/27.
 */

public class ApiManager {

    private static final String HTTP = "http://";
    private static final String IP = "192.168.55.103";
    private static final String PROT = ":8080";
    private static final String HOST = HTTP + IP + PROT;
    private static final String API = "/api";
    private static final String USER = "/user";
    private static final String NAME = "/name";
    private static final String CI = "/ci";
    private static final String LIKE = "/like";
    private static final String CI_USER = "/ciuser";

    public static final String USER_RG = HOST + API + USER + "/register";
    public static final String USER_LOGIN = HOST + API + USER + "/login";

    public static final String NAME_LIST = HOST + API + NAME + "/list";

    public static final String CI_ONE = HOST + API + CI + "/one";
    public static final String CI_RANK = HOST + API + CI + "/rank";

    public static final String DO_LIKE = HOST + API + LIKE + "/dolike";
    public static final String UN_LIKE = HOST + API + LIKE + "/unlike";
    public static final String LIKE_LIST = HOST + API + LIKE + "/list";

    public static final String CI_USER_LIKE = HOST + API + CI_USER + "/list";
    public static final String CI_USER_ADD = HOST + API + CI_USER + "/add";
}
