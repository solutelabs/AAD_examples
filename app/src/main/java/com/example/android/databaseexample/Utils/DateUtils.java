package com.example.android.databaseexample.Utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date Utils
 */

public class DateUtils {
    private final static String pattern = "MMM dd, hh:mm a";

    public static String getFormatedDate(String strTimeStamp) {
        Long timeStamp = null;
        Log.d("TimeStamp", strTimeStamp);
        try {
            timeStamp = Long.parseLong(strTimeStamp);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        if (timeStamp != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);

            TimeZone tz = TimeZone.getTimeZone(calendar.getTimeZone().getID());

            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            sdf.setTimeZone(tz);

            return sdf.format(calendar.getTime());
        }
        return "";
    }
}
