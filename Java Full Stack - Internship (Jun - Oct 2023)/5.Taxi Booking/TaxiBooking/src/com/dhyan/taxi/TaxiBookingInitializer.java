package com.dhyan.taxi;

import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class TaxiBookingInitializer {
	
	/**
	 * Initialize Place
	 */
	public static void initializePlace() {
		List<Place> placeList=new ArrayList<Place>();
		String[] places = new String[] { "Guindy", "Egmore", "Chromepet", "Kodambakkam", "Central", "Thambaram" };
		String[] tripTime = new String[] { "15", "20", "35", "40", "50", "15" };
		for (int i = 0; i < places.length; i++) {
			Place place = new Place(places[i]);
			placeList.add(place);
		}
		int placeIndex = 0;
		for (Place place : placeList) {
			while (placeIndex < places.length) {
				if (!placeList.get(placeIndex).getName().equals(place.getName())) {
					place.addPlaceDetails(placeList.get(placeIndex),randomTime(tripTime));
				}
				placeIndex++;
			}
			if (placeIndex == places.length)
				placeIndex = 0;
		}
		TaxiManager.placeList.addAll(placeList);
	}
	
	/**
	 * Initialize taxi
	 */
	public static void initializeTaxi() {
		List<Taxi> taxiList=new ArrayList<Taxi>();
		String[] places = new String[] { "Guindy", "Egmore", "Chromepet", "Kodambakkam", "Central", "Thambaram" };
		for (int i = 0; i < places.length; i++) {
			Taxi taxi = new Taxi("TN 000" + (i + 1),TaxiManager.placeList.get(i));
			taxiList.add(taxi);
		}
		initializeTaxiSeats(taxiList);
		TaxiManager.taxiList.addAll(taxiList);
	}
	
	/**
	 * Initialize Passenger
	 * @return
	 */
	public static Passenger initializePassenger() {
		System.out.print("Enter Name: ");
		String name = IOUtility.getString();
		System.out.println("Select Gender");
		System.out.println("1.Male");
		System.out.println("2.Female");
		System.out.println("3.Others");
		int opt = IOUtility.getInteger();
		String gender = " ";
		switch (opt) {
		case 1:
			gender = "Male";
			break;
		case 2:
			gender = "Female";
			break;
		case 3:
			gender = "Others";
			break;
		}
		Passenger passenger = new Passenger(name, gender);
		return passenger;
	}
	
	/**
	 * Initialize Taxi Seats
	 * @param taxiList
	 */
	public static void initializeTaxiSeats(List<Taxi> taxiList) {
		for (Taxi taxi : taxiList)
			taxi.setSeats(3);
	}
	
	/**
	 * Generate Random time
	 * @param time
	 * @return
	 */
	public static String randomTime(String[] time) {
		int min = 0;
		int max = time.length;
		int index = ThreadLocalRandom.current().nextInt(min, max);
		return time[index];
	}
}
