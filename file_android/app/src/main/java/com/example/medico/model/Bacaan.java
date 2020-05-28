package com.example.medico.model;

public class Bacaan {
    private String judul;
    private String publisher;
    private String url_profil;
    private String publish_time;
    private int jam;
    private int menit;

    public Bacaan(String judul, String publisher) {
        this.judul = judul;
        this.publisher = publisher;
    }

    public Bacaan(String judul, String publisher, String url_profil) {
        this.judul = judul;
        this.publisher = publisher;
        this.url_profil = url_profil;
    }

    public Bacaan() {
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getUrl_profil() {
        return url_profil;
    }

    public void setUrl_profil(String url_profil) {
        this.url_profil = url_profil;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public int getJam() {
        return jam;
    }

    public void setJam(int jam) {
        this.jam = jam;
    }

    public int getMenit() {
        return menit;
    }

    public void setMenit(int menit) {
        this.menit = menit;
    }
}
