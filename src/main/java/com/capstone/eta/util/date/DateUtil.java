package com.capstone.eta.util.date;

import java.util.*;

public class DateUtil {
    static Calendar calendar = new GregorianCalendar();
    
	static public Date dateAddN(Date date, int n) {
		calendar.setTime(date);
		calendar.add(Calendar.DATE, n);
		date = calendar.getTime();
		return date;
	}

	static public Integer dateDiffInDaysAbs(Date date1, Date date2) {
		long diff = Math.abs(date2.getTime() - date1.getTime());
		long numOfDays = diff / (24 * 60 * 60 * 1000);
		return (int)numOfDays;
	}

	static public Integer dateIntervalLengthInDaysAbs(Date date1, Date date2) {
		return DateUtil.dateDiffInDaysAbs(date1, date2) + 1;
	}

    public static void main(String[] args) {
        
    }
}
