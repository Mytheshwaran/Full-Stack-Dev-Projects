package com.dhyan.rest.common;

import javax.ejb.Local;

@Local
public interface EmployeeManagerLocal {
	public String addEmployee(JsonEmployee jsonEmployee);
	public String deleteEmployee(String[] jsonData);
	public String updateEmployee(JsonEmployee jsonEmployee);
	public String retrieveEmployee();
	public String checkDetails(String jsonData);
}
