package com.example.medico.model;

public class Jadwal {
    private String NamaRS;
    private String hari;
    private Date date;
    private Time mulai;
    private Time akhir;

    public Jadwal(){

    }

    public String getNamaRS() {
        return NamaRS;
    }

    public void setNamaRS(String namaRS) {
        NamaRS = namaRS;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getMulai() {
        return mulai;
    }

    public void setMulai(Time mulai) {
        this.mulai = mulai;
    }

    public Time getAkhir() {
        return akhir;
    }

    public void setAkhir(Time akhir) {
        this.akhir = akhir;
    }
}
