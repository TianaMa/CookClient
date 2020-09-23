package com.demo.cook.base.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.demo.baselib.base.BaseContext;
import com.demo.cook.ui.user.model.data.User;
import com.google.gson.Gson;


public class Storage {

    static SharedPreferences preferences = BaseContext.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);

    private static String storage_user ="user";

    public static void  setUser(User user){
        preferences.edit().putString(storage_user,new Gson().toJson(user)).commit();
    }

    public static User getUser(){
        String userString =preferences.getString(storage_user,null);
        if(userString==null){
            return null;
        }
        return new Gson().fromJson(userString,User.class);
    }

    public static void setZh(boolean chinese){
        preferences.edit().putBoolean("chinese",chinese).commit();
    }

    public static boolean getZh(){
        return preferences.getBoolean("chinese",false);
    }


    public static void setQNToken(String token){
        preferences.edit().putString("QNToken",token).commit();
    }

    public static String getQNToken(){
        return preferences.getString("QNToken","");
    }

}