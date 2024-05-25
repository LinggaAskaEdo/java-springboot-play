package com.otis.play.util;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date convertStringToDate(String strDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

            return formatter.parse(strDate);
        } catch (ParseException e) {
            System.out.println("Error when convertStringToDate:" + e);

            return null;
        }
    }

    public static Timestamp convertDateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static BigInteger convertTimestampToBigInteger(Timestamp timestamp) {
        return BigInteger.valueOf(timestamp.getTime());
    }
}
