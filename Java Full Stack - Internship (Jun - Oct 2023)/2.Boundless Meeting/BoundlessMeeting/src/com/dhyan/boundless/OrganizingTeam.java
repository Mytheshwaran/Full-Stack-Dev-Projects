package com.dhyan.boundless;

import java.util.ArrayList;
import java.util.List;

public class OrganizingTeam extends Team {
	private List<Employee> organizingTeamMembers;
	/**
	 * Get OrganizingTeam Members
	 * @return
	 */
	public List<Employee> getOrganizingTeamMembers() {
		return organizingTeamMembers;
	}
	/**
	 * Adding OrganizingTeamMembers
	 * @param member
	 */
	public void addOrganizingTeamMembers(Employee member)
	{
		if(organizingTeamMembers==null)
			organizingTeamMembers=new ArrayList<Employee>();
		organizingTeamMembers.add(member);
	}
	
}
