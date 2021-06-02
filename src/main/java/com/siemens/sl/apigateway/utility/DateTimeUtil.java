package com.siemens.sl.apigateway.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    //    static String dateFormat = "dd/MM/yyyy::HH:mm:ss";
    public static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

    public static Date getDateFromString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Unable to parse : " + dateString);
            return null;
        }
    }

    /**
     * This method returns true only when all the three parameters are valid and the the timeToCheck lie in between
     * toDateTime and fromDateTime
     *
     * @param timeToCheck  - Time to be checked
     * @param fromDateTime - From date and time
     * @param toDateTime   - To date and time
     * @return - boolean
     */
    public static boolean isTimeInBetween(String timeToCheck, String fromDateTime, String toDateTime) {
        Date timeToCheckDate = getDateFromString(timeToCheck);
        Date fromDateTimeDate = getDateFromString(fromDateTime);
        Date toDateTimeDate = getDateFromString(toDateTime);

        return null != timeToCheckDate &&
                null != fromDateTimeDate &&
                null != toDateTimeDate &&
                timeToCheckDate.after(fromDateTimeDate) &&
                timeToCheckDate.before(toDateTimeDate);
    }

    /**
     * This method checks if a passed date is valid date or not
     *
     * @param dateToCheck - Pass here the date to be checked in string format
     * @return - true if date is valid else false
     */
    public static boolean isValidDate(String dateToCheck) {
        if (dateToCheck == null)
            return false;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            simpleDateFormat.parse(dateToCheck);
            return true;
        } catch (ParseException exception) {
            return false;
        }
    }
}
