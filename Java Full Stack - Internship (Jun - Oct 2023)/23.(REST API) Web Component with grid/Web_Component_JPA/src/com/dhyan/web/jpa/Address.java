package com.dhyan.web.jpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	@Column(name="door_no")
	private int doorNo;
	
	@Column(name="street")
	private String street;
	
	@Column(name="district")
	private String district;
	
	@Column(name="state")
	private String state;

	/**
	 * @return the doorNo
	 */
	public int getDoorNo() {
		return doorNo;
	}

	/**
	 * @param doorNo the doorNo to set
	 */
	public void setDoorNo(int doorNo) {
		this.doorNo = doorNo;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return  this.doorNo + " " + this.street + " " + this.district + " " + this.state ;
	}
	
}
