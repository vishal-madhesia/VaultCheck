/**
 * 
 */
package com.checkvault.utility;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author vmadhe01
 *
 */
public class DateAndTime {
	
	public static String dateDiff(Date StartDate, Date EndDate) {
		
		long duration  = EndDate.getTime() - StartDate.getTime();

		Integer diffInSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(duration)%60;
		Integer diffInMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(duration)%60;
		Integer diffInHours   = (int) TimeUnit.MILLISECONDS.toHours(duration);
		
		return ((diffInHours)+":"+diffInMinutes+":"+(diffInSeconds));
	}

}
