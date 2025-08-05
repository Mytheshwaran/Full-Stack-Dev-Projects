package com.dhyan.fifa;

public class Team {
	private String teamName;
    private int win;
    private int loss;
    private int noOfPlayer;
    Player[] playerObj=new Player[noOfPlayer];
    /**
     * get Team name
     * @return
     */
    public String getTeamName()
    {
        return teamName;
    } 
    /**
     * set Team name
     * @param teamName
     */
    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }
    /**
     * get team win count
     * @return
     */
    public int getWin()
    {
        return win;
    } 
    /**
     * set team win count
     * @param win
     */
	public void setWin(int win)
    {
        this.win = win;
    } 
	/**
	 * get team loss count
	 * @return
	 */
    public int getLoss()
    {
        return loss;
    } 
    /**
     * set team Loss count
     * @param loss
     */
    public void setLoss(int loss)
    {
        this.loss = loss;
    }
    /**
     * return String with team name,team wins and team lost count
     */
    public String toString()
    {
    	return "Team name:"+teamName+" Wins:"+win+" Lost:"+loss;
    }
    /**
     * coping player object array to another array
     **/
    public void setPlayer(Player[] playerObj,int noOfPlayer)
    {
    	this.noOfPlayer=noOfPlayer;
    	this.playerObj=playerObj;
    }
    /**
     * Print the player list with name
     */
    public void playerList()
    {
    	for(Player playerIndex:playerObj)
		{
			System.out.println(playerIndex.getPlayerName());
		}
    }
    /**
     * return player information with player name,wins and lost count
     * @param playerName
     * @return
     */
    public int playerInfo(String playerName)
    {
    	int flag=0;
    	for(Player playerIndex:playerObj)
		{
			if(playerName.equals(playerIndex.getPlayerName()))
			{
				flag=1;
				System.out.println(playerIndex);
				break;
			}
		}
		return flag;
    }
}