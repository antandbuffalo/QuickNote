package com.antandbuffalo.quicknote;

import android.content.Context;
import android.content.SharedPreferences;

import com.antandbuffalo.quicknote.utilities.Constants;
import com.antandbuffalo.quicknote.utilities.Theme;

public class Storage {

    public static SharedPreferences getSharedPreference(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.STORAGE_NAME, 0);
        return settings;
    }

    public static Boolean putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = Storage.getSharedPreference(context).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return Storage.getSharedPreference(context).getString(key, defaultValue);
    }

    public static Boolean putBoolean(Context context, String key, Boolean value) {
        SharedPreferences.Editor editor = Storage.getSharedPreference(context).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static Boolean getBoolean(Context context, String key, Boolean defaultValue) {
        return Storage.getSharedPreference(context).getBoolean(key, defaultValue);
    }

    public static String getTheme(Context context) {
        SharedPreferences settings = Storage.getSharedPreference(context);
        return settings.getString("theme", Theme.DEFAULT.getValue());
    }

    public static boolean setTheme(Context context, String theme) {
        SharedPreferences settings = Storage.getSharedPreference(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("theme", theme);
        return editor.commit();
    }

}
