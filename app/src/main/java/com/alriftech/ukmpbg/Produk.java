package com.alriftech.ukmpbg;

public class Produk {
    private int id_produk;
    private int id_ukm;
    private String nama_produk;
    private String foto_produk;
    private String deskripsi;
    private String harga;

    public Produk(int id_produk, int id_ukm, String nama_produk, String foto_produk, String deskripsi, String harga) {
        this.id_produk = id_produk;
        this.id_ukm = id_ukm;
        this.nama_produk = nama_produk;
        this.foto_produk = foto_produk;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getId_ukm() {
        return id_ukm;
    }

    public void setId_ukm(int id_ukm) {
        this.id_ukm = id_ukm;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
