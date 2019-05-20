package com.alriftech.ukmpbg;

public class Lowongan {
    private int id_lowongan;
    private String judul;
    private String deskripsi;
    private String gaji;

    public Lowongan(int id_lowongan, String judul, String deskripsi, String gaji) {
        this.id_lowongan = id_lowongan;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.gaji = gaji;
    }

    public int getId_lowongan() {
        return id_lowongan;
    }

    public void setId_lowongan(int id_lowongan) {
        this.id_lowongan = id_lowongan;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGaji() {
        return gaji;
    }

    public void setGaji(String gaji) {
        this.gaji = gaji;
    }
}
