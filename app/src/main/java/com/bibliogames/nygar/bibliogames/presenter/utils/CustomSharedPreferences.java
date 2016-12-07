package com.bibliogames.nygar.bibliogames.presenter.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.bibliogames.nygar.bibliogames.model.User;
import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;

/**
 * Clase para controlar {@link SharedPreferences} serializando con la libreria {@link LoganSquare}
 */

public class CustomSharedPreferences {

    private final String CURRENT_USER ="current_user";
    private final String FILE="sharesPreferencesFile";

    private String userJson;
    private SharedPreferences sharedPref;


    public CustomSharedPreferences(Context context) {
        sharedPref = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        userJson = sharedPref.getString(CURRENT_USER, "");

    }

    public User getCurrentUser(){
        User currentUser = new User();
        try {
            currentUser = LoganSquare.parse(userJson,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  currentUser;
    }

    public void clearUser(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(CURRENT_USER);
        editor.apply();
    }

    public void setCurrentUser(User nUser){
        SharedPreferences.Editor editor = sharedPref.edit();
        String userLoginJson = "";
        try {
            userLoginJson = LoganSquare.serialize(nUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        editor.putString( CURRENT_USER, userLoginJson);
        editor.apply();
    }
}
