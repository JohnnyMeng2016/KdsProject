package com.johnny.kdsclient;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名称：NewKdsClient
 * 类描述：SharedPrefrence统一设置类
 * 创建人：孟忠明
 * 创建时间：2016/11/30
 */
public class SettingShared {

    private static final String SHARED_PREFRENCE = "setting";

    private static final String KEY_LOGIN_NAME = "userName";
    private static final String KEY_LOGIN_PASSWORD = "password";
    private static final String KEY_LOGIN_AUTO = "enableAutoLogin";
    private static final String KEY_THEME_DARK = "enableDarkTheme";

    private SettingShared() {

    }

    public static boolean isEnableDarkTheme(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_THEME_DARK, false);
    }

    public static String getLoginName(Context context) {
        return getSharedPreferences(context).getString(KEY_LOGIN_NAME, "");
    }

    public static String getLoginPassword(Context context) {
        return getSharedPreferences(context).getString(KEY_LOGIN_PASSWORD, "");
    }

    public static boolean isEnableAutoLogin(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_LOGIN_AUTO, false);
    }

    public static void setEnableDarkTheme(Context context, boolean isEnable) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(KEY_THEME_DARK, isEnable);
        editor.commit();
    }

    public static void setEnableAutoLogin(Context context, boolean isEnable) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(KEY_LOGIN_AUTO, isEnable);
        editor.commit();
    }

    public static void saveUserName(Context context, String userName) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(KEY_LOGIN_NAME, userName);
        editor.commit();
    }

    public static void savePassword(Context context, String password) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(KEY_LOGIN_PASSWORD, password);
        editor.commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("SHARED_PREFRENCE", context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }


}
