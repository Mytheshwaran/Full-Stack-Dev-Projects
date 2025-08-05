package com.dhyan.taxi;
import java.util.*;
public class Place {
	private String name;
	private Map<Place,String> placeDetails=new HashMap<Place,String>();
	
	/**
	 * Constructor for setting name
	 * @param name
	 */
	public Place(String name)
	{
		this.name=name;
	}
	
	/**
	 * Getting name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	public Map<Place, String> getPlaceDetails() {
		return placeDetails;
	}

	/**
	 * Add Place details
	 * @param name
	 * @param time
	 */
	public void addPlaceDetails(Place name,String time)
	{
		placeDetails.put(name, time);
	}
	
	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}