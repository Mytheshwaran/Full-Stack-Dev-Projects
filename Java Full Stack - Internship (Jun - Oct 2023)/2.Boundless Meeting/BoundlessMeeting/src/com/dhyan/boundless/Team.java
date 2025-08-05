package com.dhyan.boundless;
import java.util.*;
public class Team {
	private List<Manager> managerList=new ArrayList<Manager>();
	private List<SeniorEngineer> seniorEngineerList=new ArrayList<SeniorEngineer>();
	private List<Engineer> engineerList=new ArrayList<Engineer>();
	/**
	 * Get ManagerList
	 * @return
	 */
	public List<Manager> getManagerList() {
		return managerList;
	}
	/**
	 * Set ManagerList
	 * @param managerList
	 */
	public void setManagerList(List<Manager> managerList) {
		this.managerList = managerList;
	}
	/**
	 * Get SeniorEngineerList
	 * @return
	 */
	public List<SeniorEngineer> getSeniorEngineerList() {
		return seniorEngineerList;
	}
	/**
	 * Set SeniorEngineerList
	 * @param seniorEngineerList
	 */
	public void setSeniorEngineerList(List<SeniorEngineer> seniorEngineerList) {
		this.seniorEngineerList = seniorEngineerList;
	}
	/**
	 * Get EngineerList
	 * @return
	 */
	public List<Engineer> getEngineerList() {
		return engineerList;
	}
	/**
	 * Set EngineerList
	 * @param engineerList
	 */
	public void setEngineerList(List<Engineer> engineerList) {
		this.engineerList = engineerList;
	}
	
}