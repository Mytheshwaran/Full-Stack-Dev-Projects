package com.dhyan.rest.common;

import java.io.Serializable;
import java.util.List;

public class JsonEmployee implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int age;
	private String birth;
	private String blood;
	private String designation;
	private List<String> phoneNumbers;
	private int doorNo;
	private String street;
	private String district;
	private String state;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the birth
	 */
	public String getBirth() {
		return birth;
	}
	/**
	 * @param birth the birth to set
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}
	/**
	 * @return the blood
	 */
	public String getBlood() {
		return blood;
	}
	/**
	 * @param blood the blood to set
	 */
	public void setBlood(String blood) {
		this.blood = blood;
	}
	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}
	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	/**
	 * @return the phoneNumbers
	 */
	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}
	/**
	 * @param phoneNumbers the phoneNumbers to set
	 */
	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
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
		return "JsonEmployee1 [id=" + id + ", name=" + name + ", age=" + age + ", birth=" + birth + ", blood=" + blood
				+ ", designation=" + designation + ", phoneNumbers=" + phoneNumbers + ", doorNo=" + doorNo + ", street="
				+ street + ", district=" + district + ", state=" + state + "]";
	}
	
}
