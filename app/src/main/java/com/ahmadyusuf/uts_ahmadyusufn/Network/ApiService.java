package com.ahmadyusuf.uts_ahmadyusufn.Network;

import com.ahmadyusuf.uts_ahmadyusufn.Model.Barang;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    // Login untuk mendapatkan token
    @POST("login")
    Call<Map<String, Object>> login(
            @Body Map<String, String> credentials
    );

    // Mengambil daftar barang
    @GET("barangs")
    Call<Map<String, Object>> getBarangs(
            @Header("Authorization") String token
    );

    // Menambahkan barang
    @POST("barangs")
    Call<Map<String, Object>> tambahBarang(
            @Header("Authorization") String token,
            @Body Barang barang
    );

    // Logout
    @POST("logout")
    Call<Map<String, Object>> logout(
            @Header("Authorization") String token
    );
}
