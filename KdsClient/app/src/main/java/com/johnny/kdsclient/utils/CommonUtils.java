package com.johnny.kdsclient.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommonUtils {

    /**
     * 获取安装包信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = new PackageInfo();
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 判断Sdcard是否存在
     *
     * @return
     */
    public static boolean detectSdcardIsExist() {
        String extStorageState = Environment.getExternalStorageState();
        File file = Environment.getExternalStorageDirectory();
        if (!Environment.MEDIA_MOUNTED.equals(extStorageState)
                || !file.exists() || !file.canWrite()
                || file.getFreeSpace() <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断指定路径下的文件是否存在
     */
    public static boolean detectFileIsExist(String path) {
        if (null != path) {
            File file = new File(path);
            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dp2Px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * px转dp
     *
     * @param px
     * @return
     */
    public static int px2Dp(Context context, float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 判断字符串是否为空或NULL
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断集合对象是否为空或NULL
     *
     * @param list
     * @return
     */
    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * InputStream to String
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }


}
