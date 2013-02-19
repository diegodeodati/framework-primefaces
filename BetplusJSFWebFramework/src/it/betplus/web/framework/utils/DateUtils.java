package it.betplus.web.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {
	
	// Get local time zone date from pattern
	public static Date getLocalTimeZoneDate(Date date, String pattern) throws Exception{
		
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat(pattern);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Local time zone   
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat(pattern);

		// Time in GMT
		return dateFormatLocal.parse( dateFormatGmt.format(date) );
		
	}
	
	// Get date by specified time zone and pattern
	public static String getDateByTimeZone(String timeZone, String date, String pattern) throws Exception {
		
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat(pattern);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone(timeZone));

		Date releaseDate = dateFormatGmt.parse(date);
	
		SimpleDateFormat returnFormat = new SimpleDateFormat(pattern);
		
		return returnFormat.format(releaseDate);
		
	}
	
	// Change date time format
	public static String changeDateTimeFormat(String dateFrom, String toFormat) throws Exception {
		
		SimpleDateFormat dateDbFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date newDate = dateDbFormat.parse(dateFrom);
		
		SimpleDateFormat outFormat = new SimpleDateFormat(toFormat);
		
		return outFormat.format(newDate);
		
	}
	
	// Convert String to date by format and timezone
	public static Date stringToDate(String dateStr, String format, TimeZone tz) {
    	Date date = null;
    	
    	if (dateStr != null && format != null) {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		if (tz != null) {
    			sdf.setTimeZone(tz);
    		}    		
    		
    		try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				System.err.printf("Cannot parse date %s according to format pattern %s\n", dateStr, format);
				e.printStackTrace();
    		}
    	}
    	
    	return date;
    }
    
	// Convert String to date by format
    public static Date stringToDate(String dateStr, String format) {
    	return stringToDate(dateStr, format, null);
    }
    
    // Convert date to String by format and timezone
    public static String dateToString(Date date, String format, TimeZone tz) {
    	String dateStr = null;
    	
    	if (date != null && format != null) {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		if (tz != null) {
    			sdf.setTimeZone(tz);
    		}
    		dateStr = sdf.format(date);			
    	}
    	
    	return dateStr;
    }
    
    // Convert date to String by format 
    public static String dateToString(Date date, String format) {
    	return dateToString(date, format, null);
    }
    
    // Get current date
    public static Date getCurrentDate() {	
    	Calendar calendar = Calendar.getInstance();
    	return calendar.getTime();
    }
    
    // Format date to string by format
    public static String formatDateToString(Date date, String toFormat) throws Exception {
		
		SimpleDateFormat outFormat = new SimpleDateFormat(toFormat);	
		return outFormat.format(date);	
	}
    
	// Return day of a date
	public static int day(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
	
	// Return week of a date
	public static int week(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
	
	// Return year of a date
	public static int year(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
	
	// Return hour of a date
	public static int hour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }
	
	// Return minutes of a date
	public static int minute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }
	
	// Return second of a date
	public static int second(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }

	// Return mnth of a date
	public static int month(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH)+1;
    }
	
	// Add minutes to a date
    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
        
    // Add days to date
    public static Date addDays(Date date, int days) {
    	
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
        
    } 
    
    // Add year to date
    public static Date addYears(Date date, int years) {
    	
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
        
    } 
    
    // Return diff in days
    public static int dateDiff(Date dateMax, Date dateMin){
    	
    	long millisecond = dateMax.getTime()-dateMin.getTime();
    	int days = (int) millisecond / (24 * 60 * 60 * 1000);
    	return days;
    	
    }
    
    public static String dateDiffDetails(Date dateMax, Date dateMin){
    	
    	long millisecond = dateMax.getTime()-dateMin.getTime();
    	int hour = (int) millisecond / (60 * 60 * 1000);
    	int minute =  (int) millisecond / (60 * 1000);	
    	int second = (int) millisecond / 1000;	
    	
    	return hour+"-"+minute%60+"-"+second%60;
    	
    }    
    
    // Create Date from params
    public static Date createDate(int year, int month, int day, int hour, int mins, int seconds, int millis) {
    	
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, mins);
        cal.set(Calendar.SECOND, seconds);
        cal.set(Calendar.MILLISECOND, millis);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1); 
        cal.set(Calendar.DAY_OF_MONTH, day);
        
        return cal.getTime();
        
    } 
    
    // Check if the dates is equals
    public static boolean isDateEquals(Date date1, Date date2) {
    	
		boolean ConfrYear = false;
		boolean ConfrMonth = false;
		boolean ConfrDay = false;
		
		if(date1!=null && date2!=null){
		  	ConfrYear = year(date1)==year(date2);
		  	ConfrMonth = month(date1)==month(date2);
		  	ConfrDay = day(date1)==day(date2);		  	
		  	return ConfrYear && ConfrMonth && ConfrDay;
		}	 
		
		else 
			return true;
    	
    } 
    
    // return true if date1 is after date2
    public static boolean isDateAfter(Date date1, Date date2) {
    	return date1.after(date2);
    } 
    
    // return short value of the current semester
    public static Short sixmonth(int mese){
		if (mese<=6)
			return 1;
		else return 2;
	}
    
    // return int value of the current decade of the month
    public static int tenDays(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        if (giorno==10)
        	return 1;
        if (giorno==20)
        	return 2;
        if (giorno==31 || giorno==30)
        	return 3;
        
        return (short) ((giorno / 10)+1);
    }
	
    // return int value of the current fifteens of the month
	public static Short fifteenDays(Date date){
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        if (giorno==15)
        	return 1;
        if (giorno==30 || giorno==31)
        	return 2;
        
        return (short) ((cal.get(Calendar.DAY_OF_MONTH) / 15)+1);
	}
    
	public static Date calcPreviousDay(Date data){				
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_YEAR, -1);		
		Date ieri = cal.getTime();	
		return ieri;
	}
	
	
	public static Date calcPreviousWeek(Date data){				
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_YEAR, -7);		
		Date dat = cal.getTime();	
		return dat;
	}
	
	public static Date calcPrevious7gg(Date data){				
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_YEAR, -7);		
		Date dat = cal.getTime();	
		return dat;
	}
	
	public static Date calcPreviousMonth(Date data){				
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_YEAR, -30);		
		Date ieri = cal.getTime();	
		return ieri;
	}
	
	public static Date generateLastSecondDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
	
}
