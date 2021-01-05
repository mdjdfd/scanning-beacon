package com.opensource.beacon.network;

public class ApiEndpoints {

    public static String getBasicPushUrl(){
        return Config.AIRSHIP_BASE_URL + "push";
    }

    public static String getSchedulePushUrl(){
        return Config.AIRSHIP_BASE_URL + "schedules/";
    }
}
