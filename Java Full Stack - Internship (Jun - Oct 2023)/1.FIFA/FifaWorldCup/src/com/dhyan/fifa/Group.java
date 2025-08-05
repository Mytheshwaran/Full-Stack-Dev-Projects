package com.dhyan.fifa;

public class Group {
	private String groupName;
    private int groupPoints;
    private int noOfTeam;
    Team[] teamObj=new Team[noOfTeam];
    /**
     * Get group name
     * @return
     */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * set group name
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * get group points
	 * @return
	 */
	public int getGroupPoints() {
		return groupPoints;
	}
	/**
	 * set group points
	 * @param groupPoints
	 */
	public void setGroupPoints(int groupPoints) {
		this.groupPoints = groupPoints;
	}
	/**
	 * return String with name and group points
	 */
	public String toString()
	{
		return "Groupname:"+groupName+" Points:"+groupPoints;
	}
	/**
	 * coping team object array
	 * @param teamObj
	 * @param noOfTeam
	 */
	public void setTeam(Team[] teamObj,int noOfTeam)
	{
		this.noOfTeam=noOfTeam;
		this.teamObj=teamObj;
	}
	/**
	 * Print list of teams
	 */
	public void listOfTeams()
	{
		for(Team team:teamObj)
		{
			System.out.println(team.getTeamName());
		}
	}
	/**
	 * Getting Team Information by checking given team name
	 * @param teamName
	 * @return
	 */
	public int teamInfo(String teamName)
	{
		int flag=0;
		for(Team name:teamObj)
		{
			if(teamName.equals(name.getTeamName()))
			{
				flag=1;
				System.out.println(name);
				break;
			}
		}
		return flag;
	}
	/**
	 * Getting player name list with given team name
	 * @param teamName
	 * @return
	 */
	public int teamPlayers(String teamName)
	{
		int flag=0;
		for(Team teamIndex:teamObj)
		{
			if(teamName.equals(teamIndex.getTeamName()))
			{
				flag=1;
				teamIndex.playerList();
				break;
			}		
		}
		return flag;
	}
	/**
	 * Getting player Information by checking given player name
	 * @param playerName
	 * @return
	 */
	public int playerInfo(String playerName)
	{
		int flag=0;
		for(Team teamIndex:teamObj)
		{
			flag=teamIndex.playerInfo(playerName);
		}
		return flag;
	}
}