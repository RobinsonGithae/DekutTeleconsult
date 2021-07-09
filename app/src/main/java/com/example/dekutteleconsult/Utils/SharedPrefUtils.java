package com.example.dekutteleconsult.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefUtils {



    public SharedPrefUtils() {

        //default constuctor
    }


    public static boolean saveEmail(String Email, Context context){
        //method to save email input to shared prefs

        SharedPreferences sharedpref=PreferenceManager.getDefaultSharedPreferences(context);
       //initialize an editor object
        SharedPreferences.Editor sharedprefEditor=sharedpref.edit();
        //add key value papers into the sharedpref using editor
        sharedprefEditor.putString(AppConstants.KEY_EMAIL, Email);

       //APPLY THE EDITOR CHANGES
        sharedprefEditor.apply();

       //return true upon completion
        return true;
    }

    public static boolean savePassword(String Password,Context context)

    {
       //save password
        SharedPreferences sharedPrefs=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedPrefsEditor=sharedPrefs.edit();
        sharedPrefsEditor.putString(AppConstants.KEY_PASSWD,Password);
        sharedPrefsEditor.apply();

        return true;
    }


    public static String getEmail(Context context)
    {
        //getThe saved email from Sharedpreferences
        SharedPreferences sharedprefs=PreferenceManager.getDefaultSharedPreferences(context);

      //return or get values from sharedpreference using a key value criteria
        return sharedprefs.getString(AppConstants.KEY_EMAIL,null);
    }

    public static String getPassword(Context context)
    {

        SharedPreferences sharedPrefs=PreferenceManager.getDefaultSharedPreferences(context);
        //return/get saved password from data saved in shared preferences
        return sharedPrefs.getString(AppConstants.KEY_PASSWD,null);

    }


}
