package com.dhyan.taxi;

import java.text.ParseException;

public class TaxiBooking {
	public static void main(String[] args) throws ParseException {
		
		TaxiManager taxiManager=new TaxiManager();
		TaxiBookingInitializer.initializePlace();		
		TaxiBookingInitializer.initializeTaxi();
		
		while (true) {
			
			taxiManager.displayPlace();
			taxiManager.displayTaxi();
			taxiManager.trip();
			System.out.println("Enter 0 to exit ");
			int end = IOUtility.getInteger();
			if (end == 0)
				break;
		}
		taxiManager.tripDetails();
	}
}
