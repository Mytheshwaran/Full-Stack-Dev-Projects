package com.dhyan.employee.details;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="active_employee")
@NamedQuery(name="activeEmployee",query = "Select o from ActiveEmployee o")
@DiscriminatorValue(value = "active")
public class ActiveEmployee extends Employee{
	@Column(name="emp_salary")
	private int salary;
	
	@Column(name="emp_exp")
	private int experience;

	/**
	 * @return the salary
	 */
	public int getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(int salary) {
		this.salary = salary;
	}

	/**
	 * @return the experience
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(int experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "ActiveEmployee [Id= "+ super.getId() +", Name= "+super.getName()+", salary=" + salary + ", experience=" + experience + "]";
	}
	
}
