package com.example.medico.FungsiFungsi;

import android.util.Log;

public class DateFunction {
    private static final String TAG = "HARI";

    private String NextDayOf(String day){
        switch (day){
            case "Senin" :
                return "Selasa";
            case "Selasa" :
                return "Rabu";
            case "Rabu" :
                return "Kamis";
            case "Kamis" :
                return "Jumat";
            case "Jumat" :
                return "Sabtu";
            case "Sabtu" :
                return "Minggu";
            case "Minggu" :
                return "Senin";
            default:
                return "eror";
        }
    }

    private String NextMonthOf(String month){
        switch (month){
            case "Januari" :
                return "Februari";
            case "Februari" :
                return "Maret";
            case "Maret" :
                return "April";
            case "April" :
                return "Mei";
            case "Mei" :
                return "Juni";
            case "Juni" :
                return "Juli";
            case "Juli" :
                return "Agustus";
            case "Agustus" :
                return "September";
            case "September" :
                return "Oktober";
            case "Oktober" :
                return "November";
            case "November" :
                return "Desember";
            case "Desember" :
                return "Januari";
            default:
                return "eror";
        }
    }

    private Boolean isLeapYear(int year){
        if (year % 4 != 0){
            return false;
        }
        else return (year % 100 != 0) || (year % 400 == 0);
    }

    private int jml_hari(String month, int year){
        switch (month){
            case "Januari" :
                return 31;
            case "Februari" :
                if (isLeapYear(year)){
                    return 29;
                }
                else{
                    return 28;
                }
            case "Maret" :
                return 31;
            case "April" :
                return 30;
            case "Mei" :
                return 31;
            case "Juni" :
                return 30;
            case "Juli" :
                return 31;
            case "Agustus" :
                return 31;
            case "September" :
                return 30;
            case "Oktober" :
                return 31;
            case "November" :
                return 30;
            case "Desember" :
                return 31;
            default:
                return 0;
        }
    }

    public String ConvertDateToDay(int tanggal, String bulan, int tahun){
        int TahunAwal;
        String BulanAwal;
        int TanggalAwal = 1;
        String hari = HariAwal(bulan,tahun);
        BulanAwal = bulan;
        TahunAwal = tahun;

        boolean AkhirTahun = false;
        boolean AkhirBulan = false;
        if (tanggal == 31 && bulan.equals("Desember")){
            tanggal = 30;
            AkhirTahun = true;
        }
        else if (tanggal == jml_hari(bulan,tahun)){
            tanggal--;
            AkhirBulan = true;
        }

        while (true){
            if (tanggal < jml_hari(BulanAwal,TahunAwal)){
                TanggalAwal++;
            }
            else{
                if (BulanAwal.equals("Desember")){
                    TahunAwal++;
                }
                BulanAwal = NextMonthOf(BulanAwal);
                tanggal = 1;
            }
            hari = NextDayOf(hari);
            //println(hari);
            if (tanggal == TanggalAwal && bulan.equals(BulanAwal) && tahun == TahunAwal){
                break;
            }
        }
        if (AkhirTahun ||AkhirBulan){
            return NextDayOf(hari);
        }
        else {
            return hari;
        }
    }

    private String HariAwal(String bulan, int tahun){
        int TahunAwal = 2019;
        String hari = "Selasa";
        String BulanAwal = "Januari";
        while (!bulan.equals(BulanAwal) || tahun != TahunAwal){
            int i = 1;
            while (i <= jml_hari(BulanAwal,TahunAwal)){
                hari = NextDayOf(hari);
                i++;
            }
            BulanAwal = NextMonthOf(BulanAwal);
            if (BulanAwal.equals("Januari")){
                TahunAwal++;
            }
        }
        return hari;
    }

    public int getTanggalFromDate(String Date){
        StringBuilder tanggalBuilder = new StringBuilder();
        tanggalBuilder.append(Date.charAt(0));
        tanggalBuilder.append(Date.charAt(1));
        return Integer.valueOf(tanggalBuilder.toString());
    }

    public String getBulanFromDate(String Date){
        StringBuilder bulanBuilder = new StringBuilder();
        bulanBuilder.append(Date.charAt(3));
        bulanBuilder.append(Date.charAt(4));
        String string = bulanBuilder.toString();
        switch (string){
            case "01" :
                return "Januari";
            case "02" :
                return "Februari";
            case "03" :
                return "Maret";
            case "04" :
                return "April";
            case "05" :
                return "Mei";
            case "06" :
                return "Juni";
            case "07" :
                return "Juli";
            case "08" :
                return "Agustus";
            case "09" :
                return "September";
            case "10" :
                return "Oktober";
            case "11" :
                return "November";
            case "12" :
                return "Desember";
        }
        return bulanBuilder.toString();
    }

    public int getTahunFromDate(String Date){
        StringBuilder tahunBuilder = new StringBuilder();
        tahunBuilder.append(Date.charAt(6));
        tahunBuilder.append(Date.charAt(7));
        tahunBuilder.append(Date.charAt(8));
        tahunBuilder.append(Date.charAt(9));
        return Integer.valueOf(tahunBuilder.toString());
    }

    public String getDateDifferences(int TanggalPublish, int BulanPublish, int TahunPublish, int MenitPublish, int JamPublish, int TanggalSekarang, int BulanSekarang, int TahunSekarang, int MenitSekarang, int JamSekarang){
        if (TanggalPublish == TanggalSekarang && BulanPublish == BulanSekarang && TahunPublish == TahunSekarang){ // jika tanggalnya sama

            if (MenitPublish == MenitSekarang && JamPublish == JamSekarang){ //jika waktunya sama
                return "Beberapa detik yang lalu";
            }
            else if (JamPublish == JamSekarang){ // jika jamnya sama, menitnya berbeda
                return MenitSekarang - MenitPublish + " menit yang lalu";
            }
            else { // jamnya berbeda
                if (JamSekarang == JamPublish + 1){ //jika selisih jamnya hanya 1
                    if (MenitSekarang >= MenitPublish){
                        return "1 jam yang lalu";
                    }
                    return 60 - (MenitPublish - MenitSekarang) + " menit yang lalu";
                }
                else{ //jika selisih jamnya lebih dari 1
                    return JamPublish - JamSekarang + " jam yang lalu";
                }
            }
        } //Jika Tanggalnya Sama
        else if (isYesterday(TanggalPublish,BulanPublish,TahunPublish,TanggalSekarang,BulanSekarang,TahunSekarang)){ //Jika Publishnya Kemarin
            if (JamSekarang >= JamPublish){
                return 24 - (JamSekarang - JamPublish)  + " jam yang lalu";
            }
            else {
                if (JamSekarang == 0 && JamPublish == 23){
                    if (MenitSekarang >= MenitPublish){
                        return "1 jam yang lalu";
                    }
                    return 60 - (MenitPublish - MenitSekarang) + " menit yang lalu";
                }
                return "Kemarin";
            }
        }
        else if (BulanPublish == BulanSekarang && TahunPublish == TahunSekarang) { //jika bulan dan tahun sama tetapi tangggalnya berbeda

            int DateDifferences = TanggalSekarang - TanggalPublish;
            if (DateDifferences == 1){
                return "Kemarin";
            }
            else if (DateDifferences < 7){
                return DateDifferences + "hari yang lalu";
            }
            else if (DateDifferences == 7){
                return "1 minggu yang lalu";
            }
            else if (DateDifferences <= 14){
                return "2 minggu yang lalu";
            }
            else if (DateDifferences <= 21){
                return "3 minggu yang lalu";
            }
            else if (DateDifferences <= 28){
                return "4 minggu yang lalu";
            }
            else{
                return "4 minggu yang lalu";
            }
        }
        else {
            return TanggalPublish + " " +BulanPublish + " " + TahunPublish;
        }
    }

    public int getMonthIntFromDateWithMonthString(String date){
        int i = 0;
        char Char = date.charAt(i);
        while (Char != ' '){
            i++;
            Char = date.charAt(i);
        }
        i++;
        StringBuilder stringBuilder = new StringBuilder();
        while (date.charAt(i) != ' '){
            stringBuilder.append(date.charAt(i));
            i++;
        }
        return ConvertMonthToInt(stringBuilder.toString());
    }

    public int ConvertMonthToInt(String month){
        switch (month){
            case "Januari" : return 1;
            case "January" : return 1;
            case "Februari" : return 2;
            case "February" : return 2;
            case "Maret" : return 3;
            case "March" : return 3;
            case "April" : return 4;
            case "Mei" : return 5;
            case "May" : return 5;
            case "Juni" : return 6;
            case "June" : return 6;
            case "Juli" : return 7;
            case "July" : return 7;
            case "Agustus" : return 8;
            case "August" : return 8;
            case "September" : return 9;
            case "Oktober" : return 10;
            case "October" : return 10;
            case "November" : return 11;
            case "Desember" : return 12;
            case "December" : return 12;
            default: return 0;
        }
    }

    public String ConvertDateFormatDDMMMMYYYYToDDMMYY(String DDmmmmYY){
        StringBuilder TanggalBuilder = new StringBuilder();
        int j = 0;
        while (DDmmmmYY.charAt(j) != ' '){
            TanggalBuilder.append(DDmmmmYY.charAt(j));
            j++;
        }

        StringBuilder BulanBuilder = new StringBuilder();
        int i = j+1;
        while (DDmmmmYY.charAt(i) != ' '){
            BulanBuilder.append(DDmmmmYY.charAt(i));
            i++;
        }
        i++;

        int total = DDmmmmYY.length();
        StringBuilder TahunBuilder = new StringBuilder();
        while (i < total){
            TahunBuilder.append(DDmmmmYY.charAt(i));
            i++;
        }

        String TanggalTemp = TanggalBuilder.toString();
        int Temp =  ConvertMonthToInt(String.valueOf(BulanBuilder.toString()));
        String BulanTemp =  String.valueOf(Temp);
        if (Integer.parseInt(TanggalTemp) < 10){
            String TT2 = "0" + TanggalTemp;
            TanggalTemp = TT2;
        }

        if (Integer.parseInt(BulanTemp) < 10){
            String BBT2 = "0" + BulanTemp;
            BulanTemp = BBT2;
        }

        return TanggalTemp + " " + BulanTemp + " " +  TahunBuilder.toString();
    }

    private boolean isYesterday(int TanggalAwal, int BulanAwal, int TahunAwal, int TanggalAkhir, int BulanAkhir, int TahunAkhir){
        if (TanggalAwal == TanggalAkhir && BulanAwal == BulanAkhir && TahunAwal == TahunAkhir){ //Jika Tanggalnya Sama
            return false;
        }
        else if (TahunAwal == TahunAkhir){ //Jika Tahunnya Sama
            if (BulanAwal == BulanAkhir){ //Jika Bulannya Sama, Tahunnya Sama
                return TanggalAkhir == TanggalAwal + 1;
            }
            else { // Jika Bulannya Berbeda, Tahunnya Sama
                return TanggalAkhir == 1 && TanggalAwal == jml_hari(ConvertIntToMonth(BulanAwal),TahunAwal) && BulanAkhir == BulanAwal + 1;
            }
        }
        else { // Jika Tahunnya Berbeda
            return BulanAwal == 12 && BulanAkhir == 1 && TanggalAwal == 31 && TanggalAkhir == 1;
        }
    }

    private String ConvertIntToMonth(int month){
        switch (month){
            case 1 : return "Januari";
            case 2 : return "Februari";
            case 3 : return "Maret";
            case 4 : return "April";
            case 5 : return "Mei";
            case 6 : return "Juni";
            case 7 : return "Juli";
            case 8 : return "Agustus";
            case 9 : return "September";
            case 10 : return "Oktober";
            case 11 : return "November";
            case 12 : return "Desember";
            default: return "null";
        }
    }

    public int getHourFromTimeWithFormatHHmm(String time){
        StringBuilder HourBuilder = new StringBuilder();
        HourBuilder.append(time.charAt(0));
        HourBuilder.append(time.charAt(1));
        return Integer.valueOf( HourBuilder.toString());
    }

    public int getMinuteFromTimeWithFormatHHmm(String time){
        StringBuilder MinuteBuilder = new StringBuilder();
        MinuteBuilder.append(time.charAt(3));
        MinuteBuilder.append(time.charAt(4));
        return Integer.valueOf(MinuteBuilder.toString());
    }

    public String CovertToDateFixPatientVerification(String tanggal, String bulan, String tahun){
        return tanggal + "/" + bulan + "/" + tahun;
    }
}
