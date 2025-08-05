package com.dhyan.employee.details;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="retired_employee")
@NamedQuery(name="retiredEmployee",query = "Select o from RetiredEmployee o")
@DiscriminatorValue(value = "retired")
public class RetiredEmployee extends Employee{

	@Column(name="emp_penson")
	private int penson;

	/**
	 * @return the penson
	 */
	public int getPenson() {
		return penson;
	}

	/**
	 * @param penson the penson to set
	 */
	public void setPenson(int penson) {
		this.penson = penson;
	}

	@Override
	public String toString() {
		return "RetiredEmployee [Id= "+ super.getId() +", Name= "+super.getName()+", penson=" + penson + "]";
	}
	
}
