package com.dhyan.taxi;
import java.util.*;
public class Trip {
	private String startTime;
	private Place startPlace;
	private String endTime;
	private Place endPlace;
	private int seat;
	private Taxi taxi;
	private List<Passenger> passengerList=new ArrayList<Passenger>();
	
	/**
	 * Constructor to set start time,end time,start place,end place,seats,taxi and passengerList
	 * @param startTime
	 * @param startPlace
	 * @param endTime
	 * @param endPlace
	 * @param seat
	 * @param taxi
	 * @param passengerList
	 */
	public Trip(String startTime, Place startPlace, String endTime, Place endPlace, int seat, Taxi taxi,List<Passenger> passengerList)
	{
		this.startTime = startTime;
		this.startPlace = startPlace;
		this.endTime = endTime;
		this.endPlace = endPlace;
		this.seat = seat;
		this.taxi = taxi;
		this.passengerList = passengerList;
	}
	
	/**
	 * Get start time
	 * @return
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/**
	 * Get start place
	 * @return
	 */
	public Place getStartPlace() {
		return startPlace;
	}
	
	/**
	 * Get end time
	 * @return
	 */
	public String getEndTime() {
		return endTime;
	}
	
	/**
	 * Get end place
	 * @return
	 */
	public Place getEndPlace() {
		return endPlace;
	}
	
	/**
	 * Get passenger list
	 * @return
	 */
	public List<Passenger> getPassengerList() {
		return passengerList;
	}
	
	@Override
	public String toString()
	{
		return ("Taxi Number: "+taxi.getTaxiNo()+"\nStarting Place: "+startPlace+" \nPickup Time: "+startTime+" \nDestination: "+endPlace+" \nDrop Time: "+endTime+" \nReaming Seats: "+seat);
	}
}