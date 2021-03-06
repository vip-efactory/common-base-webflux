package vip.efactory.common.base.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Slf4j
public abstract class DateTimeUtil {
    public static final long ONE_YEAR = 366 * 24 * 60 * 60 * 1000L;
    public static final long ONE_DAY_SECOND = 24 * 60 * 60L;
    public static final String DATE_FORMAT_yyyy_MM_dd = "yyyy/MM/dd";
    public static final String DATE_FORMAT_yyyyMMdd = "yyyyMMdd";
    public static final String DATE_FORMAT_yyyyMM = "yyyyMM";
    public static final String DATE_FORMAR_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_yyyy_MM_dd_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_STARNDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String DATE_FORMAT_MM_dd_HH_MM_SS = "MM-dd HH:mm:ss";

    public final static String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public final static String HOUR_MINUTE = "HH:mm";

    public static SimpleDateFormat formatterYMDHMS = new SimpleDateFormat(DATE_STARNDARD_FORMAT);
    public static SimpleDateFormat formatterYMD = new SimpleDateFormat(YEAR_MONTH_DAY);
    public static SimpleDateFormat formatterHM = new SimpleDateFormat(HOUR_MINUTE);
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY);

    private DateTimeUtil() {
        // do nothing
    }

    public static final String SDF = "sdf";
    public static final String SDF1 = "sdf1";
    public static final String SDF2 = "sdf2";
    public static final String SDF3 = "sdf3";
    public static final String SDF4 = "sdf4";
    public static final String SDF5 = "sdf5";
    public static final String SDF6 = "sdf6";
    public static final String SDF7 = "sdf7";
    public static final String SDF8 = "sdf8";
    public static final String SDF9 = "sdf9";
    public static final String SDF10 = "sdf10";
    /**
     * use threadlocal of DateFormat to avoid date format exception when used in multithread environment.
     */
    private static ThreadLocal<Map<String, DateFormat>> threadLocal = new ThreadLocal<Map<String, DateFormat>>() {
        @Override
        protected Map<String, DateFormat> initialValue() {
            Map<String, DateFormat> map = new HashMap<String, DateFormat>();
            map.put(SDF, formatterYMDHMS);
            map.put(SDF1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            map.put(SDF2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
            map.put(SDF3, new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"));
            map.put(SDF4, new SimpleDateFormat("MMddHHmmyyyy.ss"));
            map.put(SDF5, new SimpleDateFormat("yyyyMMdd"));
            map.put(SDF6, new SimpleDateFormat("yyyyMMddHHmmss"));
            map.put(SDF7, formatterYMD);
            map.put(SDF8, new SimpleDateFormat("yyyyMMddHHmmssS"));
            map.put(SDF9, new SimpleDateFormat("yyyy-MM-dd HH:mm"));
            map.put(SDF10, new SimpleDateFormat("yyyy/MM/dd HH:mm"));
            return map;
        }
    };


    /**
     * Description:????????????????????????2018/10/12????????????
     *
     * @return java.lang.String
     * @author dbdu
     */
    public static String getCurrentDate() {
        return getCurrentDate(TimeZone.getDefault());
    }

    /**
     * Description:get current year ,like :2016
     *
     * @param
     * @return Integer
     * @author dbdu
     */
    public static Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getCurrentDate(TimeZone timeZone) {
        return getCurrentDate(DATE_FORMAT_yyyy_MM_dd, timeZone);
    }

    public static String getCurrentDate(String pattern) {
        return getCurrentDate(pattern, TimeZone.getDefault());
    }

    public static String getCurrentDate(String pattern, TimeZone timeZone) {
        return getFormatDateTime(new Date(), pattern, timeZone);
    }

    public static String getFormatDateTime(Date dateValue, String strFormat, TimeZone timeZone) {
        DateFormat formatter = new SimpleDateFormat(strFormat);
        formatter.setTimeZone(timeZone);
        return formatter.format(dateValue);
    }

    public static String getDateAfter(long inter, String format) {
        Date d1 = new Date();
        d1.setTime(d1.getTime() + inter * 24 * 3600 * 1000);
        return getFormatDateTime(d1, format, TimeZone.getDefault());
    }

    /**
     * For a instance : getDateBefore(10)
     *
     * @param inter -
     * @return get the day before today
     */
    public static String getDateBefore(long inter) {
        Date today = new Date();
        today.setTime(today.getTime() - inter * 24 * 3600 * 1000);
        return getFormatDateTime(today, DATE_FORMAT_yyyy_MM_dd, TimeZone.getDefault());
    }

    public static Calendar getCalendarByTimeStr(String timeStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(changeDateStringToLong(timeStr));
        return calendar;
    }

    /**
     * Return the start time of previous month. For example, current date is 2016/03/29: will return long value of 2016/02/01 00:00:00
     */
    public static long getPreviousMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, 1);
        String startTimeStr = getFormatDateTime(calendar.getTime(), DATE_FORMAT_yyyy_MM_dd, TimeZone.getDefault());
        return changeDateStringToLong(startTimeStr);
    }

    /**
     * Return the end time of previous month. For example, current date is 2016/03/29: will return long value of 2016/02/29 00:00:00
     */
    public static long getPreviousMonthEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        String startTimeStr = getFormatDateTime(calendar.getTime(), DATE_FORMAT_yyyy_MM_dd, TimeZone.getDefault());
        return changeDateStringToLong(startTimeStr);
    }

    /**
     * Return the start time of current month. For example, current date is 2016/03/29: will return long value of 2016/03/01 00:00:00
     */
    public static long getCurrentMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        String startTimeStr = getFormatDateTime(calendar.getTime(), DATE_FORMAT_yyyy_MM_dd, TimeZone.getDefault());
        return changeDateStringToLong(startTimeStr);
    }

    /**
     * Get the input year start time. For example, the input is 2016: will return long value of 2016/01/01 00:00:00
     */
    public static long getSpecificYearStart(Integer s_year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(changeDateStringToLong(s_year + "/1/1"));
        String startTimeStr = getFormatDateTime(calendar.getTime(), DATE_FORMAT_yyyy_MM_dd, TimeZone.getDefault());
        return changeDateStringToLong(startTimeStr);
    }

    /**
     * Get the next year of input year start time. For example, the input is
     * 2016: will return long value of 2017/01/01 00:00:00
     */
    public static long getSpecificNextYearStart(Integer s_year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(changeDateStringToLong(++s_year + "/1/1"));
        String startTimeStr = getFormatDateTime(calendar.getTime(), DATE_FORMAT_yyyy_MM_dd, TimeZone.getDefault());
        return changeDateStringToLong(startTimeStr);
    }

    public static int getDateDiff(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        Long lngDiff = (c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);
        return (lngDiff.intValue());
    }

    public static int getDateMinutes() {
        //return Calendar.getInstance().getTime().getMinutes();
        String minutes = getFormatDateTime(Calendar.getInstance().getTime(), "mm", TimeZone.getDefault());
        return Integer.valueOf(minutes);
    }

    public static int getDateSeconds() {
        //return Calendar.getInstance().getTime().getMinutes();
        String seconds = getFormatDateTime(Calendar.getInstance().getTime(), "ss", TimeZone.getDefault());
        return Integer.valueOf(seconds);
    }

    public static long changeDateStringToLong(String s_date) {
        DateFormat format = new SimpleDateFormat(DATE_FORMAT_yyyy_MM_dd);
        Date date;
        long l = System.currentTimeMillis();
        try {
            date = format.parse(s_date);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            l = c.getTimeInMillis();
        } catch (Exception e) {
            log.error("Error to conver to long value", e);
        }
        return l;
    }

    /**
     * ?????????????????????????????????????????????????????????:
     * "yyyy/MM/dd"???"yyyy/MM/dd HH:mm"???"yyyy/MM/dd HH:mm:ss"
     * "yyyy_MM_dd"???"yyyy_MM_dd HH:mm"???yyyy_MM_dd HH:mm:ss"
     * "yyyy-MM-dd"???"yyyy-MM-dd HH:mm"???"yyyy-MM-dd HH:mm:ss"
     * "yyyyMMdd"???"yyyyMMdd HH:mm"???"yyyyMMdd HH:mm:ss"
     * "yyyyMMdd HHmmss"???"yyyyMMdd HHmm"
     * "HH:mm"???"HH:mm:ss"
     * dbdu at 2019-0707
     *
     * @param dateString
     * @return
     */
    public static Date getDateFromString(String dateString) {
        if (!CommUtil.isEmptyString(dateString)) {
            String pattern = "";
            pattern = getPatternFromDateString(dateString);
            assert pattern != null;
            DateFormat df = new SimpleDateFormat(pattern);
            Date date;
            try {
                date = df.parse(dateString);
            } catch (ParseException e) {
                log.warn(e.getMessage());
                return null;
            }

            return date;
        }
        return null;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     * ?????????????????????????????????????????????????????????:
     * "yyyy/MM/dd"???"yyyy/MM/dd HH:mm"???"yyyy/MM/dd HH:mm:ss"
     * "yyyy_MM_dd"???"yyyy_MM_dd HH:mm"???yyyy_MM_dd HH:mm:ss"
     * "yyyy-MM-dd"???"yyyy-MM-dd HH:mm"???"yyyy-MM-dd HH:mm:ss"
     * "yyyyMMdd"???"yyyyMMdd HH:mm"???"yyyyMMdd HH:mm:ss"
     * "yyyyMMdd HHmmss"???"yyyyMMdd HHmm"
     * "HH:mm"???"HH:mm:ss"
     *
     * @param dateString ???????????????
     * @return pattern ???????????????
     */
    private static String getPatternFromDateString(String dateString) {
        if (CommUtil.isEmptyString(dateString)) {
            return null;
        }
        String pattern = null;
        if (dateString.length() == 19) { // ????????????
            if (dateString.indexOf("/") > 0) {
                pattern = "yyyy/MM/dd HH:mm:ss";
            } else if (dateString.indexOf("_") > 0) {
                pattern = "yyyy_MM_dd HH:mm:ss";
            } else if (dateString.indexOf("-") > 0) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
        } else if (dateString.length() == 17) { // ????????????????????????????????????
            pattern = "yyyyMMdd HH:mm:ss";
        } else if (dateString.length() == 16) { // ?????????????????????
            if (dateString.indexOf("/") > 0) {
                pattern = "yyyy/MM/dd HH:mm";
            } else if (dateString.indexOf("_") > 0) {
                pattern = "yyyy_MM_dd HH:mm";
            } else if (dateString.indexOf("-") > 0) {
                pattern = "yyyy-MM-dd HH:mm";
            }
        } else if (dateString.length() == 15) { // ????????????????????????????????????????????????????????????
            pattern = "yyyyMMdd HHmmss";
        } else if (dateString.length() == 14) { // ?????????????????????????????????????????????
            pattern = "yyyyMMdd HH:mm";
        } else if (dateString.length() == 13) { // ?????????????????????????????????????????????
            pattern = "yyyyMMdd HHmm";
        } else if (dateString.length() == 10) { // ??????????????????????????????
            if (dateString.indexOf("/") > 0) {
                pattern = "yyyy/MM/dd";
            } else if (dateString.indexOf("_") > 0) {
                pattern = "yyyy_MM_dd";
            } else if (dateString.indexOf("-") > 0) {
                pattern = "yyyy-MM-dd";
            }
        } else if (dateString.length() == 8) { // ?????????????????????????????????????????????????????????
            if (dateString.indexOf(":") > 0) {
                pattern = "HH:mm:ss";
            } else {
                pattern = "yyyyMMdd";
            }
        } else if (dateString.length() == 5) {  // ?????????????????????
            if (dateString.indexOf(":") > 0) {
                pattern = "HH:mm";
            }
        } else {
            log.warn("?????????????????????:" + dateString);
        }
        return pattern;
    }

    /**
     * ??????????????????????????????LocalDateTime?????????????????????:
     * "yyyy/MM/dd HH:mm"???"yyyy/MM/dd HH:mm:ss"
     * "yyyy_MM_dd HH:mm"???yyyy_MM_dd HH:mm:ss"
     * "yyyy-MM-dd HH:mm"???"yyyy-MM-dd HH:mm:ss"
     * "yyyyMMdd HH:mm"???"yyyyMMdd HH:mm:ss"
     * "yyyyMMdd HHmmss"
     * ISO_LOCAL_DATE_TIME,??????:2011-12-03T10:15:30 DateTimeFormatter.ISO_LOCAL_DATE_TIME
     *
     * @param dateString ????????????????????????
     * @return LocalDateTime java8?????????????????????
     */
    public static LocalDateTime getLocalDateTimeFromString(String dateString) {
        if (!CommUtil.isEmptyString(dateString)) {
            if (dateString.contains("T") && dateString.length() == 19) {
                // ??????????????????ISO_LOCAL_DATE_TIME??????
                return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else if (!dateString.contains("T") && dateString.length() >= 13 && dateString.length() <= 19) {
                // ?????????????????????????????????
                String pattern = getPatternFromDateString(dateString);
                assert pattern != null;
                return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(pattern));
            }
        }
        return null;
    }

    /**
     * ??????????????????????????????LocalDate?????????????????????:
     * "yyyy/MM/dd"???"yyyy/MM/dd HH:mm"???"yyyy/MM/dd HH:mm:ss"
     * "yyyy_MM_dd"???"yyyy_MM_dd HH:mm"???yyyy_MM_dd HH:mm:ss"
     * "yyyy-MM-dd"???"yyyy-MM-dd HH:mm"???"yyyy-MM-dd HH:mm:ss"
     * "yyyyMMdd"???"yyyyMMdd HH:mm"???"yyyyMMdd HH:mm:ss"
     * "yyyyMMdd HHmmss"???"yyyyMMdd HHmm"
     * ISO_LOCAL_DATE_TIME,??????:2011-12-03T10:15:30 DateTimeFormatter.ISO_LOCAL_DATE_TIME
     *
     * @param dateString ????????????????????????
     * @return LocalDate java8?????????????????????
     */
    public static LocalDate getLocalDateFromString(String dateString) {
        if (!CommUtil.isEmptyString(dateString)) {
            // ?????????????????????????????????
            if (dateString.length() >= 13 && dateString.length() <= 19) {
                // ??????????????????ISO_LOCAL_DATE_TIME??????
                return Objects.requireNonNull(getLocalDateTimeFromString(dateString)).toLocalDate();
            } else if (dateString.length() >= 8 && dateString.length() <= 10 && !dateString.contains(":")) {
                // ????????????????????????????????????
                String pattern = getPatternFromDateString(dateString);
                assert pattern != null;
                return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern));
            }
        }
        return null;
    }

    /**
     * ??????????????????????????????LocalTime?????????????????????:
     * "yyyy/MM/dd HH:mm"???"yyyy/MM/dd HH:mm:ss"
     * "yyyy_MM_dd HH:mm"???yyyy_MM_dd HH:mm:ss"
     * "yyyy-MM-dd HH:mm"???"yyyy-MM-dd HH:mm:ss"
     * "yyyyMMdd HH:mm"???"yyyyMMdd HH:mm:ss"
     * "yyyyMMdd HHmmss"???"yyyyMMdd HHmm"
     * "HH:mm"???"HH:mm:ss"
     * ISO_LOCAL_DATE_TIME,??????:2011-12-03T10:15:30 DateTimeFormatter.ISO_LOCAL_DATE_TIME
     *
     * @param dateString ????????????????????????
     * @return LocalTime java8?????????????????????
     */
    public static LocalTime getLocalTimeFromString(String dateString) {
        if (!CommUtil.isEmptyString(dateString)) {
            // ?????????????????????????????????
            if (dateString.length() >= 13 && dateString.length() <= 19) {
                return Objects.requireNonNull(getLocalDateTimeFromString(dateString)).toLocalTime();
            } else if (dateString.length() <= 8 && dateString.contains(":")) {
                // "HH:mm"???"HH:mm:ss"
                String pattern = getPatternFromDateString(dateString);
                assert pattern != null;
                return LocalTime.parse(dateString, DateTimeFormatter.ofPattern(pattern));
            }
        }
        return null;
    }

    public static String getDateStrFromLong(long dateLong) {
        return getDateStrFromLong(dateLong, new SimpleDateFormat(DATE_FORMAT_yyyy_MM_dd));
    }

    public static String getFullDateStrFromLong(long dateLong) {
        return getDateStrFromLong(dateLong, new SimpleDateFormat(DATE_FORMAT_yyyy_MM_dd_HH_MM_SS));
    }

    public static String getHHMMSSDateStrFromLong(long dateLong) {
        return getDateStrFromLong(dateLong, new SimpleDateFormat(DATE_FORMAT_HH_MM_SS));
    }

    public static String getMdHmsDateStrFromLong(long dateLong) {
        return getDateStrFromLong(dateLong, new SimpleDateFormat(DATE_FORMAT_MM_dd_HH_MM_SS));
    }

    /**
     * Description:get time string from os,string like "2016/04/05 15:31:36"
     *
     * @param
     * @return String
     * @author dbdu
     */
    public static String getOsCurrentTime() {
        //get currentTime from os
        Long currentLong = System.currentTimeMillis();
        String currentTime = DateTimeUtil.getFullDateStrFromLong(currentLong);
        return currentTime;
    }

    public static String getDateStrFromLong(long dateLong, SimpleDateFormat sdFormat) {
        sdFormat.setTimeZone(TimeZone.getDefault());
        return sdFormat.format(new Date(dateLong));
    }

    /**
     * ??????????????????????????????12?????????eg:today is 2016/03/24,return 2015/03/01 00:00:00
     *
     * @return
     */
    public static long getCurrYesteryear() {
        long curMonthStart = DateTimeUtil.getCurrentMonthStart();
        String dtString = DateTimeUtil.getFullDateStrFromLong(curMonthStart);
        int year = DateTimeUtil.getCurrentYear().intValue() - 1;

        String yesterYear = year + dtString.substring(4, dtString.length());
        //System.out.println("??????????????????????????????12??????:" + yesterYear);

        return getTimeFromString(yesterYear);
    }

    /**
     * ???????????????????????????
     *
     * @return long
     */
    public static long getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * ??????????????????????????? ???like???2015/01/01 00:00:00
     *
     * @param year
     * @return long
     */
    public static long getYearFirst(int year) {
        if (CommUtil.isEmptyInt(year)) {
            return 0l;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        long currYearFirst = calendar.getTimeInMillis();
        return currYearFirst;
    }

    /**
     * ????????????????????????????????????????????????like???2015/12/31 23:59:59
     *
     * @param year
     * @return long
     */
    public static long getYearLast(int year) {
        if (CommUtil.isEmptyInt(year)) {
            return 0l;
        }
        String fullDateString = year + "/12/31 23:59:59";
        long currYearLast = getTimeFromString(fullDateString);

        /*String yearLast = DateTimeUtil.getFullDateStrFromLong(currYearLast);
        System.out.println("getYearLast:" + yearLast);*/
        return currYearLast;
    }

    /**
     * Description:dateString ?????????yyyy-MM-dd HH:mm:ss???????????????
     *
     * @param dateString ???????????????
     * @return java.util.Date
     * @author dbdu
     */
    public static Date toDate(String dateString) {
        if (CommUtil.isEmptyString(dateString))
            return null;
        try {
            if (dateString.indexOf('/') != -1) {
                return threadLocal.get().get(SDF2).parse(dateString);
            } else if (dateString.indexOf('-') != -1) {
                return threadLocal.get().get(SDF).parse(dateString);
            }
        } catch (Exception e) {
            log.error("Error to conver date from String", e);
        }
        return null;
    }

    /**
     * Description:dateString ?????????yyyy-MM-dd HH:mm?????????
     *
     * @param dateString ???????????????
     * @return java.util.Date
     * @author dbdu
     */
    public static Date toDateWithoutSecond(String dateString) {
        if (CommUtil.isEmptyString(dateString))
            return null;
        try {
            if (dateString.indexOf('/') != -1) {
                return threadLocal.get().get(SDF10).parse(dateString);
            } else if (dateString.indexOf('-') != -1) {
                return threadLocal.get().get(SDF9).parse(dateString);
            }
        } catch (Exception e) {
            log.error("Error to conver date from String", e);
        }
        return null;
    }

    public static Long toSqlDateTime(String dateString) {
        return toSqlDateTime(dateString, false);
    }

    public static Long toSqlDateTime(String dateString, boolean endTime) {
        Long ret = null;
        Date d = null;
        if (CommUtil.isEmptyString(dateString))
            return ret;
        try {
            dateString = dateString.replace(" 00:00:00", "");
            if (dateString.indexOf(':') == -1) {
                if (endTime) {
                    dateString = dateString.trim() + " 23:59:59";
                } else {
                    dateString = dateString.trim() + " 00:00:00";
                }
            }

            if (dateString.indexOf('/') == 2) {
                d = threadLocal.get().get(SDF3).parse(dateString);
            } else if (dateString.indexOf('/') != -1) {
                d = threadLocal.get().get(SDF2).parse(dateString);
            } else if (dateString.indexOf('-') != -1) {
                d = threadLocal.get().get(SDF).parse(dateString);
            }
            if (null != d) {
                ret = d.getTime();
                if (endTime) {
                    ret += 999;
                }
            }
        } catch (Exception e) {
            log.error("Error to Parse the date time: " + dateString, e);
        }
        return ret;
    }

    /**
     * according type to generate date object
     *
     * @param datetime
     * @param type     0:"yyyy-MM-dd HH:mm:ss" 1:"yyyy/MM/dd HH:mm:ss"
     *                 2:"MM/dd/yyyy HH:mm:ss" 3:"MMddhhmmyyyy.ss" 4:"yyyyMMdd"
     *                 5:"yyyyMMddHHmmss" 6:"yyyy-MM-dd" 7:"yyyyMMddHHmmssS" 8:"yyyy-MM-dd HH:mm"
     * @return
     */
    public static String getDateString(long datetime, int type) {
        if (-1 == datetime) {
            return "";
        }
        Date date = new Date(datetime);
        try {
            if (type == 0) {
                return threadLocal.get().get(SDF).format(date);
            } else if (type == 1) {
                return threadLocal.get().get(SDF2).format(date);
            } else if (type == 2) {
                return threadLocal.get().get(SDF3).format(date);
            } else if (type == 3) {
                return threadLocal.get().get(SDF4).format(date);
            } else if (type == 4) {
                return threadLocal.get().get(SDF5).format(date);
            } else if (type == 5) {
                return threadLocal.get().get(SDF6).format(date);
            } else if (type == 6) {
                return threadLocal.get().get(SDF7).format(date);
            } else if (type == 7) {
                return threadLocal.get().get(SDF8).format(date);
            } else if (type == 8) {
                return threadLocal.get().get(SDF9).format(date);
            }
        } catch (Exception e) {
            log.error("Error to get data String", e);
        }
        return "";
    }

    /**
     * Description:??????????????????2018-10-12 13:58:22????????????
     *
     * @return java.lang.String
     * @author dbdu
     */
    public static String getCurDateStr() {
        return getDateString(System.currentTimeMillis(), 0);
    }

    /**
     * according type to generate date object
     *
     * @param datetime
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String getDateString(Long datetime) {
        if (CommUtil.isEmptyLong(datetime) || datetime == 0) {
            return "";
        }
        return getDateString(datetime, 0);
    }

    /**
     * according type to generate date object
     *
     * @param datetime
     * @return "yyyy-MM-dd HH:mm"
     */
    public static String getMinDateString(Long datetime) {
        if (CommUtil.isEmptyLong(datetime) || datetime == 0) {
            return "";
        }
        return getDateString(datetime, 8);
    }

    /**
     * according type to generate date object
     *
     * @param date
     * @param type 0:"yyyy-MM-dd HH:mm:ss" 1:"yyyy/MM/dd HH:mm:ss"
     *             2:"MM/dd/yyyy HH:mm:ss" 3:"MMddhhmmyyyy.ss" 4:"yyyyMMdd"
     *             5:"yyyyMMddHHmmss" 6:"yyyy-MM-dd" 7:"yyyyMMddHHmmssS"
     * @return
     */
    public static String getDateString(Date date, int type) {
        if (date == null)
            return "";
        try {
            if (type == 0) {
                return threadLocal.get().get(SDF).format(date);
            } else if (type == 1) {
                return threadLocal.get().get(SDF2).format(date);
            } else if (type == 2) {
                return threadLocal.get().get(SDF3).format(date);
            } else if (type == 3) {
                return threadLocal.get().get(SDF4).format(date);
            } else if (type == 4) {
                return threadLocal.get().get(SDF5).format(date);
            } else if (type == 5) {
                return threadLocal.get().get(SDF6).format(date);
            } else if (type == 6) {
                return threadLocal.get().get(SDF7).format(date);
            } else if (type == 7) {
                return threadLocal.get().get(SDF8).format(date);
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * ???????????????????????????long??????????????????
     *
     * @param time
     * @param value
     * @return
     */
    public static Long getDateByAdd(Long time, int value) {
        if (time == null)
            return null;
        Long date = time + ((long) value * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date fromLong(long time) {
        Date date = new Date(time);
        return date;
    }

    /**
     * Description:dateString ?????????yyyy-MM-dd HH:mm:ss???????????????
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     *
     * @param dateString ???????????????
     * @return long ;if success return date object's time, else return -1
     * @author dbdu
     */
    public static long getTimeFromString(String dateString) {
        Date date = toDate(dateString);
        if (date != null)
            return date.getTime();
        return -1;
    }

    /**
     * Description:dateString ?????????yyyy-MM-dd HH:mm:ss???????????????
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     *
     * @param dateString ???????????????
     * @return java.lang.Long
     * @author dbdu
     */
    public static Long getTimeMSFromString(String dateString) {
        Date date = toDate(dateString);
        if (date != null)
            return date.getTime();
        return null;
    }

    public static long convertStringToMills(String dateString, String type) {
        Calendar calendar = Calendar.getInstance();
        long mills = 0;
        try {
            calendar.setTime(threadLocal.get().get(type).parse(dateString));
            mills = calendar.getTimeInMillis();
        } catch (ParseException e) {
            log.error("parse date string error", e);
        }
        return mills;
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return dateFormatter;
    }

    /**
     * ??????????????????
     *
     * @return ?????????????????? yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDateFull() {
        String dateString = formatterYMDHMS.format(new Date());
        //???string to date
        Date now = toDate(dateString);
        return now;
    }

    /**
     * ??????????????????
     *
     * @return????????????????????? yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        String dateString = formatterYMD.format(new Date());
        try {
            Date currentTime_2 = formatterYMD.parse(dateString);
            return currentTime_2;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * ??????????????????
     *
     * @return????????????????????? yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        String dateString = formatterYMDHMS.format(new Date());
        return dateString;
    }

    /**
     * ??????????????????
     *
     * @return????????????????????? yyyyMMddHHmmss
     */
    public static String getStringAllDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * ??????????????????
     *
     * @return ??????????????????????????????yyyy-MM-dd
     */
    public static String getStringDateShort() {
        String dateString = formatterYMD.format(new Date());
        return dateString;
    }

    /**
     * ???????????? ??????:???;??? HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * ?????????????????????????????????????????? yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatterYMDHMS.parse(strDate, pos);
        return strtodate;
    }

    /**
     * ?????????????????????????????????????????? yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        String dateString = formatterYMDHMS.format(dateDate);
        return dateString;
    }

    /**
     * ?????????????????????????????????????????? yyyy-MM-dd
     *
     * @param dateDate
     * @param k
     * @return
     */
    public static String dateToStr(Date dateDate) {
        String dateString = formatterYMD.format(dateDate);
        return dateString;
    }

    public static String dateToStr(java.time.LocalDate dateDate) {
        String dateString = dateFormatter.format(dateDate);
        return dateString;
    }


    /**
     * ?????????????????????????????????????????? yyyy-MM-dd HH:mm:ss
     *  ?????????????????????????????????????????????null
     * @param strDate
     * @return
     */

  /*  public static Timestamp strToDateSql(String strDate) {
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatterYMDHMS.parse(strDate, pos);
        return new Timestamp(strtodate.getTime());
    }*/

    /**
     * ??????????????????
     *
     * @return
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * ?????????????????????????????????
     *
     * @param day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * ??????????????????
     *
     * @return ????????? yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * ?????????
     *
     * @version 2016???12???16??? ??????4:41:51
     */
    public static String getTodayShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * @param @param  value
     * @param @return
     * @return String
     * @throws
     * @Description: ????????????????????????????????????, ???????????????????????????????????????
     * ???????????????568
     * ??????????????????00:09:28
     * ??????null????????????
     * ???????????????:00:00:00
     */
    public static String getHHMMSS(String value) {
        String hour = "00";
        String minute = "00";
        String second = "00";
        if (value != null && !value.trim().equals("")) {
            int v_int = Integer.valueOf(value);
            hour = v_int / 3600 + "";//????????????;
            minute = v_int % 3600 / 60 + "";//????????????;
            second = v_int % 3600 % 60 + "";//????????????;
        }
        return (hour.length() > 1 ? hour : "0" + hour) + ":" + (minute.length() > 1 ? minute : "0" + minute) + ":" + (second.length() > 1 ? second : "0" + second);
    }

    /**
     * ??????????????????
     */
    public static String getHour() {
        Date currentTime = new Date();
        String dateString = formatterYMDHMS.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        String dateString = formatterYMDHMS.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * ????????????????????????????????????????????????????????????????????? ?????????yyyyMMdd???????????????y???????????????
     *
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * ??????????????????????????????,??????????????????????????????"HH:MM"????????????????????????????????????
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * ????????????????????????????????????
     */
    public static String getTwoDay(String sj1, String sj2) {
        long day = 0;
        try {
            Date date = formatterYMD.parse(sj1);
            Date mydate = formatterYMD.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * ???????????????????????????,??????JJ????????????.
     */
    public static String getPreTime(String sj1, String jj) {
        String mydate1 = "";
        try {
            Date date1 = formatterYMDHMS.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = formatterYMDHMS.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * ????????????????????????????????????????????????,nowdate(yyyy-mm-dd)?????????,delay???????????????????????????
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            String mdate = "";
            Date d = getDateFromString(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = formatterYMD.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * ????????? ????????????????????????????????????
     * ??????????????????????????????????????????yyyy-MM-dd HH:mm:ss
     * day  ????????????????????????????????????
     * ????????????????????????????????????
     */
    public static String getFromNow(int day) {
        Date date = new Date();
        long dateTime = (date.getTime() / 1000) + day * 24 * 60 * 60;
        date.setTime(dateTime * 1000);
        return formatterYMDHMS.format(date);
    }

    /**
     * ??????????????????,ddate??????:"yyyy/MM/dd";"yyyy_MM_dd";"yyyy-MM-dd";"yyyyMMdd"
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {

        /**
         * ??????????????? 1.???400??????????????????????????? 2.?????????4????????????????????? 3.??????4?????????????????????100??????????????????
         * 3.??????4??????????????????100?????????????????????
         */
        Date d = getDateFromString(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    /**
     * ???????????????????????? 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatterYMD.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * ??????????????????????????????
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * ???????????????????????????????????????
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // ??????12???????????????????????????????????????????????????????????????????????????????????????
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * ???????????????,????????????????????????????????????????????????
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * ??????????????????????????????????????????????????????????????????2002???2???3?????????????????????????????????
     * sdate??????:"yyyy/MM/dd";"yyyy_MM_dd";"yyyy-MM-dd";"yyyyMMdd"
     */
    public static String getWeek(String sdate, String num) {
        // ??????????????????
        Date dd = getDateFromString(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // ??????????????????????????????
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // ??????????????????????????????
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // ??????????????????????????????
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // ??????????????????????????????
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // ??????????????????????????????
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // ??????????????????????????????
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // ??????????????????????????????
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * ??????????????????(????????????????????????)?????????????????????????????????
     * sdate:"yyyy/MM/dd";"yyyy_MM_dd";"yyyy-MM-dd";"yyyyMMdd"
     */
    public static String getWeek(String sdate) {
        // ??????????????????
        Date date = getDateFromString(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour??????????????????????????????????????? 1~7
        // 1=????????? 7=????????????????????????
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public static String getWeekStr(String sdate) {
        String str = "";
        str = getWeek(sdate);
        if ("1".equals(str)) {
            str = "?????????";
        } else if ("2".equals(str)) {
            str = "?????????";
        } else if ("3".equals(str)) {
            str = "?????????";
        } else if ("4".equals(str)) {
            str = "?????????";
        } else if ("5".equals(str)) {
            str = "?????????";
        } else if ("6".equals(str)) {
            str = "?????????";
        } else if ("7".equals(str)) {
            str = "?????????";
        }
        return str;
    }

    /**
     * ???????????????????????????
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // ?????????????????????
        Date date = null;
        Date mydate = null;
        try {
            date = formatterYMD.parse(date1);
            mydate = formatterYMD.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * ????????????????????? ??? ????????????????????????????????????????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ??????????????????????????????
     * ????????????????????????????????????????????????????????? sdate:"yyyy/MM/dd";"yyyy_MM_dd";"yyyy-MM-dd";"yyyyMMdd"
     */
    public static String getNowMonth(String sdate) {
        // ??????????????????????????????
        sdate = sdate.substring(0, 8) + "01";

        // ??????????????????1???????????????
        Date date = getDateFromString(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        String newday = getNextDay(sdate, (1 - u) + "");
        return newday;
    }

    /**
     * ????????????????????? ???????????????yyyymmddhhmmss+k????????????
     *
     * @param k ?????????????????????????????????????????????
     */

    public static String getNo(int k) {

        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * ?????????????????????
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    /***************************************************************************
     * //nd=1????????????????????????????????? //yf=1????????????????????????????????? //rq=1????????????????????????????????? //format????????????????????? 1
     * ???????????????????????? 2 ?????????-?????? // 3 ?????????/?????? 4 ??????????????????????????????????????? // 5 ?????????.??????
     **************************************************************************/
    public static String getStringDateMonth(String sdate, String nd, String yf, String rq, String format) {
        Date currentTime = new Date();
        String dateString = formatterYMD.format(currentTime);
        String s_nd = dateString.substring(0, 4); // ??????
        String s_yf = dateString.substring(5, 7); // ??????
        String s_rq = dateString.substring(8, 10); // ??????
        String sreturn = "";
        //roc.util.MyChar mc = new roc.util.MyChar();
        //if (sdate == null || sdate.equals("") || !mc.Isdate(sdate)) { // ??????????????????
        if (sdate == null || sdate.equals("")) {
            if (nd.equals("1")) {
                sreturn = s_nd;
                // ???????????????
                if (format.equals("1"))
                    sreturn = sreturn + "???";
                else if (format.equals("2"))
                    sreturn = sreturn + "-";
                else if (format.equals("3"))
                    sreturn = sreturn + "/";
                else if (format.equals("5"))
                    sreturn = sreturn + ".";
            }
            // ????????????
            if (yf.equals("1")) {
                sreturn = sreturn + s_yf;
                if (format.equals("1"))
                    sreturn = sreturn + "???";
                else if (format.equals("2"))
                    sreturn = sreturn + "-";
                else if (format.equals("3"))
                    sreturn = sreturn + "/";
                else if (format.equals("5"))
                    sreturn = sreturn + ".";
            }
            // ????????????
            if (rq.equals("1")) {
                sreturn = sreturn + s_rq;
                if (format.equals("1"))
                    sreturn = sreturn + "???";
            }
        } else {
            // ??????????????????????????????????????????????????????????????????????????????????????????
            sdate = getOKDate(sdate);
            s_nd = sdate.substring(0, 4); // ??????
            s_yf = sdate.substring(5, 7); // ??????
            s_rq = sdate.substring(8, 10); // ??????
            if (nd.equals("1")) {
                sreturn = s_nd;
                // ???????????????
                if (format.equals("1"))
                    sreturn = sreturn + "???";
                else if (format.equals("2"))
                    sreturn = sreturn + "-";
                else if (format.equals("3"))
                    sreturn = sreturn + "/";
                else if (format.equals("5"))
                    sreturn = sreturn + ".";
            }
            // ????????????
            if (yf.equals("1")) {
                sreturn = sreturn + s_yf;
                if (format.equals("1"))
                    sreturn = sreturn + "???";
                else if (format.equals("2"))
                    sreturn = sreturn + "-";
                else if (format.equals("3"))
                    sreturn = sreturn + "/";
                else if (format.equals("5"))
                    sreturn = sreturn + ".";
            }
            // ????????????
            if (rq.equals("1")) {
                sreturn = sreturn + s_rq;
                if (format.equals("1"))
                    sreturn = sreturn + "???";
            }
        }
        return sreturn;
    }

    public static String getNextMonthDay(String sdate, int m) {
        sdate = getOKDate(sdate);
        int year = Integer.parseInt(sdate.substring(0, 4));
        int month = Integer.parseInt(sdate.substring(5, 7));
        month = month + m;
        if (month < 0) {
            month = month + 12;
            year = year - 1;
        } else if (month > 12) {
            month = month - 12;
            year = year + 1;
        }
        String smonth = "";
        if (month < 10)
            smonth = "0" + month;
        else
            smonth = "" + month;
        return year + "-" + smonth + "-10";
    }

    /**
     * ?????????
     *
     * @author Tony
     * @version 2015-3-31 ??????09:29:31 <br/>
     */
    public static String getOKDate(String sdate) {
        if (sdate == null || sdate.equals(""))
            return getStringDateShort();

//	  if (!VeStr.Isdate(sdate)) {
//	   sdate = getStringDateShort();
//	  }
//	  // ??????/???????????????-???
//	  sdate = VeStr.Replace(sdate, "/", "-");
        // ????????????8??????????????????????????????
        if (sdate.length() == 8)
            sdate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-" + sdate.substring(6, 8);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatterYMD.parse(sdate, pos);
        String dateString = formatterYMD.format(strtodate);
        return dateString;
    }

    /**
     * ????????????????????????????????????
     */
    private static String getBeforeDay(Calendar cl) {
        //??????roll????????????????????????
        //cl.roll(Calendar.DATE, -1);
        //??????set????????????????????????
        // int day = cl.get(Calendar.DATE);
        cl.add(Calendar.DATE, -1);
        return formatterYMD.format(cl.getTime());
    }

    /**
     * ????????????????????????????????????
     */
    private static String getAfterDay(Calendar cl) {
        //??????roll???????????????????????????????????????
        //cl.roll(Calendar.DATE, 1);
        //??????set???????????????????????????
        //int day = cl.get(Calendar.DATE);
        cl.add(Calendar.DATE, 1);
        return formatterYMD.format(cl.getTime());
    }

    private static String getDateAMPM() {
        GregorianCalendar ca = new GregorianCalendar();
        //????????????0????????????     ????????????1????????????
        int i = ca.get(GregorianCalendar.AM_PM);
        return i == 0 ? "AM" : "PM";
    }

    private static int compareToDate(String date1, String date2) {
        return date1.compareTo(date2);
    }

    private static int compareToDateString(String date1, String date2) {
        int i = 0;
        try {
            long ldate1 = formatterYMDHMS.parse(date1).getTime();
            long ldate2 = formatterYMDHMS.parse(date2).getTime();
            if (ldate1 > ldate2) {
                i = 1;
            } else if (ldate1 == ldate2) {
                i = 0;
            } else {
                i = -1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static String[] getFiveDate() {
        String[] dates = new String[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String five = " 05:00:00";

        if (getDateAMPM().equals("AM") && compareToDateString(getStringDate(), getStringDateShort() + five) == -1) {
            dates[0] = getBeforeDay(calendar) + five;
            dates[1] = getStringDateShort() + five;
        } else {
            dates[0] = getStringDateShort() + five;
            dates[1] = getAfterDay(calendar) + five;
        }

        return dates;
    }

    public static String getFiveDate2() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String five = " 05:00:00";
        String reStr = "";
        if (getDateAMPM().equals("AM") && compareToDateString(getStringDate(), getStringDateShort() + five) == -1) {
            reStr = getBeforeDay(calendar);
        } else {
            reStr = getStringDateShort();
        }
        return reStr;
    }

    /**
     * Description:???????????????????????????yyyy-MM-dd HH:mm:ss????????????
     *
     * @author dbdu
     */
    public static String getStringNow() {
        Date now = new Date();
        return getStringDateTime(now);
    }

    /**
     * Description:?????????????????????yyyy-MM-dd HH:mm:ss????????????
     *
     * @param date ??????
     * @return java.lang.String
     * @author dbdu
     */
    public static String getStringDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return formatterYMDHMS.format(date);
    }

    /**
     * Description:????????????yyyy-MM-dd HH:mm????????????
     * ?????????????????????yyyy-MM-dd????????????
     *
     * @param date ??????
     * @return java.lang.String
     * @author dbdu
     */
    public static String getStringDate(Date date) {
        if (date == null) {
            return "";
        }
        return getDateString(date, 6);
    }

    /**
     * Description:?????????????????????HH:mm????????????
     *
     * @param date ??????
     * @return java.lang.String
     * @author dbdu
     */
    public static String getStringTime(Date date) {
        if (date == null) {
            return "";
        }
        return formatterHM.format(date);
    }

    /**
     * Description:????????????????????????????????????
     *
     * @param
     * @return Created at:18-4-25 ??????1:27
     */
    public static String getLastDayOfMonth(String dateStr) {
        SimpleDateFormat sfMonth = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = "";
        try {
            Date t = sfMonth.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(t);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, lastDay);
            lastDayOfMonth = sfDate.format(calendar.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lastDayOfMonth;
    }

    /**
     * Description:??????????????????????????????????????????
     *
     * @param
     * @return Created at:18-4-25 ??????11:09
     */
    public static String nextMonth(String dateStr) {
        SimpleDateFormat sfMonth = new SimpleDateFormat("yyyy-MM-dd");
        String nextMonth = "";
        try {
            Date t = sfMonth.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(t);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DATE, date);
            Date time = calendar.getTime();
            nextMonth = sfMonth.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextMonth;
    }

    /**
     * Description:??????????????????
     *
     * @param
     * @return Created at:18-4-25 ??????1:13
     */
    public static Integer nowMonth(String dateStr) {
        SimpleDateFormat sfMonth = new SimpleDateFormat("yyyy-MM");
        Integer nextMonth = null;
        try {
            Date t = sfMonth.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(t);
            int year = calendar.get(Calendar.YEAR);
            nextMonth = calendar.get(Calendar.MONTH) + 1;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextMonth;
    }

    /**
     * Description:????????????????????????????????????
     *
     * @param ???????????????
     * @return Created at:18-4-25 ??????2:12
     */
    public static Integer nowYear(String dateStr) {
        SimpleDateFormat sfMonth = new SimpleDateFormat("yyyy-MM");
        Integer year = null;
        try {
            Date t = sfMonth.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(t);
            year = calendar.get(Calendar.YEAR);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return year;


    }

    /**
     * Description:??????????????????????????????????????????
     *
     * @param
     * @return Created at:18-4-26 ??????4:18
     */
    public static boolean isSameMonth(Date startDate, Date endDate) {
        Calendar sCal = Calendar.getInstance();
        sCal.setTime(startDate);
        Calendar eCal = Calendar.getInstance();
        eCal.setTime(endDate);
        boolean isSameYear = sCal.get(Calendar.YEAR) == eCal.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && sCal.get(Calendar.MONTH) == eCal.get(Calendar.MONTH);
        boolean isSame = isSameYear && isSameMonth;
        return isSame;

    }

    /**
     * Description:??????????????????????????????????????????Long???????????? 2018-10-12 00:00:00 000 ?????????Long???
     *
     * @return java.lang.Long
     * @author dbdu
     */
    public static Long getTodayStart() {
        return DateTimeUtil.getTimeMSFromString(DateTimeUtil.getCurrentDate() + " 00:00:00");
    }

    /**
     * Description:??????????????????????????????????????????Long???????????? 2018-10-12 23:59:59 999 ?????????Long???
     *
     * @return java.lang.Long
     * @author dbdu
     */
    public static Long getTodayEnd() {
        return DateTimeUtil.getTodayStart() + ONE_DAY_SECOND * 1000 - 1;
    }

    public static void main(String[] args) throws ParseException {
        String fullT = "2018-04-30T08:30:30";
        String full1 = "2018-04-30 08:30:30";
        String full2 = "2018/04/30 08:30:30";
        String full3 = "2018_04_30 08:30";
        String full4 = "20180430 083030";
        System.out.println(full1 + "====getDateFromString ===" + getDateFromString(full1));
        System.out.println(full2 + "====getDateFromString ===" + getDateFromString(full2));
        System.out.println(full3 + "====getDateFromString ===" + getDateFromString(full3));
        System.out.println(full4 + "====getDateFromString ===" + getDateFromString(full4));
        System.out.println(fullT + "getLocalDateTimeFromString ===" + getLocalDateTimeFromString(fullT));
        System.out.println(full1 + "====getLocalDateTimeFromString ===" + getLocalDateTimeFromString(full1));
        System.out.println(full2 + "====getLocalDateTimeFromString ===" + getLocalDateTimeFromString(full2));
        System.out.println(full3 + "====getLocalDateTimeFromString ===" + getLocalDateTimeFromString(full3));
        System.out.println(full4 + "====getLocalDateTimeFromString ===" + getLocalDateTimeFromString(full4));
        System.out.println("getLocalTimeFromString ===" + getLocalDateTimeFromString(full4).toLocalTime());
        System.out.println("getLocalDateFromString ===" + getLocalDateTimeFromString(full4).toLocalDate());
        String date1 = "2018-04-30";
        String date2 = "2018/04/30";
        String date3 = "2018_04_30";
        String date4 = "20180430";
        System.out.println("getLocalDateFromString ===" + getLocalDateFromString(date1));
        System.out.println("getLocalDateFromString ===" + getLocalDateFromString(date2));
        System.out.println("getLocalDateFromString ===" + getLocalDateFromString(date3));
        System.out.println("getLocalDateFromString ===" + getLocalDateFromString(date4));
        String time1 = "10:25:22";
        String time2 = "10:26";
        System.out.println("getLocalTimeFromString ==="+getLocalTimeFromString(time1));
        System.out.println("getLocalTimeFromString ==="+getLocalTimeFromString(time2));
    }
}
