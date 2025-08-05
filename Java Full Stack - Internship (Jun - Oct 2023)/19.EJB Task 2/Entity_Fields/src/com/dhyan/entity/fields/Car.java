package com.dhyan.entity.fields;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="Car_Details")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="car_no")
	private int no;
	
	@Column(name="car_company")
	private String company;
	
	@Enumerated(EnumType.STRING)
	private Color Color;
	
	@Temporal(TemporalType.DATE)
	@Column(name="registered_date")
	private Date date;
	
	@Transient
	private String carOwner;

	/**
	 * @return the car_no
	 */
	public int getNo() {
		return no;
	}

	/**
	 * @param car_no the car_no to set
	 */
	public void setNo(int no) {
		this.no = no;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return Color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		Color = color;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the carOwner
	 */
	public String getCarOwner() {
		return carOwner;
	}

	/**
	 * @param carOwner the carOwner to set
	 */
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	@Override
	public String toString() {
		return "Car [no=" + no + ", company=" + company + ", Color=" + Color + ", date=" + date + ", carOwner="
				+ carOwner + "]";
	}

}
