package me.chonchol.andropos.rest;

import me.chonchol.andropos.helper.UrlHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String getBaseUrl() {
        UrlHelper urlHelper = UrlHelper.getInstance();
        return urlHelper.getFullUrl();
    }

    public static final String clientUrl = getBaseUrl();
    public static final String BASE_URL = "http://"+clientUrl;
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
