package com.ahmadyusuf.uts_ahmadyusufn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmadyusuf.uts_ahmadyusufn.Network.ApiClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Jika token sudah tersimpan, langsung buka halaman utama
        SharedPreferences preferences =
                getSharedPreferences("ERP_PREFS", MODE_PRIVATE);

        String token = preferences.getString("token", null);

        if (token != null && !token.isEmpty()) {
            bukaHalamanUtama();
            return;
        }

        btnLogin.setOnClickListener(v -> prosesLogin());
    }

    private void prosesLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                    this,
                    "Email dan password wajib diisi",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        btnLogin.setEnabled(false);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        ApiClient.getApiService().login(credentials)
                .enqueue(new Callback<Map<String, Object>>() {

                    @Override
                    public void onResponse(
                            Call<Map<String, Object>> call,
                            Response<Map<String, Object>> response) {

                        btnLogin.setEnabled(true);

                        if (response.isSuccessful()
                                && response.body() != null) {

                            Object tokenObject =
                                    response.body().get("token");

                            if (tokenObject != null
                                    && !tokenObject.toString().isEmpty()) {

                                SharedPreferences preferences =
                                        getSharedPreferences(
                                                "ERP_PREFS",
                                                MODE_PRIVATE
                                        );

                                // Simpan token untuk digunakan API
                                preferences.edit()
                                        .putString(
                                                "token",
                                                tokenObject.toString()
                                        )
                                        .apply();

                                // Debug: jangan cetak token asli
                                String tokenTersimpan =
                                        preferences.getString("token", null);

                                Toast.makeText(
                                        LoginActivity.this,
                                        "Login berhasil",
                                        Toast.LENGTH_SHORT
                                ).show();

                                bukaHalamanUtama();

                            } else {
                                Toast.makeText(
                                        LoginActivity.this,
                                        "Token tidak ditemukan pada respons API",
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                        } else {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Login gagal. Periksa email dan password.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Map<String, Object>> call,
                            Throwable t) {

                        btnLogin.setEnabled(true);

                        Log.e(
                                "LOGIN_ERROR",
                                "Request login gagal",
                                t
                        );

                        Toast.makeText(
                                LoginActivity.this,
                                "Koneksi gagal: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void bukaHalamanUtama() {
        Intent intent = new Intent(
                LoginActivity.this,
                MainActivity.class
        );

        startActivity(intent);
        finish();
    }
}
