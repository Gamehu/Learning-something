package com.elane.common.utils.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 时间日期处理类
 * 
 * @author chengbin
 *
 */
public class DateTimeUtil {

	public static final String DATE_PATTERN_YMD = "yyyy-MM-dd";
	public static final String DATE_PATTERN_YMDSTR = "yyyyMMdd";
	public static final String DATE_PATTERN_YMDSTRS = "yyyyMMddhhmmss";
	public static final String DATE_PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	@SuppressWarnings("serial")
	public static final Map<Integer, String> DATE_PATTERN_MAP = new HashMap<Integer, String>() {
		{
			put(8, DATE_PATTERN_YMDSTR);
			put(10, DATE_PATTERN_YMD);
			put(14, DATE_PATTERN_YMDSTRS);
			put(19, DATE_PATTERN_YMDHMS);
		}
	};

	/**
	 * 将字符串日期格式转换为sql日期格式
	 * 
	 * @param date
	 *            (yyyyMMdd)
	 * @return
	 */
	public static java.sql.Date format(final String date, final String pattern) {
		DateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		try {
			java.util.Date udate = sdf.parse(date);
			java.sql.Date sdate = new java.sql.Date(udate.getTime());
			return sdate;
		} catch (ParseException e) {
		}
		return null;
	}

	/**
     * 将字符串日期格式转换为java.util.Date日期格式
     * 
     * @param date
     *            (yyyyMMdd)
     * @return
     */
    public static java.util.Date formatUtilDate(final String date, final String pattern) {
        DateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            java.util.Date udate = sdf.parse(date);
            return udate;
        } catch (ParseException e) {
        }
        return null;
    }
    
	/**
	 * java.util.Date转换为java.sql.Timestamp
	 * 
	 * @param date
	 *            (yyyyMMdd)
	 * @return
	 */
	public static java.sql.Timestamp format(final java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * String转换为java.sql.Timestamp
	 * 
	 * @return
	 */
	public static java.sql.Timestamp format(final String datestr) {
		DateFormat df = new SimpleDateFormat(DATE_PATTERN_YMDHMS, Locale.CHINA);
		try {
			java.util.Date date = df.parse(datestr);
			return new java.sql.Timestamp(date.getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * String转换为java.sql.Timestamp "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static java.sql.Timestamp format2(final String datestr) {
		DateFormat df = new SimpleDateFormat(DATE_PATTERN_YMDSTRS, Locale.CHINA);
		try {
			java.util.Date date = df.parse(datestr);
			return new java.sql.Timestamp(date.getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	/***
	 * String转换为java.sql.Timestamp
	 * 
	 * @param datestr
	 *            字符串日期
	 * @param format
	 *            格式化字符转
	 * @return
	 */
	public static java.sql.Timestamp formatToTimestamp(final String datestr, final String format) {
		DateFormat df = new SimpleDateFormat(format, Locale.CHINA);
		try {
			java.util.Date date = df.parse(datestr);
			return new java.sql.Timestamp(date.getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	/***
	 * String转换为java.sql.Timestamp<br>
	 * 根据字符串日期长度自动判断，默认：DATE_PATTERN_YMDHMS<br>
	 * 
	 * @param datestr
	 *            字符串日期
	 * @return
	 */
	public static java.sql.Timestamp formatToTimestampAuto(final String datestr) {
		DateFormat df = null;
		String format = DATE_PATTERN_YMDHMS;
		try {
			if (StringUtils.isEmpty(datestr)) {
				return null;
			}
			format = DATE_PATTERN_MAP.get(datestr.length());
			df = new SimpleDateFormat(format, Locale.CHINA);
			java.util.Date date = df.parse(datestr);
			return new java.sql.Timestamp(date.getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * String转换为java.sql.Timestamp "yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static java.sql.Timestamp format3(final String datestr) {
	    SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN_YMD, Locale.CHINA);
		try {
			java.util.Date date = df.parse(datestr);
			return new java.sql.Timestamp(date.getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * jw 格式化String
	 * 
	 * @param param
	 * @return yyyyMMdd
	 */
	public static String formatStr(final java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YMDSTR, Locale.CHINA);
		String dt = sdf.format(date);
		return dt;
	}

    /**
     * jw 格式化String
     * 
     * @param param
     * @param formatStr
     * @return yyyyMMdd
     * 
     */
    public static String formatStr(final String date,String formFormat,String toFormat) {
        String dt="";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(toFormat, Locale.CHINA);
            java.util.Date datetime=formatUtilDate(date, formFormat);
            dt = sdf.format(datetime);
        } catch (Exception e) {
            dt="";
        }
        return dt;
    }

	/**
	 * 获取当前日期字符串格式
	 * 
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String getCurrentDateString(final String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		return sdf.format(new Date());
	}

	/**
	 * jw 返回n天后的日
	 * 
	 * @param date
	 * @param n
	 */
	public static String getNextDateByNumber(Date date, int n,String toFormat) {
	    if(StringUtils.isEmpty(toFormat)){
	        toFormat=DATE_PATTERN_YMDSTR;
	    }
		// 转换为日期类
		SimpleDateFormat df = new SimpleDateFormat(toFormat, Locale.CHINA);
		Date newDate = new Date(date.getTime() + n * 24 * 60 * 60 * 1000);
		// 再转回String
		String okDate = df.format(newDate);
		return okDate;
	}

	/**
	 * Timestamp格式转化为String格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String timestampToString(final Timestamp ts) {
		String tsStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YMDHMS, Locale.CHINA);
			tsStr = sdf.format(ts);
		} catch (Exception e) {
		}
		return tsStr;
	}
	public static Date stringToDate(final String date,String formFormat,String toFormat){
	    Date dt=new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(toFormat, Locale.CHINA);
            dt=formatUtilDate(date, formFormat);
        } catch (Exception e) {
            dt=new Date();
        }
        return dt;
	}
	/**
	 * date转换为Timestamp
	 * @param date
	 * @param formateStr
	 * @return
	 */
	public static Timestamp dateToTimestamp(final Date date){
        return new Timestamp(date.getTime());
        
	}
}
