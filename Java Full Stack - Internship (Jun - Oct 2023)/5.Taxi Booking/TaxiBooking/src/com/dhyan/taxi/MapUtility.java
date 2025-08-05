package com.dhyan.taxi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MapUtility {
	
	/**
	 * Predict taxi ending time
	 * @param pickUptime
	 * @param taxi
	 * @param endPlace
	 * @return
	 * @throws ParseException
	 */
	public static String endTimePredictor(String pickUptime, Taxi taxi, Place endPlace) throws ParseException {
		String myTime = pickUptime;
		SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm aa");
		Date time = timeformat.parse(myTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MINUTE, Integer.parseInt(taxi.getPlace().getPlaceDetails().get(endPlace)));
		String newTime = timeformat.format(cal.getTime());
		return newTime;
	}
}
