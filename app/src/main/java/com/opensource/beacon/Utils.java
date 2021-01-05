package com.opensource.beacon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Utils {

    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

    public static String getDeviceDateTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();


        return sdf.format(new Date((t + (1 * ONE_MINUTE_IN_MILLIS))));
    }
}
