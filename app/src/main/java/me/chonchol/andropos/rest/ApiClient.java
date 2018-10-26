package me.chonchol.andropos.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiClient {

    public static Retrofit retrofit = null;

    public static Retrofit getClient(String url){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

//    public static String getBaseUrl(Context context) {
//        class GetBaseUrl extends AsyncTask<Void,Void,ClientUrl>{
//
//            ClientUrl clientUrl = new ClientUrl();
//
//            @Override
//            protected ClientUrl doInBackground(Void... voids) {
//                clientUrl = DatabaseClient.getInstance(context)
//                        .getAndroPOSDB().clientUrlDao().getDefaultUrl();
//                return clientUrl;
//            }
//
//            @Override
//            protected void onPostExecute(ClientUrl clientUrl) {
//                super.onPostExecute(clientUrl);
//            }
//        }
//
//        GetBaseUrl getBaseUrl = new GetBaseUrl();
//        getBaseUrl.execute();
//
//        return getBaseUrl.clientUrl.getFullUrl();
//    }

}
