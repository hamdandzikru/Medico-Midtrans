package com.example.medico.utils;

public class MidtransUtils {

    //true : untuk production
    //false : untuk sandbox
    private static final Boolean ENVIRONMENT = false;
    public static final String MERCHANT_BASE_CHECKOUT_URL = "https://serverside-medico.herokuapp.com//";
    private static final String MERCHANT_CLIENT_KEY_SANDBOX = "SB-Mid-client-hTHUMdz4Hz48QzPM";
    private static final String MERCHANT_CLIENT_KEY_PRODUCTION = "Mid-client-jzqtw7GvejcQbyVp";

    public static String getMerchantClientKey() {
        if (ENVIRONMENT) {
            return MERCHANT_CLIENT_KEY_PRODUCTION;
        } else {
            return MERCHANT_CLIENT_KEY_SANDBOX;
        }
    }
}
