package me.chonchol.andropos.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mehedi.chonchol on 23-Oct-18.
 */

public class ClientSharedPreference {

    public static final String IS_CLIENT_LOGGED_IN = "client_logged_in";
    public static final String CLIENT_URL = "http://ip:port";

    static SharedPreferences getClientSharedPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean isLoggedIn){
        SharedPreferences.Editor editor = getClientSharedPref(context).edit();
        editor.putBoolean(IS_CLIENT_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public static void setClientUrl(Context context, String clientDBName){
        SharedPreferences.Editor editor = getClientSharedPref(context).edit();
        editor.putString(CLIENT_URL, clientDBName);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        return getClientSharedPref(context).getBoolean(IS_CLIENT_LOGGED_IN, false);
    }

    public static String getClientUrl(Context context){
        return getClientSharedPref(context).getString(CLIENT_URL, "default_db_name");
    }

}
