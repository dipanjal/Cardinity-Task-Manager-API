package rest.api.cardinity.taskmanager.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtils {

    public DateTimeUtils() {
    }

    //region Date Format
    private static final String[] dateFormats = {
            "dd-MM-yyyy",
            "dd/MM/yyyy",
            "dd-MMM-yyyy",
            "MM/dd/yyyy",
            "yyyy-MM-dd",
            "ddMMyyyy"
    };

    public static final String APP_DATE_FORMAT = "dd-MM-yyyy";
    public static final String API_DATE_FROMAT = "dd-MM-yyyy HH:mm:ss";


    public static String formatDate(Date date, String dateFormat) {
        return (date == null || StringUtils.isBlank(dateFormat))
                ? ""
                : new SimpleDateFormat(dateFormat).format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, APP_DATE_FORMAT);
    }

    public static String getCurrentDateString(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static Date convertToDate(String dateStr, String dateFormat) {
        if(StringUtils.isBlank(dateStr) || StringUtils.isBlank(dateFormat))
            return null;
        try {
            return new SimpleDateFormat(dateFormat).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static Date expireAtHour(int hour){
        return addHour(new Date(), hour);
    }

    public static int convertToMilli(int minute, int calenderFlag){
        if(Calendar.HOUR == calenderFlag) return 1000 * 60 * 60 * minute;
        if(Calendar.MINUTE == calenderFlag) return 1000 * 60 * minute;
        return 0;
    }

}
