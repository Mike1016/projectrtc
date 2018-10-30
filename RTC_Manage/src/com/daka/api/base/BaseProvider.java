package com.daka.api.base;

import com.daka.util.ExpiryMap;

public class BaseProvider {

    public static ExpiryMap<String, String> SMSMap = new ExpiryMap<>(1000*60);

}
