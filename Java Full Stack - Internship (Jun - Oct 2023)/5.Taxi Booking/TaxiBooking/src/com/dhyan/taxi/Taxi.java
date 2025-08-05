package com.dhyan.taxi;
import java.util.*;
public class Taxi
{
	private String taxiNo;
	private int seats;
	private List<Trip> tripList=new ArrayList<Trip>();
	private Place taxiplace;
	
	/**
	 * Constructor for setting taxiNo and place
	 * @param taxiNo
	 * @param place
	 */
	public Taxi(String taxiNo,Place place)
	{
		this.taxiNo = taxiNo;
		this.taxiplace = place;
	}
	
	/**
	 * Add Trip Details
	 * @param tripList
	 */
	public void addTrip(Trip tripList)
	{
		this.tripList.add(tripList);
	}
	
	/**
	 * Setting seat size
	 * @param seats
	 */
	public void setSeats(int seats)
	{
		this.seats=seats;
	}
	
	/**
	 * Getting Taxi number
	 * @return
	 */
	public String getTaxiNo() {
		return taxiNo;
	}
	
	/**
	 * Getting Seats
	 * @return
	 */
	public int getSeats() {
		return seats;
	}
	
	/**
	 * Getting Trip List
	 * @return
	 */
	public List<Trip> getTripList() {
		return tripList;
	}
	
	/**
	 * Get Place Details
	 * @return
	 */
	public Place getPlace() {
		return taxiplace;
	}		
	
	/**
	 * Setting Place
	 * @param taxiPlace
	 */
	public void setPlace(Place taxiPlace)
	{
		this.taxiplace=taxiPlace;
	}
	 
	@Override
	public String toString()
	{
		return ("Taxi Number: "+taxiNo+" Available Seats: "+seats+" Loaction: "+taxiplace);
	}
}