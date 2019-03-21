package com.majiaxueyuan.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String formatYYYYMMDD(Date date) {
		Format format = new SimpleDateFormat("yyyyMMdd");
		String format2 = format.format(date);
		return format2;
	}
}
