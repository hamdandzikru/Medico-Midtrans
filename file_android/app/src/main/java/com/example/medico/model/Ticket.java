package com.example.medico.model;

public class Ticket {
    private String jns_layanan;
    private String penyedia_layanan;
    private String harga_layanan;
    private String tanggal;
    private String waktu;
    private String id;
    private Integer status;

    public Ticket() {

    }

    public Ticket(String jns_layanan, String penyedia_layanan, String harga_layanan, String tanggal, String waktu, String id) {
        this.jns_layanan = jns_layanan;
        this.penyedia_layanan = penyedia_layanan;
        this.harga_layanan = harga_layanan;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.id = id;
        this.status = 0;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getJns_layanan() {
        return jns_layanan;
    }

    public void setJns_layanan(String jns_layanan) {
        this.jns_layanan = jns_layanan;
    }

    public String getPenyedia_layanan() {
        return penyedia_layanan;
    }

    public void setPenyedia_layanan(String penyedia_layanan) {
        this.penyedia_layanan = penyedia_layanan;
    }

    public String getHarga_layanan() {
        return harga_layanan;
    }

    public void setHarga_layanan(String harga_layanan) {
        this.harga_layanan = harga_layanan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
