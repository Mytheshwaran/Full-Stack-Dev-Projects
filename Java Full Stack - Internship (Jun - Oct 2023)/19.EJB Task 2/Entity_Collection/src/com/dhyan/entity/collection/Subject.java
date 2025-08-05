package com.dhyan.entity.collection;

import javax.persistence.Embeddable;

@Embeddable
public class Subject {
	private int subject_code;
	private String subject_name;
	/**
	 * @return the subject_code
	 */
	public int getSubject_code() {
		return subject_code;
	}
	/**
	 * @param subject_code the subject_code to set
	 */
	public void setSubject_code(int subject_code) {
		this.subject_code = subject_code;
	}
	/**
	 * @return the subject_name
	 */
	public String getSubject_name() {
		return subject_name;
	}
	/**
	 * @param subject_name the subject_name to set
	 */
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	

}
