
package com.can.store.androidbcc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;

import slim3.jackpot.controller.Const;
import slim3.jackpot.controller.Const.Country;
import slim3.jackpot.controller.dto.MonthDto;
import slim3.jackpot.exception.MyException;

/**
 * 日付系のUtilです。 Dateを文字列操作、文字列返却する際には、引数で渡されたタイムゾーンに変換されます。<br/>
 * 引数がない場合は、日本時間として、返却されます。<br/>
 *
 * @author kitazawa.takuya
 *
 */
public class DateUtil {
	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(DateUtil.class.getName());

	// 期限なし(9999年を入れて実質期限なしとしている)
	public static final Date MAX_DATE;

	/**
	 * 日本のLocaleとTimezoneで初期化したカレンダークラスを返すクラス （統一されていなかったためひとまず作成。また折を見て既存のソースも直す）
	 *
	 * @return
	 */
	public static Calendar getNewCalendarInstance() {
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		Locale locale = Locale.JAPAN;
		Calendar calendar = Calendar.getInstance(timezone, locale);
		calendar.setTimeZone(timezone);
		return calendar;
	}

	static {
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		Locale locale = Locale.JAPAN;
		Calendar cal = Calendar.getInstance(timezone, locale);
		cal.set(9999, 11, 31, 23, 59, 59);
		MAX_DATE = cal.getTime();
	}

	/**
	 * 現在時刻を取得します。
	 *
	 * @return
	 */
	public static Date getDate() {
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		Locale locale = Locale.JAPAN;
		Calendar calendar = Calendar.getInstance(timezone, locale);
		calendar.setTimeZone(timezone);
		// 表示
		return calendar.getTime();
	}

	public static Date getDate(Calendar calendar) {
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		calendar.setTimeZone(timezone);
		// 表示
		return calendar.getTime();

	}

	/**
	 * DateをPatternにフォーマットします。
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if(date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.JAPAN);
		formatter.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
		return formatter.format(date);
	}

	/**
	 * DateをPatternにフォーマットします。 タイムゾーン指定版
	 *
	 * @param date
	 * @param pattern
	 * @param country
	 * @return
	 */
	public static String formatDate(Date date, String pattern, Country country) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setTimeZone(TimeZone.getTimeZone(country.getTimeZone()));
		return formatter.format(date);
	}

	/**
	 * DateをPatternにフォーマットします。 タイムゾーン指定版
	 *
	 * @param date
	 * @param pattern
	 * @param country
	 * @return
	 */
	public static String formatDate(Date date, String pattern, String timeZone) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(date);
	}

	/**
	 * StringをDateに変換します。
	 *
	 * @param str
	 * @param fmt
	 * @return
	 */
	public static Date parseDate(String str, String fmt) {
		try {
			if(str == null) {
				return null;
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt, Locale.JAPAN);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
			return new SimpleDateFormat(fmt).parse(str);
		} catch (ParseException e) {
			throw new MyException(e);
		}
	}

	/**
	 * StringをDateに変換します。
	 *
	 * @param str
	 * @param fmt
	 * @param timeZone
	 * @return
	 */
	public static Date parseDate(String str, String fmt, String timeZone) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
			return simpleDateFormat.parse(str);
		} catch (ParseException e) {
			throw new MyException(e);
		}
	}

	/**
	 * 受け取ったタイムゾーンに合わせた時刻をStringで返却します。
	 *
	 * @param str
	 * @param fmt
	 * @param country
	 * @return
	 */
	public static Date parseDate(String str, String fmt, Country country) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(fmt, country.getLocale());
			date = formatter.parse(str);
		} catch (ParseException e) {
			throw new MyException(e);
		}
		return date;
	}

	/**
	 * 日付をString型で返します。
	 *
	 * @return
	 */
	public static String getDateString() {
		return getDateString("yyyy/MM/dd HH:mm");
	}

	/**
	 * 日付をString型で返します。
	 *
	 * @param fmt
	 *            フォーマット
	 * @param target
	 *            経過日数
	 * @return
	 */
	public static String getDateString(String fmt, int target) {
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		Locale locale = Locale.JAPAN;
		Calendar calendar = Calendar.getInstance(timezone, locale);
		calendar.setTimeZone(timezone);
		calendar.add(Calendar.DAY_OF_MONTH, target);
		// 表示
		format.setTimeZone(timezone);
		String strDate = format.format(calendar.getTime());
		// 整形して表示
		return strDate;
	}

	/**
	 * 日付をString型で返します。
	 *
	 * @param fmt
	 *            フォーマット
	 * @return
	 */
	public static String getDateString(String fmt) {
		return formatDate(getDate(), fmt);
	}

	/**
	 * 日付をString型で返します。
	 *
	 * @param fmt
	 *            フォーマット
	 * @return
	 */
	public static String getDateString(String fmt, Country country) {
		return formatDate(getDate(), fmt, country);
	}

	/**
	 * 経過時間をミリ秒で返します。
	 *
	 * @param startTime
	 * @return
	 */
	public static long getProgressTime(Date startTime) {
		return getDate().getTime() - startTime.getTime();
	}

	/**
	 * dateが現在から何日経過しているかを返します。
	 *
	 * @param date
	 * @return
	 */
	public static int countdown(Date date) {
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		simpleDateFormat.setTimeZone(timezone);

		String fmtDate = simpleDateFormat.format(date);
		String fmtNow = simpleDateFormat.format(getDate());

		try {
			long time = simpleDateFormat.parse(fmtDate).getTime();
			long now = simpleDateFormat.parse(fmtNow).getTime();

			long day = (time - now) / (24 * 60 * 60 * 1000);

			return (int) day;
		} catch (ParseException e) {
			throw new MyException(e);
		}
	}

	/**
	 * 現在日付から引数で指定された日数を引いた月の開始日、終了日を取得する。
	 *
	 * @param calenderEnum
	 * @param i
	 * @return
	 */
	public static MonthDto getMonthPair(CalenderEnum calenderEnum, int i) {
		String fmt = "yyyy/MM/dd";
		MonthDto monthDto = new MonthDto();

		SimpleDateFormat format = new SimpleDateFormat(fmt);
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		Locale locale = Locale.JAPAN;
		Calendar calendar = Calendar.getInstance(timezone, locale);

		if(calenderEnum != null && i != 0) {
			if(calenderEnum.equals(CalenderEnum.YEAR)) {
				calendar.add(Calendar.YEAR, i);
			} else if(calenderEnum.equals(CalenderEnum.DAY_OF_MONTH)) {
				calendar.add(Calendar.MONTH, i);
			} else if(calenderEnum.equals(CalenderEnum.DAY)) {
				calendar.add(Calendar.DATE, i);
			}
		}

		calendar.setTimeZone(timezone);
		format.setTimeZone(timezone);

		// 月初を取得
		int days = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, days);
		String startDate = format.format(calendar.getTime());
		monthDto.startDate = startDate + " 00:00";

		// 月末を取得
		days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, days);
		String endDate = format.format(calendar.getTime());
		monthDto.endDate = endDate + " 23:59";

		// 整形して表示
		return monthDto;

	}

	/**
	 * 現在日付のカレンダーの月初の日付を返却します。
	 *
	 * @param calenderEnum
	 * @param i
	 * @return
	 */
	public static Date getMonthStartDate(CalenderEnum calenderEnum, int i) {
		String fmt = "yyyy/MM/dd";
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		Locale locale = Locale.JAPAN;
		Calendar calendar = Calendar.getInstance(timezone, locale);

		if(calenderEnum != null) {
			if(calenderEnum.equals(CalenderEnum.YEAR)) {
				calendar.add(Calendar.YEAR, i);
			} else if(calenderEnum.equals(CalenderEnum.DAY_OF_MONTH)) {
				calendar.add(Calendar.MONTH, i);
			} else if(calenderEnum.equals(CalenderEnum.DAY)) {
				calendar.add(Calendar.DATE, i);
			}
		}

		calendar.setTimeZone(timezone);
		format.setTimeZone(timezone);

		// 月初を取得
		int days = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, days);
		String startDate = format.format(calendar.getTime());
		startDate = startDate + " 00:00:00";
		return parseDate(startDate, "yyyy/MM/dd hh:mm:ss");

	}

	/**
	 * 現在日付のカレンダーの月初の日付を返却します。
	 *
	 * @param calenderEnum
	 * @param i
	 * @return
	 */
	public static Date getMonthStartDate(Date date, boolean isJst) {
		Calendar cal;
		if(isJst) {
			TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
			Locale locale = Locale.JAPAN;
			cal = Calendar.getInstance(timezone, locale);
		} else {
			cal = Calendar.getInstance();
		}

		cal.setTime(date);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static Date getMonthStartDate(Date date) {
		return getMonthStartDate(date, true);
	}

	/**
	 * 現在日付のカレンダーの月末日を返却します。
	 *
	 * @param calenderEnum
	 * @param i
	 * @return
	 */
	public static Date getMonthEndDate(CalenderEnum calenderEnum, int i) {
		String fmt = "yyyy/MM/dd";
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		Locale locale = Locale.JAPAN;
		Calendar calendar = Calendar.getInstance(timezone, locale);

		if(calenderEnum != null) {
			if(calenderEnum.equals(CalenderEnum.YEAR)) {
				calendar.add(Calendar.YEAR, i);
			} else if(calenderEnum.equals(CalenderEnum.DAY_OF_MONTH)) {
				calendar.add(Calendar.MONTH, i);
			} else if(calenderEnum.equals(CalenderEnum.DAY)) {
				calendar.add(Calendar.DATE, i);
			}
		}

		calendar.setTimeZone(timezone);
		format.setTimeZone(timezone);

		// 月末を取得
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, days);
		String endDate = format.format(calendar.getTime());
		endDate = endDate + " 23:59:59";
		return parseDate(endDate, "yyyy/MM/dd hh:mm:ss");
	}

	/**
	 * 現在日付のカレンダーの月末日を返却します。
	 *
	 * @param calenderEnum
	 * @param i
	 * @return
	 */
	public static Date getMonthEndDate(Date date, boolean isJst) {
		Calendar cal;
		if(isJst) {
			TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
			Locale locale = Locale.JAPAN;
			cal = Calendar.getInstance(timezone, locale);
		} else {
			cal = Calendar.getInstance();
		}

		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// 1ヶ月進める
		cal.add(Calendar.MONTH, 1);

		// 1ms減らす（来月の最初-1ms＝今月の最後）
		cal.add(Calendar.MILLISECOND, -1);

		return cal.getTime();
	}

	public static Date getMonthEndDate(Date date) {
		return getMonthEndDate(date, true);
	}

	/**
	 * 休日かどうかを判定します。
	 *
	 * @param yyyymmdd
	 * @return
	 */
	public static boolean isHoliday(String yyyymmdd) {
		for(String str : Const.holidays) {
			if(yyyymmdd.equals(str) || yyyymmdd.equals(str.replaceAll("/", "-"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 時間が0時0分0秒か判定します。
	 *
	 * @param date
	 *            日付
	 * @return 時間が0時0分0秒の場合true
	 */
	public static boolean isTimeTruncateDate(Date date) {
		if(date == null) {
			return false;
		}
		Date dateTruncate = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
		return date.equals(dateTruncate);
	}

	/**
	 * 現在の日付・時刻から指定の時間量を加算・減算した結果を返します。 年、月、日、時間、分、秒、ミリ秒の各時間フィールドに対し、 任意の時間量を設定できます。 たとえば、現在の日付時刻から 10 日前を計算する場合は以下となります。 Calendar cal = add(null,0,0,-10,0,0,0,0);
	 *
	 * 各時間フィールドの値がその範囲を超えた場合、次の大きい時間フィールドが 増分または減分されます。 たとえば、以下では1時間と5分進めることになります。 Calendar cal = add(null,0,0,0,0,65,0,0);
	 *
	 * 各時間フィールドに設定する数量が0の場合は、現在の値が設定されます。
	 *
	 * @param cal
	 *            日付時刻の指定があればセットする。 nullの場合、現在の日付時刻で新しいCalendarインスタンスを生成する。
	 * @param addYear
	 *            加算・減算する年数
	 * @param addMonth
	 *            加算・減算する月数
	 * @param addDate
	 *            加算・減算する日数
	 * @param addHour
	 *            加算・減算する時間
	 * @param addMinute
	 *            加算・減算する分
	 * @param addSecond
	 *            加算・減算する秒
	 * @param addMillisecond
	 *            加算・減算するミリ秒
	 * @return 計算後の Calendar インスタンス。
	 */
	public static Date add(int addYear, int addMonth, int addDate, int addHour, int addMinute, int addSecond, int addMillisecond) {

		TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");
		Locale locale = Locale.JAPAN;
		Calendar cal = Calendar.getInstance(timezone, locale);
		cal.add(Calendar.YEAR, addYear);
		cal.add(Calendar.MONTH, addMonth);
		cal.add(Calendar.DATE, addDate);
		cal.add(Calendar.HOUR_OF_DAY, addHour);
		cal.add(Calendar.MINUTE, addMinute);
		cal.add(Calendar.SECOND, addSecond);
		cal.add(Calendar.MILLISECOND, addMillisecond);
		cal.setTimeZone(timezone);
		return cal.getTime();
	}

	public static Date add(Date date, int addYear, int addMonth, int addDate, int addHour, int addMinute, int addSecond, int addMillisecond) {
		if(date == null) {
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, addYear);
		cal.add(Calendar.MONTH, addMonth);
		cal.add(Calendar.DATE, addDate);
		cal.add(Calendar.HOUR_OF_DAY, addHour);
		cal.add(Calendar.MINUTE, addMinute);
		cal.add(Calendar.SECOND, addSecond);
		cal.add(Calendar.MILLISECOND, addMillisecond);
		return cal.getTime();
	}

	/**
	 * 現在より前かどうかを返します。 現在より前ならtrue 現在と同じか後ならfalse
	 *
	 * @param date
	 * @return
	 */
	public static boolean isPast(Date date) {
		long now = getDate().getTime();
		if(now > date.getTime()) {
			return true;
		} else {
			return false;
		}
	}

    /**
     * dateがbaseより未来かどうかを返します。 未来ならtrue 同じか過去ならfalse
     *
     * @param date
     * @return
     */
    public static boolean isFuture(Date base, Date date) {
        if(base.getTime() < date.getTime()) {
            return true;
        } else {
            return false;
        }
    }
	/**
	 * dateがbaseより昔かどうかを返します。 過去ならtrue 同じか未来ならfalse
	 *
	 * @param date
	 * @return
	 */
	public static boolean isPast(Date base, Date date) {
		if(base.getTime() > date.getTime()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 祝日ならTrue、それ以外ならFalse
	 *
	 * @param date
	 * @return
	 */
	public static boolean isPublicHoliday(Date date) {
		return Holiday.isHoliday(date);
	}

	/**
	 * 土日祝日ならtrue、それ以外ならfalse
	 *
	 * @param date
	 * @return
	 */
	public static boolean isHoliday(Date date) {
		Calendar cal = getNewCalendarInstance();
		cal.setTime(date);

		int weekNum = cal.get(Calendar.DAY_OF_WEEK);
		boolean isSatOrSun = weekNum == Calendar.SUNDAY || weekNum == Calendar.SATURDAY ? true : false;

		// 土日もしくは祝日ならtrue
		return isSatOrSun || Holiday.isHoliday(date);
	}

	/**
	 * 土日祝日ならfalse、それ以外ならtrue
	 *
	 * @param date
	 * @return
	 */
	public static boolean isBusinessDay(Date date) {
		return !isHoliday(date);
	}

	// ================================
	// カレンダー
	public static enum CalenderEnum {
		YEAR("year"), DAY_OF_MONTH("month"), DAY("day");

		private String name;

		// コンストラクタ
		private CalenderEnum(String name) {
			this.setName(name);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

}