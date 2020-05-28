package com.example.medico.model;

public class DiagnosisRS {
    private String nama;
    private String desc;

    public DiagnosisRS(){}

    public DiagnosisRS(String nama, String desc) {
        this.nama = nama;
        this.desc = desc;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
