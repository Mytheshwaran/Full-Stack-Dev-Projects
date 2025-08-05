package com.dhyan.boundless;

import java.util.ArrayList;
import java.util.List;

public class ParticipatingTeam extends Team {
	private List<Employee> participatingTeamMembers;
	/**
	 * Get ParticipatingTeamMembers
	 * @return
	 */
	public List<Employee> getParticipatingTeamMembers() {
		return participatingTeamMembers;
	}
	/**
	 * Adding ParticipatingTeamMembers
	 * @param member
	 */
	public void addParticipatingTeamMembers(Employee member)
	{
		if(participatingTeamMembers==null)
			participatingTeamMembers=new ArrayList<Employee>();
		participatingTeamMembers.add(member);
	}
}