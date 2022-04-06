package com.capstone.eta.util.date;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class DateUtil {
    static Calendar calendar = new GregorianCalendar();
    
	/**
	 * Add n days to date, return new date
	 * @param date
	 * @param n
	 * @return
	 */
	static public Date dateAddN(Date date, int n) {
		calendar.setTime(date);
		calendar.add(Calendar.DATE, n);
		date = calendar.getTime();
		return date;
	}

	/**
	 * Count the diff between date1 and date2
	 * @param date1
	 * @param date2
	 * @return
	 */
	static public Integer dateDiffInDaysAbs(Date date1, Date date2) {
		long diff = Math.abs(date2.getTime() - date1.getTime());
		long numOfDays = diff / (24 * 60 * 60 * 1000);
		return (int)numOfDays;
	}

	static public Date getDateFromString(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date curDate = sdf.parse(date);
            return curDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

	static public List<Date> getAllDatesBetween(Date start, Date end) {
		List<Date> list = new ArrayList<>();
		long s = start.getTime();
		long e = end.getTime();
		
		Long oneDay = 1000 * 60 * 60 * 24l;

		while (s <= e) {
			start = new Date(s);
			list.add(start);
			s += oneDay;
		}
		return list;
	}

	/**
	 * Count the number of days betweeen date1 and date2
	 * @param date1
	 * @param date2
	 * @return
	 */
	static public Integer dateIntervalLengthInDaysAbs(Date date1, Date date2) {
		return DateUtil.dateDiffInDaysAbs(date1, date2) + 1;
	}

    public static void main(String[] args) {
        
    }
}
