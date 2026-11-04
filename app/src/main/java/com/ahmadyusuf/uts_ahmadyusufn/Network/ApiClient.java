package com.ahmadyusuf.uts_ahmadyusufn.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://192.168.18.137:8000/api/";

    private static Retrofit retrofit;
    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    )
                    .build();

            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }
}
