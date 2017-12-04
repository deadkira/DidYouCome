package edu.scut.software.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Helper {
	public static long previous = new Date().getTime();

	public static Date pareseString(String pattern, String source) {
		try {
			return new SimpleDateFormat(pattern).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatDate(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date combineDateWithTime(Date date, Date time) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		Calendar timeCal = Calendar.getInstance();
		timeCal.setTime(time);
		Calendar combinedCal = Calendar.getInstance();
		combinedCal.set(dateCal.get(Calendar.YEAR), dateCal.get(Calendar.MONTH), dateCal.get(Calendar.DATE),
				timeCal.get(Calendar.HOUR_OF_DAY), timeCal.get(Calendar.MINUTE), timeCal.get(Calendar.SECOND));
		combinedCal.set(Calendar.MILLISECOND, 0);
		return combinedCal.getTime();
	}

	public static synchronized String generateToken(String id) {
		try {
			long current = System.currentTimeMillis();

			if (current == previous) {
				current++;
			}
			previous = current;
			byte[] now = new Long(current).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(id.getBytes());
			md.update(now);

			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);

		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}

		return sb.toString();
	}

	public static Map<String, Object> toMap(String s, Object o) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(s, o);
		return map;
	}
}
