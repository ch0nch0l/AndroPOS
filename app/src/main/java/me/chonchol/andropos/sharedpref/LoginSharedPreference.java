package me.chonchol.andropos.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mehedi.chonchol on 23-Oct-18.
 */

public class LoginSharedPreference {

    public static final String IS_LOGGED_IN = "user_logged_in";

    static SharedPreferences getLoginSharedPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean isLoggedIn){
        SharedPreferences.Editor editor = getLoginSharedPref(context).edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        return getLoginSharedPref(context).getBoolean(IS_LOGGED_IN, false);
    }
}
