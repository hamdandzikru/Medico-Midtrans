package com.example.medico.model;

public class User {
    private String NamaDepan;
    private String NamaBelakang;
    private String JenisKelamin; // "pria" atau "wanita"
    private String email;
    private String password;
    private String nohp;

    public User() {
    }

    public User(String namaDepan, String namaBelakang, String jenisKelamin, String email, String password, String nohp) {
        NamaDepan = namaDepan;
        NamaBelakang = namaBelakang;
        JenisKelamin = jenisKelamin;
        this.email = email;
        this.password = password;
        this.nohp = nohp;
    }

    public User(String namaDepan, String namaBelakang, String jenisKelamin) {
        NamaDepan = namaDepan;
        NamaBelakang = namaBelakang;
        JenisKelamin = jenisKelamin;
    }

    public String getNamaDepan() {
        return NamaDepan;
    }

    public String setNamaDepan(String namaDepan) {
        NamaDepan = namaDepan;
        return namaDepan;
    }

    public String getNamaBelakang() {
        return NamaBelakang;
    }

    public String setNamaBelakang(String namaBelakang) {
        NamaBelakang = namaBelakang;
        return namaBelakang;
    }

    public String getJenisKelamin() {
        return JenisKelamin;
    }

    public String setJenisKelamin(String jenisKelamin) {
        JenisKelamin = jenisKelamin;
        return jenisKelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getnohp() {
        return nohp;
    }

    public String setnohp(String nohp) {
        this.nohp = nohp;
        return nohp;
    }
}
