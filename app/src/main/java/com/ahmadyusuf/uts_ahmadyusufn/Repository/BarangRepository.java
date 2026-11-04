package com.ahmadyusuf.uts_ahmadyusufn.Repository;

import androidx.lifecycle.MutableLiveData;

import com.ahmadyusuf.uts_ahmadyusufn.Model.Barang;
import com.ahmadyusuf.uts_ahmadyusufn.Network.ApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangRepository {

    private final MutableLiveData<List<Barang>> daftarBarang =
            new MutableLiveData<>();

    private final MutableLiveData<String> pesanError =
            new MutableLiveData<>();

    public MutableLiveData<List<Barang>> getDaftarBarang() {
        return daftarBarang;
    }

    public MutableLiveData<String> getPesanError() {
        return pesanError;
    }

    public void ambilBarang(String token) {
        ApiClient.getApiService().getBarangs(token)
                .enqueue(new Callback<java.util.Map<String, Object>>() {
                    @Override
                    public void onResponse(
                            Call<java.util.Map<String, Object>> call,
                            Response<java.util.Map<String, Object>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                Object data = response.body().get("data");
                                JSONArray array = new JSONArray(
                                        new com.google.gson.Gson().toJson(data)
                                );

                                List<Barang> hasil = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject item = array.getJSONObject(i);

                                    Barang barang = new Barang();
                                    barang.setId(item.optInt("id"));
                                    barang.setNama_barang(
                                            item.optString("nama_barang")
                                    );
                                    barang.setKategori(
                                            item.optString("kategori")
                                    );
                                    barang.setStok(item.optInt("stok"));
                                    barang.setHarga(item.optString("harga"));

                                    hasil.add(barang);
                                }

                                daftarBarang.postValue(hasil);
                            } catch (Exception e) {
                                pesanError.postValue(
                                        "Gagal membaca data: " + e.getMessage()
                                );
                            }
                        } else {
                            pesanError.postValue(
                                    "Gagal mengambil barang. HTTP "
                                            + response.code()
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<java.util.Map<String, Object>> call,
                            Throwable t) {
                        pesanError.postValue(
                                "Koneksi gagal: " + t.getMessage()
                        );
                    }
                });
    }
}
