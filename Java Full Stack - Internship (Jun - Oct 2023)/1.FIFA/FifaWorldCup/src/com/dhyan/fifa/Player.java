package com.dhyan.fifa;

public class Player {
	private String playerName;
    private int goal;
    private int age;
    private int match;
    /**
     * get player name
     * @return
     */
    public String getPlayerName()
    {
        return playerName;
    } 
    /**
     * set player name
     * @param playerName
     */
    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }   
    /**
     * get player goal
     * @return
     */
    protected int getGoal() {
		return goal;
	}
    /**
     * set player goal 
     * @param goal
     */
	protected void setGoal(int goal) {
		this.goal = goal;
	}
	/**
	 * get player age
	 * @return
	 */
	public int getAge()
    {
        return age;
    } 
	/**
	 * set player age
	 * @param age
	 */
    public void setAge(int age)
    {
        this.age = age;
    } 
    /**
     * get player played matches
     * @return
     */
    public int getMatch()
    {
        return match;
    } 
    /**
     * set player played matches
     * @param match
     */
    public void setMatch(int match)
    {
        this.match = match;
    }
    /**
     * Print String with player name,goals,age and matches played 
     */
    public String toString()
    {
    	return "Name:"+playerName+" Goals:"+goal+" Age:"+age+" Matches Played:"+match;
    }

}