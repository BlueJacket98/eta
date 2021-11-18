package com.capstone.eta.util.date;

import java.util.*;

public class DateAddOne {
    static Calendar calendar = new GregorianCalendar();
    
	static public Date dateAddOne(Date date) {
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		return date;
	}

    public static void main(String[] args) {
        
    }
}
