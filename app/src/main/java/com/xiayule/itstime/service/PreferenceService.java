package com.xiayule.itstime.service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tan on 14-5-19.
 */
public class PreferenceService {
    private static final String PREFERENCES_NAME = "settings";

    public static final String METHOD_SHOW = "显示";

    public static void saveShowMethod(Context context, int method) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(METHOD_SHOW, method);
        editor.commit();
    }

    public static int getShowMethod(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(METHOD_SHOW, 1);
    }
}
