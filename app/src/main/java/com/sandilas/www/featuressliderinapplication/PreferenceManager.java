package com.sandilas.www.featuressliderinapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferenceManager(Context context){
        this.context=context;
        getSharedPreference();
    }

    private  void getSharedPreference(){
        sharedPreferences=context.getSharedPreferences(context.getString(R.string.my_preferrence),Context.MODE_PRIVATE);

    }

    public void writePreferences(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_preference_key),"OK");
        editor.commit();
    }

    public boolean checkPreference() {
         boolean status=false;

         if(sharedPreferences.getString(context.getString(R.string.my_preference_key),"null").equals("null")){
             status=false;

         }else{
             status=true;
         }

         return status;
    }


    public  void clearPreferences(){
        sharedPreferences.edit().clear().commit();
    }
}
