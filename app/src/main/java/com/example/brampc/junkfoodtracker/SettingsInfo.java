package com.example.brampc.junkfoodtracker;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsInfo {
    private Context context;

    private final String SHARED_PREFS = "sharedPrefs";
    private final String KEYWORD = "keyword";
    private final String RADIUS = "radius";
    private final String LANGUAGE = "language";
    private final String NAAM = "naam";
    private final String DATA_LOCATION = "dataLocatie";

    public SettingsInfo(Context context) {
        this.context = context;
    }

    public void saveKeyword(String keyword){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEYWORD, keyword);
        editor.apply();
    }

    public void saveNaam(String naam){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAAM, naam);
        editor.apply();
    }

    public void saveRadius(int radius){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(RADIUS, radius);
        editor.apply();
    }

    public void saveLanguage(String language){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    public void saveDataLocation(String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATA_LOCATION, data);
        editor.apply();
    }

    public String loadKeyword(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEYWORD,"");
    }

    public String loadNaam(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAAM,"Unknown");
    }

    public int loadRadius(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(RADIUS,1000);
    }

    public String loadLanguage(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LANGUAGE,"en");
    }

    public String loadDataLocation(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DATA_LOCATION,"local");
    }

}
