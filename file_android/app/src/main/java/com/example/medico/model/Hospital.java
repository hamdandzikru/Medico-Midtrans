package com.example.medico.model;

public class Hospital {
    private String nama;
    private String address;
    private String ShortAddress;
    private String deskripsi;
    private String HospitalType; // Bersalin, jiwa, umum
    private HospitalContact mHospitalContact;
    private String url_profil;
    private String url_alamat;
    private String RSLainnya;

    public Hospital() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortAddress() {
        return ShortAddress;
    }

    public void setShortAddress(String shortAddress) {
        ShortAddress = shortAddress;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHospitalType() {
        return HospitalType;
    }

    public void setHospitalType(String hospitalType) {
        HospitalType = hospitalType;
    }

    public HospitalContact getmHospitalContact() {
        return mHospitalContact;
    }

    public void setmHospitalContact(HospitalContact mHospitalContact) {
        this.mHospitalContact = mHospitalContact;
    }

    public String getUrl_profil() {
        return url_profil;
    }

    public void setUrl_profil(String url_profil) {
        this.url_profil = url_profil;
    }

    public String getUrl_alamat() {
        return url_alamat;
    }

    public void setUrl_alamat(String url_alamat) {
        this.url_alamat = url_alamat;
    }

    public String getIGD() {
        return mHospitalContact.getIGD();
    }

    public String getInformation() {
        return mHospitalContact.getInformation();
    }

    public String getEmail() {
        return mHospitalContact.getEmail();
    }

    public String getRSLainnya() {
        return RSLainnya;
    }

    public void setRSLainnya(String RSLainnya) {
        this.RSLainnya = RSLainnya;
    }
}
