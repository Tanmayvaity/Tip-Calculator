package com.example.tipcalculator;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;


public class PrefManager{


    private SharedPreferences sharedPreferences;
    private static volatile PrefManager instance;
    public static final String PRE_NAME = "com.example.tipcalculator.tipsinfo";

    private PrefManager(@NonNull Context appContext){
        sharedPreferences = appContext.getSharedPreferences(PRE_NAME,Context.MODE_PRIVATE);
    }


    public static PrefManager getInstance(){
        if(instance == null){
            throw new IllegalStateException("call initialize method at least once");
        }
        return instance;
    }

    public static void initialize(Context appContext){
        if(appContext == null){
            throw new NullPointerException("Provided application context is null");
        }

        if(instance == null){
            synchronized (PrefManager.class){
                if(instance == null){
                    instance = new PrefManager(appContext);
                }
            }
        }
    }

    private SharedPreferences getPrefs(){
        return sharedPreferences;
    }

    public void clearPrefs(){
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.clear();
        editor.apply();
    }

    public void removeKey(String key){
        getPrefs().edit().remove(key).apply();
    }

    public boolean containsKey(String key){
        return getPrefs().contains(key);
    }

    public String getString(String key,String defaultValue){
        return getPrefs().getString(key,defaultValue);
    }

    public int getInt(String key , int defaultValue){
        return getPrefs().getInt(key,defaultValue);
    }

    public int getInt(String key ){
        return getPrefs().getInt(key,0);
    }

    public void setString(String key,String value ){
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(key,value);
        editor.apply();
    }

    public void setInt(String key , int value){
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putInt(key,value);
        editor.apply();
    }

}
