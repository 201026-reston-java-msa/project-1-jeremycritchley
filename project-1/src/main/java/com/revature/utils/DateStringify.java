package com.revature.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringify {
	
	public static String stringifyNow() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
	    return formatter.format(date);
	}
	
}
