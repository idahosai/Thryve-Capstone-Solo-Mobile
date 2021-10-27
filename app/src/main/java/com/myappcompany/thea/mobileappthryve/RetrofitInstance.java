package com.myappcompany.thea.mobileappthryve;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;

    public static JsonPlaceHolderApi getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://thryve-database-2021.eba-5qjamc5m.us-east-2.elasticbeanstalk.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return retrofit.create(JsonPlaceHolderApi.class);
    }
}
