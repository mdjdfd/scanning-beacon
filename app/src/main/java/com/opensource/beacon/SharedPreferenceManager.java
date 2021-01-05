package com.opensource.beacon;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static SharedPreferences mSharedPreference;

    private SharedPreferenceManager(){}

    public static void init(Context context){
        if(mSharedPreference == null)
            mSharedPreference = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static void setString(String key, String value){
        SharedPreferences.Editor prefsEditor = mSharedPreference.edit();
        prefsEditor.putString(key,value).commit();
    }

    public static String getString(String key, String defValue){
        return mSharedPreference.getString(key,defValue);
    }

    public static void setBoolean(String key, boolean value){
        SharedPreferences.Editor prefsEditor = mSharedPreference.edit();
        prefsEditor.putBoolean(key,value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue){
        return mSharedPreference.getBoolean(key,defValue);
    }

    public static void setInteger(String key, Integer value){
        SharedPreferences.Editor prefsEditor = mSharedPreference.edit();
        prefsEditor.putInt(key,value).commit();
    }

    public static Integer getInteger(String key, int defValue){
        return mSharedPreference.getInt(key,defValue);
    }

    public static void setFloat(String key, float value){
        SharedPreferences.Editor prefsEditor = mSharedPreference.edit();
        prefsEditor.putFloat(key, value).commit();
    }

    public static float getFloat(String key, float defValue){
        return mSharedPreference.getFloat(key,defValue);
    }



}
