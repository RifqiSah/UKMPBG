package com.alriftech.ukmpbg;

public class UKM {
    private int id_ukm;
    private String nama_ukm;
    private String alamat;
    private String jam_buka;
    private String jam_tutup;

    public UKM(int id_ukm, String nama_ukm, String alamat, String jam_buka, String jam_tutup) {
        this.id_ukm = id_ukm;
        this.nama_ukm = nama_ukm;
        this.alamat = alamat;
        this.jam_buka = jam_buka;
        this.jam_tutup = jam_tutup;
    }

    public int getId_ukm() {
        return id_ukm;
    }

    public void setId_ukm(int id_ukm) {
        this.id_ukm = id_ukm;
    }

    public String getNama_ukm() {
        return nama_ukm;
    }

    public void setNama_ukm(String nama_ukm) {
        this.nama_ukm = nama_ukm;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJam_buka() {
        return jam_buka;
    }

    public void setJam_buka(String jam_buka) {
        this.jam_buka = jam_buka;
    }

    public String getJam_tutup() {
        return jam_tutup;
    }

    public void setJam_tutup(String jam_tutup) {
        this.jam_tutup = jam_tutup;
    }
}
