package com.example.medico.FungsiFungsi;

import java.util.ArrayList;

public class StringFunction {
    public static String DeleteDotFromString(String string) {
        int i = 0;

        String temp = "";
        while (i < string.length()) {
            if (!(string.charAt(i) == '.')) {
                temp = temp + string.charAt(i);
            }
            i++;
        }
        if (temp.contains(".")) {
            return DeleteDotFromString(temp);
        }
        return temp;
    }


    public static String ConvertEmailToID(String email) {
        int jmlh = email.length();
        int i = jmlh - 1;
        char temp = email.charAt(i);
        while (i > 0) {
            temp = email.charAt(i);
            if (temp == '.') {
                break;
            }
            i--;
        }
        String result = email.substring(0, i) + "-" + email.substring(i + 1, email.length());
        return result.contains(".") ? ConvertEmailToID(result) : result;
    }

    public static ArrayList<String> UraiWaktuBerobat(String sWaktu) {
        ArrayList<String> dataList = new ArrayList<>();
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while (i < sWaktu.length()) {
            if (sWaktu.charAt(i) != '|') {
                stringBuilder.append(sWaktu.charAt(i));
                if (i == sWaktu.length() - 1) {
                    dataList.add(stringBuilder.toString());
                }
            } else {
                dataList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
            i++;
        }
        return dataList;
    }

    public static int ConvertPriceToInt(String price) {
        price = price.replace("Rp. ", "");
        price = price.replace(".", "");

        return Integer.parseInt(price);

    }

    public static ArrayList<String> ParseOtherHospital(String OtherHospital) {
        ArrayList<String> dataList = new ArrayList<>();
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while (i < OtherHospital.length()) {
            if (OtherHospital.charAt(i) != '|') {
                stringBuilder.append(OtherHospital.charAt(i));
                if (i == OtherHospital.length() - 1) {
                    dataList.add(stringBuilder.toString());
                }
            } else {
                dataList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
            i++;
        }
        return dataList;
    }
}
