package com.kelasp1.quizmobile;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Jika pakai php artisan serve --host=0.0.0.0 --port=8000:
    private static final String BASE_URL = "http://172.16.63.171:8000/";

    // Jika pakai Apache Laragon (folder di C:\laragon\www\laravel-api-fondasi):
    // private static final String BASE_URL = "http://172.16.63.171/laravel-api-fondasi/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}