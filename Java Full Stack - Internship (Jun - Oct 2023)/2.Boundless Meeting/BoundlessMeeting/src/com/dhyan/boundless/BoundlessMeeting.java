package com.dhyan.boundless;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BoundlessMeeting {
	private List<Manager> managerList = new ArrayList<Manager>();
	private List<SeniorEngineer> seniorEngineerList = new ArrayList<SeniorEngineer>();
	private List<Engineer> engineerList = new ArrayList<Engineer>();
	private List<Employee> tempManagerList = new ArrayList<Employee>();
	private List<Employee> tempEngineerList = new ArrayList<Employee>();
	private List<Employee> tempSeniorEngineerList = new ArrayList<Employee>();
	private List<OrganizingTeam> organizingTeam;
	private List<ParticipatingTeam> participatingTeamsList;

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Size of Organizing Team(must >=3):");
		int organizingTeamSize = scan.nextInt();
		System.out.println("Enter the Size of Boundless Team:");
		int boundlessTeamSize = scan.nextInt();

		BoundlessMeeting meetingObj = new BoundlessMeeting();

		meetingObj.initializeEmployee();
		meetingObj.shuffleEmployees();
		meetingObj.displayEmployees();

		while (true) {
			System.out.println("--------OrganizingTeam--------");
			meetingObj.createOrganizingTeam(organizingTeamSize);
			meetingObj.displayOrganizingTeam();

			System.out.println("--------ParticipatingTeam--------");

			meetingObj.createParticipatingTeam(boundlessTeamSize);
			meetingObj.displayParticipatingTeams();

			System.out.println("--Enter 0 to exit--");
			int exit = scan.nextInt();
			if (exit == 0)
				break;
		}
		scan.close();
		System.out.println("---End---");
	}
	/**
	 * Initialize Employees by reading text file
	 * @throws Exception
	 */
	public void initializeEmployee() throws Exception {
		File file = new File("input.txt");
		Scanner readFile = new Scanner(file);
		while (readFile.hasNextLine()) {
			String[] employee = readFile.nextLine().split("-");
			String name = employee[0];
			String designation = employee[1];
			if (designation.equals("Manager")) {
				managerList.add(new Manager(name, designation));
			} else if (designation.equals("SeniorEngineer")) {
				seniorEngineerList.add(new SeniorEngineer(name, designation));
			} else if (designation.equals("Engineer")) {
				engineerList.add(new Engineer(name, designation));
			}
		}
		readFile.close();
		Team teamObj = new Team();
		teamObj.setManagerList(managerList);
		teamObj.setSeniorEngineerList(seniorEngineerList);
		teamObj.setEngineerList(engineerList);

		tempManagerList.addAll(teamObj.getManagerList());
		tempSeniorEngineerList.addAll(teamObj.getSeniorEngineerList());
		tempEngineerList.addAll(teamObj.getEngineerList());
	}
	/**
	 * shuffle employee Lists
	 */
	public void shuffleEmployees() {
		Collections.shuffle(tempManagerList);
		Collections.shuffle(tempSeniorEngineerList);
		Collections.shuffle(tempEngineerList);
	}
	/**
	 * Display Employees
	 */
	public void displayEmployees() {
		System.out.println(managerList.size() + " " + managerList);
		System.out.println(seniorEngineerList.size() + " " + seniorEngineerList);
		System.out.println(engineerList.size() + " " + engineerList);
	}
	/**
	 * Create OrganizingTeam
	 * @param organizingTeamSize
	 */
	public void createOrganizingTeam(int organizingTeamSize) {
		organizingTeam = new ArrayList<OrganizingTeam>();
		OrganizingTeam organizingTeamObj = new OrganizingTeam();
		organizingTeam.add(organizingTeamObj);

		int opt = 1;
		for (int i = 0; i < organizingTeamSize; i++) {
			switch (opt) {
			case 1:
				if (!tempManagerList.isEmpty()) {
					selectOrganizingManager(organizingTeamObj, tempManagerList);
				} else {
					tempManagerList.addAll(managerList);
					selectOrganizingManager(organizingTeamObj, tempManagerList);
				}
				break;
			case 2:
				if (!tempSeniorEngineerList.isEmpty()) {
					selectOrganizingSeniorEngineer(organizingTeamObj,tempSeniorEngineerList);
				} else {
					tempSeniorEngineerList.addAll(seniorEngineerList);
					selectOrganizingSeniorEngineer(organizingTeamObj, tempSeniorEngineerList);
				}
				break;
			case 3:
				if (!tempEngineerList.isEmpty()) {
					selectOrganizingEngineer(organizingTeamObj,tempEngineerList);
				} else {
					tempEngineerList.addAll(engineerList);
					selectOrganizingEngineer(organizingTeamObj,tempEngineerList);
				}
				break;
			}
			if (i == 0) {
				if (!tempSeniorEngineerList.isEmpty()) {
					selectOrganizingSeniorEngineer(organizingTeamObj,tempSeniorEngineerList);
				} else {
					tempSeniorEngineerList.addAll(seniorEngineerList);
					selectOrganizingSeniorEngineer(organizingTeamObj, tempSeniorEngineerList);
				}
				if (!tempEngineerList.isEmpty()) {
					selectOrganizingEngineer(organizingTeamObj,tempEngineerList);
				} else {
					tempEngineerList.addAll(organizingTeamObj.getEngineerList());
					selectOrganizingEngineer(organizingTeamObj,tempEngineerList);
				}
				i += 2;
			}
			opt = randomCase();
		}
	}
	/**
	 * Select OrganizingTeam Managers
	 * @param organizingTeamObj
	 * @param managers
	 */
	public void selectOrganizingManager(OrganizingTeam organizingTeamObj,List<Employee> managers) {
		boolean isManagerFound = false;
		while (!isManagerFound) {
			int randomIndex = (int) (Math.random() * managers.size());
			if (organizingTeamObj.getOrganizingTeamMembers()==null) {
				organizingTeamObj.addOrganizingTeamMembers(managers.get(randomIndex));
				managers.remove(randomIndex);
				isManagerFound = true;
			} else if (!organizingTeamObj.getOrganizingTeamMembers().contains(managers.get(randomIndex))) {
				organizingTeamObj.addOrganizingTeamMembers(managers.get(randomIndex));
				managers.remove(randomIndex);
				isManagerFound = true;
			}
		}
	}
	/**
	 * Select OrganizingTeam SeniorEngineers
	 * @param organizingTeamObj
	 * @param seniorEngineers
	 */
	public void selectOrganizingSeniorEngineer(OrganizingTeam organizingTeamObj, List<Employee> seniorEngineers) {
		boolean isSeniorEngineerFound = false;
		while (!isSeniorEngineerFound) {
			int randomIndex = (int) (Math.random() * seniorEngineers.size());
			if (organizingTeamObj.getOrganizingTeamMembers()==null) {
				organizingTeamObj.addOrganizingTeamMembers(seniorEngineers.get(randomIndex));
				seniorEngineers.remove(randomIndex);
				isSeniorEngineerFound = true;
			} else if (!organizingTeamObj.getOrganizingTeamMembers().contains(seniorEngineers.get(randomIndex))) {
				organizingTeamObj.addOrganizingTeamMembers(seniorEngineers.get(randomIndex));
				seniorEngineers.remove(randomIndex);
				isSeniorEngineerFound = true;
			}
		}
	}
	/**
	 * Select OrganizingTeam Engineers
	 * @param organizingTeamObj
	 * @param engineers
	 */
	public void selectOrganizingEngineer(OrganizingTeam organizingTeamObj, List<Employee> engineers) {
		boolean isEngineerFound = false;
		while (!isEngineerFound) {
			int randomIndex = (int) (Math.random() * engineers.size());
			if (organizingTeamObj.getOrganizingTeamMembers()==null) {
				organizingTeamObj.addOrganizingTeamMembers(engineers.get(randomIndex));
				engineers.remove(randomIndex);
				isEngineerFound = true;
			} else if (!organizingTeamObj.getOrganizingTeamMembers().contains(engineers.get(randomIndex))) {
				organizingTeamObj.addOrganizingTeamMembers(engineers.get(randomIndex));
				engineers.remove(randomIndex);
				isEngineerFound = true;
			}
		}
	}
	/**
	 * Display OrganizingTeam
	 */
	public void displayOrganizingTeam() {
		System.out.println(organizingTeam.get(0).getOrganizingTeamMembers().size() + " "
				+ organizingTeam.get(0).getOrganizingTeamMembers());
	}
	/**
	 * Create ParticipatingTeam
	 * @param boundlessTeamSize
	 */
	public void createParticipatingTeam(int boundlessTeamSize) {
		participatingTeamsList = new ArrayList<ParticipatingTeam>();
		for (int i = 0; i < boundlessTeamSize; i++) {
			ParticipatingTeam teamObj = new ParticipatingTeam();
			participatingTeamsList.add(teamObj);
		}

		addParticipatingTeamManager();
		addParticipatingTeamSeniorEngineer();
		addParticipatingTeamEngineer();
	}
	/**
	 * Add participatingTeam manager
	 */
	public void addParticipatingTeamManager() {
		int participatingTeam = 0;
		for (Employee manager : managerList) {
			if (!organizingTeam.get(0).getOrganizingTeamMembers().contains(manager)) {
				participatingTeamsList.get(participatingTeam).addParticipatingTeamMembers(manager);
				participatingTeam++;
				if (participatingTeam == participatingTeamsList.size())
					participatingTeam = 0;
			}
		}
	}
	/**
	 * Add participatingTeam SeniorEngineer
	 */
	public void addParticipatingTeamSeniorEngineer() {
		int participatingTeam = 0;
		for (Employee seniorEngineer : seniorEngineerList) {
			if (!organizingTeam.get(0).getOrganizingTeamMembers().contains(seniorEngineer)) {
				participatingTeamsList.get(participatingTeam).addParticipatingTeamMembers(seniorEngineer);
				participatingTeam++;
				if (participatingTeam == participatingTeamsList.size())
					participatingTeam = 0;
			}
		}
	}
	/**
	 * Add ParticipatingTeam Engineer
	 */
	public void addParticipatingTeamEngineer() {
		int participatingTeam = 0;
		for (Employee engineer : engineerList) {
			if (!organizingTeam.get(0).getOrganizingTeamMembers().contains(engineer)) {
				participatingTeamsList.get(participatingTeam).addParticipatingTeamMembers(engineer);
				participatingTeam++;
				if (participatingTeam == participatingTeamsList.size())
					participatingTeam = 0;
			}
		}
	}
	/**
	 * Display ParticipatingTeam
	 */
	public void displayParticipatingTeams() {
		int team = 1;
		for (ParticipatingTeam teamObj : participatingTeamsList) {
			System.out.println("Team " + team + " : " + teamObj.getParticipatingTeamMembers().size() + " "
					+ teamObj.getParticipatingTeamMembers());
			team++;
		}
	}
	/**
	 * Generate random case to form OrganizingTeam
	 * @return
	 */
	public int randomCase() {
		int min = 1;
		int max = 3;
		return ThreadLocalRandom.current().nextInt(min, max);
	}
}