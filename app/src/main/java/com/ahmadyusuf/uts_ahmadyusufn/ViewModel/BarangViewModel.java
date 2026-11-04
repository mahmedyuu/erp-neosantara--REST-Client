package com.ahmadyusuf.uts_ahmadyusufn.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmadyusuf.uts_ahmadyusufn.Model.Barang;
import com.ahmadyusuf.uts_ahmadyusufn.Repository.BarangRepository;

import java.util.List;

public class BarangViewModel extends ViewModel {

    private final BarangRepository repository;

    private final MutableLiveData<List<Barang>> daftarBarang;
    private final MutableLiveData<String> pesanError;

    public BarangViewModel() {
        repository = new BarangRepository();
        daftarBarang = repository.getDaftarBarang();
        pesanError = repository.getPesanError();
    }

    public LiveData<List<Barang>> getDaftarBarang() {
        return daftarBarang;
    }

    public LiveData<String> getPesanError() {
        return pesanError;
    }

    public void ambilBarang(String token) {
        repository.ambilBarang(token);
    }
}
