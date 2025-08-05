package com.dhyan.taxi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxiManager {
	static List<Place> placeList = new ArrayList<Place>();
	static List<Taxi> taxiList = new ArrayList<Taxi>();
	static List<Passenger> passengerList = new ArrayList<Passenger>();

	/**
	 * Getting start place,end place and time to make trip
	 * @throws ParseException
	 */
	public void trip() throws ParseException {
		System.out.println("Enter the Pick up Location: ");
		Place startPlace = new Place(IOUtility.getString());
		System.out.println("Enter the Drop Location: ");
		Place endPlace = new Place(IOUtility.getString());
		System.out.println("Enter the Pick up Time:(HH:MM [AM/PM]) ");
		String startTime = IOUtility.getString();

		Date Time = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		Date currentTime = timeFormat.parse(timeFormat.format(Time));
		Date departureTime = timeFormat.parse(startTime);

		if (departureTime.after(currentTime) && (!startPlace.equals(endPlace))) {
			makeTrip(startPlace, startTime, endPlace);
		} else {
			System.out.println("XXX--  Enter Correct Information --XXX");
			trip();
		}

	}

	/**
	 * Make trip with given details by checking taxi availability
	 * @param startPlace
	 * @param startTime
	 * @param endPlace
	 * @throws ParseException
	 */
	public static void makeTrip(Place startPlace, String startTime, Place endPlace) throws ParseException {
		int loopcount = 0;
		boolean isTaxiBooked = false;
		boolean isAvailable;
		String endTime;
		int noOfPassenger;

		System.out.println("Enter Number of passengers:");
		noOfPassenger = IOUtility.getInteger();

		for (Taxi taxi : taxiList) {
			for (Place destinationPlace : taxi.getPlace().getPlaceDetails().keySet()) {
				if (taxi.getPlace().equals(startPlace) && destinationPlace.equals(endPlace)) {

					endTime = MapUtility.endTimePredictor(startTime, taxi, endPlace);

					isAvailable = checkTaxiAvailable(startTime, startPlace, endTime, taxi, endPlace);
					if (isAvailable && taxi.getSeats() >= noOfPassenger) {
						System.out.println("Taxi No: " + taxi.getTaxiNo() + " , Available seats: " + taxi.getSeats());
						bookTaxi(taxi, startTime, endTime, endPlace, startPlace, noOfPassenger);
					} else {
						alternativeTaxi(startTime, startPlace, endPlace, endTime, noOfPassenger);
					}
					isTaxiBooked = true;
					break;
				}
			}
			loopcount++;
			if (loopcount >= taxiList.size() && (!isTaxiBooked)) {
				endTime = "0";
				alternativeTaxi(startTime, startPlace, endPlace, endTime, noOfPassenger);
			}
		}
	}

	/**
	 * Check's for alternative taxi 
	 * @param startTime
	 * @param startPlace
	 * @param endPlace
	 * @param endTime
	 * @param noOfPassenger
	 * @throws ParseException
	 */
	public static void alternativeTaxi(String startTime, Place startPlace, Place endPlace, String endTime,
			int noOfPassenger) throws ParseException {

		boolean isTaxiBooked = false;
		Place newTaxiPlace;
		SimpleDateFormat timeFormat;
		Date initialPassengerTime;
		Calendar calendar1;
		String newPickupTime;
		boolean isAvailable;
		char option;

		Map<Place, Integer> taxiPlace = new HashMap<Place, Integer>();
		for (Place place : placeList) {
			if (place.getPlaceDetails().get(endPlace) != null) {
				taxiPlace.put(place, Integer.parseInt(place.getPlaceDetails().get(endPlace)));
			}
		}

		while (!isTaxiBooked) {
			// getting minimum time to reach destination
			newTaxiPlace = null;
			for (Place key : taxiPlace.keySet()) {
				if (newTaxiPlace == null || taxiPlace.get(key) < taxiPlace.get(newTaxiPlace)) {
					newTaxiPlace = key;
				}
			}

			timeFormat = new SimpleDateFormat("hh:mm aa");
			initialPassengerTime = timeFormat.parse(startTime);

			calendar1 = Calendar.getInstance();
			calendar1.setTime(initialPassengerTime);
			calendar1.add(Calendar.MINUTE, taxiPlace.remove(newTaxiPlace));
			newPickupTime = timeFormat.format(calendar1.getTime());

			// New taxi booking
			for (Taxi taxi : taxiList) {
				if (taxi.getPlace().equals(newTaxiPlace)) {
					if (endTime.equals("0")) {
						endTime = MapUtility.endTimePredictor(startTime, taxi, endPlace);
					}
					isAvailable = checkTaxiAvailable(newPickupTime, startPlace, endTime, taxi, endPlace);
					if (isAvailable) {
						System.out.println("Taxi Available At: " + newPickupTime);
						System.out.println("You want to book the taxi at the available time:(Y or N) ");
						isTaxiBooked = true;
						option = IOUtility.getString().charAt(0);
						if (option == 'Y') {
							bookTaxi(taxi, newPickupTime, endTime, endPlace, startPlace, noOfPassenger);
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * Check taxi availability
	 * @param initialTime
	 * @param startPlace
	 * @param endTime
	 * @param taxi
	 * @param endPlace
	 * @return
	 * @throws ParseException
	 */
	public static boolean checkTaxiAvailable(String initialTime, Place startPlace, String endTime, Taxi taxi,
			Place endPlace) throws ParseException {
		boolean isAvailable = true;
		String Time;
		SimpleDateFormat timeFormat;
		Date passengerDepartureTime;
		Date taxiReturnTime;
		String previousPickupTime;
		Date taxiPreviousStartTime;
		Date taxiPreviousEndTime;

		timeFormat = new SimpleDateFormat("hh:mm aa");
		passengerDepartureTime = timeFormat.parse(initialTime);

		for (Trip trip : taxi.getTripList()) {

			Time = trip.getEndTime();

			taxiReturnTime = timeFormat.parse(Time);

			previousPickupTime = trip.getStartTime();

			taxiPreviousStartTime = timeFormat.parse(previousPickupTime);
			if (passengerDepartureTime.after(taxiPreviousStartTime) && passengerDepartureTime.before(taxiReturnTime)) {
				isAvailable = false;
			} else {
				isAvailable = true;
			}
			if (isAvailable && (!passengerDepartureTime.equals(taxiPreviousStartTime))) {
				taxi.setSeats(3);
			}

		}
		if (isAvailable) {
			for (Trip trip : taxi.getTripList()) {

				Time = nextAvailableTaxiTime(taxi, endTime, endPlace);
				taxiReturnTime = timeFormat.parse(Time);

				Time = trip.getEndTime();
				taxiPreviousEndTime = timeFormat.parse(Time);

				previousPickupTime = trip.getStartTime();
				taxiPreviousStartTime = timeFormat.parse(previousPickupTime);
				if ( ( (passengerDepartureTime.before(taxiPreviousStartTime)
						&& taxiReturnTime.before(taxiPreviousStartTime))
						|| (passengerDepartureTime.after(taxiPreviousEndTime)
								&& taxiReturnTime.after(taxiPreviousEndTime)) )
						&& (!(passengerDepartureTime.equals(taxiPreviousStartTime)))) {
					isAvailable = true;
				} else {
					isAvailable = false;
					break;
				}
			}

		}
		return isAvailable;
	}

	/**
	 * Predict next available taxi time
	 * @param taxi
	 * @param dropTime
	 * @param endPlace
	 * @return
	 * @throws ParseException
	 */
	public static String nextAvailableTaxiTime(Taxi taxi, String dropTime, Place endPlace) throws ParseException {
		SimpleDateFormat timeFormat;
		Date EndTime;
		Calendar calendar2;
		Date returnTime;

		timeFormat = new SimpleDateFormat("hh:mm aa");
		EndTime = timeFormat.parse(dropTime);

		calendar2 = Calendar.getInstance();
		calendar2.setTime(EndTime);
		calendar2.add(Calendar.MINUTE, Integer.parseInt(taxi.getPlace().getPlaceDetails().get(endPlace)));

		returnTime = calendar2.getTime();
		return timeFormat.format(returnTime);
	}

	/**
	 * Display available Taxis
	 */
	public void displayTaxi() {
		System.out.println("--Available Taxies--");
		for (Taxi taxi : taxiList)
			System.out.println("   " + taxi.getTaxiNo() + "   ");
	}

	/**
	 * Display available Places
	 */
	public void displayPlace() {
		System.out.println("---Destination Places---");
		for (Place place : placeList) {
			System.out.println("   " + place + "   ");
		}
	}

	/**
	 * Display Trip details
	 */
	public void tripDetails() {
		for (Passenger passenger : passengerList) {
			System.out.println("~~~~~~~~~~~~");
			System.out.println(passenger.toString());
		}
	}

	/**
	 * Book taxi
	 * @param taxi
	 * @param startTime
	 * @param endTime
	 * @param endPlace
	 * @param startPlace
	 * @param noOfPassenger
	 * @throws ParseException
	 */
	public static void bookTaxi(Taxi taxi, String startTime, String endTime, Place endPlace, Place startPlace,
			int noOfPassenger) throws ParseException {

		List<Passenger> newPassengers = new ArrayList<Passenger>();
		for (int i = 0; i < noOfPassenger; i++) {
			newPassengers.add(TaxiBookingInitializer.initializePassenger());
		}
		bookTrip(startTime, startPlace, endTime, endPlace, taxi, newPassengers);

	}

	/**
	 * Book trip
	 * @param startTime
	 * @param startPlace
	 * @param endTime
	 * @param endPlace
	 * @param taxi
	 * @param newPassengers
	 */
	public static void bookTrip(String startTime, Place startPlace, String endTime, Place endPlace, Taxi taxi,
			List<Passenger> newPassengers) {
		for (Passenger passenger : newPassengers) {

			taxi.setSeats(taxi.getSeats() - 1);
			Trip trip = new Trip(startTime, startPlace, endTime, endPlace, taxi.getSeats(), taxi, newPassengers);

			passenger.addTrip(trip);
			taxi.addTrip(trip);
			passengerList.add(passenger);

//			changeTaxiPlace(taxi, startPlace, endPlace);
		}

	}

	/**
	 * Change taxi place after a trip
	 * @param taxi
	 * @param startPlace
	 * @param endPlace
	 */
	public static void changeTaxiPlace(Taxi taxi, Place startPlace, Place endPlace) {
		String placeTime = taxi.getPlace().getPlaceDetails().remove(endPlace);
		taxi.getPlace().getPlaceDetails().put(startPlace, placeTime);

		Map<Place, String> newPlaceDetails = new HashMap<Place, String>();
		newPlaceDetails.putAll(taxi.getPlace().getPlaceDetails());

		taxi.setPlace(endPlace);
		taxi.getPlace().getPlaceDetails().putAll(newPlaceDetails);
		taxi.setSeats(3);
	}
}