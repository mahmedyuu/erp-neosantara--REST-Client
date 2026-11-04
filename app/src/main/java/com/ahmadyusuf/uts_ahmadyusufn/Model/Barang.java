package com.ahmadyusuf.uts_ahmadyusufn.Model;

public class Barang {

    private int id;
    private String nama_barang;
    private String kategori;
    private int stok;
    private String harga;

    public Barang() {
        // Constructor kosong untuk Gson
    }

    public int getId() {
        return id;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getKategori() {
        return kategori;
    }

    public int getStok() {
        return stok;
    }

    public String getHarga() {
        return harga;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
