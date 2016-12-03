package com.johnny.kdsclient.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.johnny.kdsclient.SettingShared;

public final class ThemeUtils {

    private ThemeUtils() {
    }

    public static boolean configThemeBeforeOnCreate(@NonNull Activity activity, @StyleRes int light, @StyleRes int dark) {
        boolean enable = SettingShared.isEnableDarkTheme(activity);
        activity.setTheme(enable ? dark : light);
        return enable;
    }

    public static void notifyThemeApply(@NonNull final Activity activity, boolean delay) {
        if (delay) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    recreate(activity);
                }

            });
        } else {
            recreate(activity);
        }
    }

    public static void recreate(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            activity.recreate();
        } else {
            Intent intent = activity.getIntent();
            intent.setClass(activity, activity.getClass());
            activity.startActivity(intent);
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }

}
