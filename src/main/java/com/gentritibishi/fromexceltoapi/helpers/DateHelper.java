package com.gentritibishi.fromexceltoapi.helpers;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import static com.gentritibishi.fromexceltoapi.helpers.Constants.DATE_TIME;

public class DateHelper {

    public static String getDateTime(Date netDate) {
        try {
            SimpleDateFormat sfd = new SimpleDateFormat(DATE_TIME, Locale.getDefault());
            return sfd.format(netDate);
        } catch (Exception e) {
            return "datetime";
        }
    }

    public static Boolean isAccountActive(Integer yyyy, Integer mm, Integer dd) {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(yyyy, mm, dd);

        long days = ChronoUnit.DAYS.between(now, date);

        if (days > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String formatDate(String date) {
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6);
        return year+"-"+month+"-"+day;
    }


}
