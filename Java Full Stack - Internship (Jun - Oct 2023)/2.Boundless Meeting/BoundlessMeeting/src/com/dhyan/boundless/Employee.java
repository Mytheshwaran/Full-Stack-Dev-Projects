package com.dhyan.boundless;

public class Employee {
	public String name;
	public String designation;
	/**
	 * Add Employee
	 * @param name
	 * @param designation
	 */
	public Employee(String name,String designation)
	{
		this.name=name;
		this.designation=designation;
	}
	/**
	 * Get Name
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Set Designation
	 * @return
	 */
	public String getDesignation()
	{
		return designation;
	}
	@Override
	public String toString()
	{
		return name+"_"+designation;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
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
		Employee other = (Employee) obj;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}