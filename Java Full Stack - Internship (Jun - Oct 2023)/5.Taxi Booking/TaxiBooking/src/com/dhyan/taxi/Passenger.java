package com.dhyan.taxi;

public class Passenger {
	private String name;
	private String gender;
	private Trip trip;
	
	/**
	 * Constructor for setting name and gender
	 * @param name
	 * @param gender
	 */
	public Passenger(String name, String gender) {
		this.name = name;
		this.gender = gender;
	}
	
	/**
	 * Add Trip details
	 * @param trip
	 */
	public void addTrip(Trip trip)
	{
		this.trip=trip;
	}
	
	/**
	 * Getting name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getting Gender
	 * @return
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * Getting Trip Details
	 * @return
	 */
	public Trip getTrip() {
		return trip;
	}	
	
	@Override
	public String toString()
	{
		return ("Name: "+name+" \nGender: "+gender+" \n"+trip);
	}
}