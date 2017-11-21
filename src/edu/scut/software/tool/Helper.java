package edu.scut.software.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {
	public static Date pareseString(String pattern, String source) {
		try {
			return new SimpleDateFormat(pattern).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date combineDateWithTime(Date date, Date time) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		Calendar timeCal = Calendar.getInstance();
		timeCal.setTime(time);
		Calendar combinedCal = Calendar.getInstance();
		combinedCal.set(dateCal.get(Calendar.YEAR), dateCal.get(Calendar.MONTH), dateCal.get(Calendar.DATE),
				timeCal.get(Calendar.HOUR_OF_DAY), timeCal.get(Calendar.MINUTE), timeCal.get(Calendar.SECOND));
		return combinedCal.getTime();
	}
}
