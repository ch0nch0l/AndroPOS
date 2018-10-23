package me.chonchol.andropos.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mehedi.chonchol on 23-Oct-18.
 */

public class SplashSharedPref {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String SPLASH_PREF = "splash_pref";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public SplashSharedPref(Context context){
        this.context = context;
        pref = context.getSharedPreferences(SPLASH_PREF, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
