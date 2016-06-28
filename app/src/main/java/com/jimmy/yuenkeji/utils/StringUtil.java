package com.jimmy.yuenkeji.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.jimmy.yuenkeji.upa.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtil
 * @Description: TODO(该类的作用：验证工具类)
 * @author Fei
 * 
 */
public class StringUtil {
	/**
	 * 
	 * @param
	 * @return
	 * 手机号码的格式验证
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("[1][34578]\\d{9}");
		Matcher m = p.matcher(mobiles);
		return m.find();
	}

	/**
	 * 
	 * @param
	 * @return
	 * 邮箱的格式验证
	 */
	public static boolean getEmail(String line) {
		// Pattern p = Pattern
		// .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		// Matcher m = p.matcher(line);
		// return m.find();
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(line);
		return m.matches();
	}

	/**
	 * 验证身份证号码
	 * 
	 * @param idCard
	 *            居民身份证号码15位或18位，最后一位可能是数字或字母
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 
	 * @param
	 * @return不能用中文的汉字和符号!
	 * 账号的格式验证
	 */
	public static boolean isHaveChineseChar(String str) {
		if (str.length() == str.getBytes().length) {
			return true;
		}
		return false;
	}

	/**
	 * 获取本机手机号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

	/**
	 * 获取网络的状态信息
	 */
	public static String detectionMesh(Context context) {
		ConnectivityManager connectionManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取网络的状态信息，有下面三种方式 0手机网络1wifi

		NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
		if (null != networkInfo) {
			int aaaaString = networkInfo.getType();
			return aaaaString + "";
		} else {
			return "-1";
		}
	}

	/**
	 * 获取当前时间 yyyy年MM月dd日
	 * 
	 * @return
	 */
	public static String getData_yyyy_MM_dd() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 比较时间
	 * 
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static long compare_date(String DATE1, String DATE2) {
		long days = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			long diff = dt2.getTime() - dt1.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception exception) {
			exception.printStackTrace();
			return 7;
		}
		return days;
	}

	/**
	 * 获取当前时间 yyyy年MM月dd日HH:mm:ss
	 * 
	 * @return
	 */
	public static String getData_yyyy_MM_dd_HH_mm() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 获取当前时间 HH_mm
	 * 
	 * @return
	 */
	public static String getData_HH_mm() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		// int str = Integer.parseInt(formatter.format(curDate).toString());
		return formatter.format(curDate).toString();
	}

	private static final double EARTH_RADIUS = 6378137;

	private static GregorianCalendar calendar = new GregorianCalendar();

	/**
	 * 根据输入的毫秒数，获得日期字符串
	 * 
	 * @param millis
	 * @return
	 */

	public static String getDate(long millis) {

		calendar.setTimeInMillis(millis);

		return DateFormat.getDateInstance().format(calendar.getTime());

	}

	/**
	 * 获得当前时间的毫秒表示
	 * 
	 * @return
	 */

	public static long getNow() {

		GregorianCalendar now = new GregorianCalendar();

		return now.getTimeInMillis();

	}

	/**
	 * 获得明天日期
	 * 
	 * @return
	 */

	public static String getTomoData() {

		// 86400000为一天的毫秒数

		return getDate(getNow() + 86400000);

	}

	public static String getDateyyyy_MM_dd_HH_mm_ss(long millis) {
		try {
			calendar.setTimeInMillis(millis);
			Date date = (Date) calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String dateCreated = formatter.format(date);
			String _year;
			String _month;
			String _day;
			String _hour;
			if (-1 < dateCreated.indexOf(" ")) {
				_year = dateCreated.substring(0, 4);
				dateCreated = dateCreated.replace(" ", " ");
				if (16 < dateCreated.length()) {
					dateCreated = dateCreated.substring(0, 16);
					_day = dateCreated.substring(0, 10);
					_hour = dateCreated.substring(11, 16);
					_month = dateCreated.substring(5, 16);
					if (0 == compare_date(_day, getData())) {
						return _hour;
					} else if (1 == compare_date(dateCreated, getData())) {
						return context.getResources().getString(R.string.day) + _hour;
					} else if (2 == compare_date(dateCreated, getData())) {
						return _month;
					} else {
						if (_year.equals(getYYYY())) {
							return dateCreated.substring(5, 16);
						} else {
							return dateCreated;
						}
					}
				}
			}

			return dateCreated;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 提供“yyyy-mm-dd HH:mm:ss”形式的字符串到毫秒的转换
	 * 
	 * @param dateString
	 * @return
	 */

	public static long getMillis(String dateString) {

		// hourOfDay, minute, second)
		if (-1 < dateString.indexOf(" ")) {
			String[] date = dateString.split(" ")[0].split("-");
			String[] hour = dateString.split(" ")[1].split(":");
			return getMillis(date[0], date[1], date[2], hour[0], hour[1],
					hour[2]);
		}
		return 0;

	}

	/**
	 * 根据输入的年、月、日，时，分，转换成毫秒表示的时间
	 * 
	 * @param yearString
	 * @param monthString
	 * @param dayString
	 * @return
	 */

	public static long getMillis(String yearString, String monthString,

	String dayString, String hourString, String minuteString,
			String secondString) {
		int year = Integer.parseInt(yearString);

		int month = Integer.parseInt(monthString);

		int day = Integer.parseInt(dayString);

		int hour = Integer.parseInt(hourString);

		int minute = Integer.parseInt(minuteString);

		int second = Integer.parseInt(secondString);

		return getMillis(year, month, day, hour, minute, second);
	}

	/**
	 * 根据输入的年、月、日，时，分，秒转换成毫秒表示的时间
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */

	public static long getMillis(int year, int month, int day, int hour,
			int minute, int second) {
		month--;
		GregorianCalendar calendar = new GregorianCalendar(year, month, day,
				hour, minute, second);
		long InMillis = calendar.getTimeInMillis() + 8 * 3600 * 1000;
		return InMillis;
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static String getDataHHmm() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * @Title: GTToBeiJingTime
	 * @Description: TODO(这个方法的作用：格林时间转换成北京时间)
	 * @param @param data
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String GTToBeiJingTime(String data) {
		try {
			if (-1 < data.indexOf("T")) {
				data = data.replace("T", " ");
			}
			if (-1 < data.indexOf("Z")) {
				data = data.split("Z")[0];
			}
			return getDateyyyy_MM_dd_HH_mm_ss(getMillis(data));
		} catch (Exception e) {
		}
		return null;
	}

	private static Context context;

	/**
	 * @Title: getMessageTime
	 * @Description: TODO(这个方法的作用：获取格式化之后的消息时间)
	 * @param @param dateCreated
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getMessageTime(String dateCreated, Context context1) {
		context = context1;
		try {
			if (5 == dateCreated.length()) {
				return dateCreated;
			}
			if (-1 < dateCreated.indexOf("T")) {
				dateCreated = dateCreated.replace("T", " ");
			}
			if (-1 < dateCreated.indexOf("Z")) {
				dateCreated = dateCreated.split("Z")[0];
			}
			return getDateyyyy_MM_dd_HH_mm_ss(getMillis(dateCreated));
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static String getYYYY() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static String getData() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}
}
