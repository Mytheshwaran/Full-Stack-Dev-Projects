package com.dhyan.web.jpa;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="emp_phone_number")
public class PhoneNumber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="phone_id")
	private int phoneId;
	
	@Column(name="phone_no",unique = true)
	private long phoneNo;

	/**
	 * @return the phoneId
	 */
	public int getPhoneId() {
		return phoneId;
	}

	/**
	 * @param phoneId the phoneId to set
	 */
	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}

	/**
	 * @return the phone_no
	 */
	public long getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phone_no the phone_no to set
	 */
	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {
		return phoneNo+"";
	}

	@Override
	public int hashCode() {
		return Objects.hash(phoneId, phoneNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneNumber other = (PhoneNumber) obj;
		return phoneId == other.phoneId && phoneNo == other.phoneNo;
	}
	
	
}
