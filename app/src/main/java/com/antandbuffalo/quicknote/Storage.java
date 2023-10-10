package com.antandbuffalo.quicknote;

import android.content.Context;
import android.content.SharedPreferences;

import com.antandbuffalo.quicknote.utilities.Constants;

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

}
