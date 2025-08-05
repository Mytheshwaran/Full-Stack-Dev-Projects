package com.dhyan.fifa;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Fifa {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of groups:");
		int noOfGroup = s.nextInt();
		System.out.println("Enter the number of teams:");
		int noOfTeam = s.nextInt();
		System.out.println("Enter the number of players:");
		int noOfPlayer = s.nextInt();
		Group[] groupObj = new Group[noOfGroup];
		for (int i = 0; i < noOfGroup; i++) {
			groupObj[i] = new Group();
			groupObj[i].setGroupName("G" + (i + 1));
			groupObj[i].setGroupPoints(groupPoints());
			Team[] teamObj = new Team[noOfTeam];
			for (int j = 0; j < noOfTeam; j++) {
				teamObj[j] = new Team();
				teamObj[j].setTeamName("G" + (i + 1) + "-" + "T" + (j + 1));
				teamObj[j].setWin(teamWin());
				teamObj[j].setLoss(teamLoss());
				Player[] playerObj = new Player[noOfPlayer];
				for (int k = 0; k < noOfPlayer; k++) {
					playerObj[k] = new Player();
					playerObj[k].setPlayerName("G" + (i + 1) + "-" + "T" + (j + 1) + "-P" + (k + 1));
					playerObj[k].setGoal(playerGoal());
					playerObj[k].setAge(playerAge());
					playerObj[k].setMatch(playerMatch());
				}
				teamObj[j].setPlayer(playerObj, noOfPlayer);
			}
			groupObj[i].setTeam(teamObj, noOfTeam);
		}
		int quit = 1;
		while (quit != 0) {
			System.out.println("1.List of Groups");
			System.out.println("2.Group Information");
			System.out.println("3.List of Teams in a group");
			System.out.println("4.Team Information");
			System.out.println("5.List of players in a team");
			System.out.println("6.Player Information");
			System.out.println("7.Exit");
			int opt = s.nextInt();
			switch (opt) {
			case 1:
				for (Group groupName : groupObj) {
					System.out.println(groupName);
				}
				break;
			case 2:
				System.out.println("Enter the Group name:");
				String groupName = s.next();
				int flag = 0;
				for (Group groupIndex : groupObj) {
					if (groupName.equals(groupIndex.getGroupName())) {
						flag = 1;
						System.out.println("Group Points:" + groupIndex.getGroupPoints());
						System.out.println(
								"It have " + noOfTeam + " teams and " + noOfPlayer + " players for each teams.");
						break;
					}
				}
				if (flag == 0) {
					System.out.println("Enter the valid Group name");
				}
				break;
			case 3:
				System.out.println("Enter the Group name:");
				String groupName1 = s.next();
				int flag1 = 0;
				for (Group groupIndex : groupObj) {
					if (groupName1.equals(groupIndex.getGroupName())) {
						flag1 = 1;
						System.out.println(groupIndex.getGroupName());
						groupIndex.listOfTeams();
						break;
					}
				}
				if (flag1 == 0) {
				System.out.println("Enter the valid Group name");
				}
				break;
			case 4:
				System.out.println("Enter the Team name:");
				String teamName1 = s.next();
				int find1=0;
				for (Group groupIndex : groupObj) {
					find1=groupIndex.teamInfo(teamName1);
				}
				if(find1==0)
				{
					System.out.println("Enter valid Team name");
				}
				break;
			case 5:
				System.out.println("Enter the Team name:");
				String teamName = s.next();
				int find=0;
				for (Group groupIndex : groupObj) {
					find=groupIndex.teamPlayers(teamName);
				}
				if(find==0)
				{
					System.out.println("Enter valid Team name");
				}
				break;
			case 6:
				System.out.println("Enter the player name:");
				String playerName = s.next();
				int flag2=0;
				for (Group groupIndex : groupObj) {
					flag2=groupIndex.playerInfo(playerName);
				}
				if(flag2==0)
				{
					System.out.println("Enter valid Player name");
				}
				break;
			default:
				quit = 0;
				break;
			}
		}
	}
	/**
	 * Generate random group points
	 * @return
	 */
	static int groupPoints() {
		int min = 0;
		int max = 10;
		return (ThreadLocalRandom.current().nextInt(min, max));
	}
	/**
	 * Generate random team win count
	 * @return
	 */
	static int teamWin() {
		int min = 0;
		int max = 10;
		return (ThreadLocalRandom.current().nextInt(min, max));
	}
	/**
	 * Generate random team Loss count
	 * @return
	 */
	static int teamLoss() {
		int min = 0;
		int max = 10;
		return (ThreadLocalRandom.current().nextInt(min, max));
	}
	/**
	 * Generate random player goal
	 * @return
	 */
	static int playerGoal() {
		int min = 1;
		int max = 10;
		return (ThreadLocalRandom.current().nextInt(min, max));
	}
	/**
	 * Generate random player age
	 * @return
	 */
	static int playerAge() {
		int min = 18;
		int max = 30;
		return (ThreadLocalRandom.current().nextInt(min, max));
	}
	/**
	 * Generate random Match count
	 * @return
	 */
	static int playerMatch() {
		int min = 0;
		int max = 10;
		return (ThreadLocalRandom.current().nextInt(min, max));
	}
}