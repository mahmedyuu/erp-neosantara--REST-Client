package com.ahmadyusuf.uts_ahmadyusufn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ahmadyusuf.uts_ahmadyusufn.Model.Barang;
import com.ahmadyusuf.uts_ahmadyusufn.ViewModel.BarangViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BarangViewModel barangViewModel;
    private BarangAdapter barangAdapter;
    private SharedPreferences preferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(
                "ERP_PREFS",
                MODE_PRIVATE
        );

        token = preferences.getString("token", null);

        // Jika belum login, kembali ke halaman login
        if (token == null || token.isEmpty()) {
            bukaLogin();
            return;
        }

        ListView listBarang = findViewById(R.id.listBarang);
        Button btnLogout = findViewById(R.id.btnLogout);

        barangAdapter = new BarangAdapter(
                this,
                new ArrayList<>()
        );

        listBarang.setAdapter(barangAdapter);

        // Menghubungkan Activity dengan ViewModel
        barangViewModel = new ViewModelProvider(
                this
        ).get(BarangViewModel.class);

        // Mengamati perubahan daftar barang
        barangViewModel.getDaftarBarang().observe(
                this,
                daftarBarang -> {
                    if (daftarBarang != null) {
                        barangAdapter.clear();
                        barangAdapter.addAll(daftarBarang);
                        barangAdapter.notifyDataSetChanged();
                    }
                }
        );

        // Mengamati pesan error dari Repository
        barangViewModel.getPesanError().observe(
                this,
                pesan -> {
                    if (pesan != null && !pesan.isEmpty()) {
                        Toast.makeText(
                                this,
                                pesan,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );

        // Meminta data barang dari Laravel API
        barangViewModel.ambilBarang("Bearer " + token);

        // Tombol logout
//        btnLogout.setOnClickListener(v -> {
//            preferences.edit().remove("token").apply();
//            bukaLogin();
//        });


        btnLogout.setOnClickListener(v -> {
            btnLogout.setEnabled(false);

            com.ahmadyusuf.uts_ahmadyusufn.Network.ApiClient
                    .getApiService()
                    .logout("Bearer " + token)
                    .enqueue(new retrofit2.Callback<java.util.Map<String, Object>>() {
                        @Override
                        public void onResponse(
                                retrofit2.Call<java.util.Map<String, Object>> call,
                                retrofit2.Response<java.util.Map<String, Object>> response) {

                            hapusTokenDanKembali();
                        }

                        @Override
                        public void onFailure(
                                retrofit2.Call<java.util.Map<String, Object>> call,
                                Throwable t) {

                            hapusTokenDanKembali();
                        }
                    });
        });


    }

    private void bukaLogin() {
        android.content.Intent intent =
                new android.content.Intent(
                        MainActivity.this,
                        LoginActivity.class
                );

        startActivity(intent);
        finish();
    }
    private void hapusTokenDanKembali() {
        preferences.edit().remove("token").apply();
        bukaLogin();
    }
}
