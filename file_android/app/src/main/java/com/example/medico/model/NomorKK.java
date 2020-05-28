package com.example.medico.model;

public class NomorKK {
    private String nama;
    private String nik;
    private String tempat_lahir;
    private String tanggal_lahir;;
    private String bulan_lahir;
    private String tahun_lahir;
    private String alamat;

    public NomorKK() {
    }

    public NomorKK(String nomor, String nama, String nik, String tempat_lahir, String tanggal_lahir, String bulan_lahir, String tahun_lahir, String alamat) {
        this.nama = nama;
        this.nik = nik;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.bulan_lahir = bulan_lahir;
        this.tahun_lahir = tahun_lahir;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public String getNik() {
        return nik;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public String getBulan_lahir() {
        return bulan_lahir;
    }

    public String getTahun_lahir() {
        return tahun_lahir;
    }

    public String getAlamat() {
        return alamat;
    }
}
