package huanju.chen.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HuanJu
 */
public class DateUtils {


    public static Date getMonthEnd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        String[] ymd=dateStr.split("-");
        String newDateStr=getMonthEnd(dateStr);
        Date endDate=null;
        try {
            endDate=sdf.parse(newDateStr);
        } catch (ParseException e) {
            endDate=null;
        }
        return endDate;
    }

    public static String getMonthEnd(String dateStr){
        String[] ymd=dateStr.split("-");

        StringBuilder builder=new StringBuilder(ymd[0]);
        builder.append('-');
        builder.append(ymd[1]);
        builder.append('-');
        builder.append(getMonthLastDay(dateStr));

        return builder.toString();
    }


    /**
     * 获取当前月最后一天
     */
    public static int getMonthLastDay(Date date) {
        if (date == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return getMonthLastDay(dateStr);
    }


    public static int getMonthLastDay(String dateStr) {
        String[] ymd = dateStr.split("-");
        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        return isLeapYear(year) ? 29 : 28;
    }

    /**
     * 查看year是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else return year % 4 == 0 && year % 100 != 0;
    }

}
