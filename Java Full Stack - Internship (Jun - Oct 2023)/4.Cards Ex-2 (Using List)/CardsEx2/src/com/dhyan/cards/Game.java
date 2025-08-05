package com.dhyan.cards;

import java.util.*;

public class Game {
	private List<Card> cardList;
	private List<Player> playerList;
	private Map<Player, Integer> playerPoints;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of players:");
		int noOfPlayers = s.nextInt();
		System.out.println("Enter number of matchs:");
		int noOfMatches = s.nextInt();
		Game gameObj = new Game();
		gameObj.playerPoints = new HashMap<Player, Integer>();
		gameObj.initialize(noOfPlayers, noOfMatches);
		int round = 0;
		while (noOfMatches != round) {
			System.out
					.println("==========================-----Match " + (round + 1) + "-----==========================");
			Collections.shuffle(gameObj.cardList);
			System.out.println(gameObj.cardList);
			gameObj.distributingCards();
			gameObj.displayPlayerCards();
			gameObj.playerPoints = gameObj.playGame();
			gameObj.clearCards();
			round++;
		}
		gameObj.displayPoints();
	}

	/**
	 * Initialize cards & Players
	 * 
	 * @param noOfPlayers
	 * @param noOfMatches
	 */
	public void initialize(int noOfPlayers, int noOfMatches) {
		cardList = initializeCards();
		playerList = initializePlayers(noOfPlayers);

	}

	/**
	 * Initialize cards
	 * 
	 * @return
	 */
	public List<Card> initializeCards() {
		String[] values = new String[] { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
		String[] symbols = new String[] { "spade", "Hearts", "Diamonds", "Clubs" };
		List<Card> cardList = new ArrayList<Card>();
		for (String value : values) {
			for (String symbol : symbols) {
				cardList.add(new Card(value, symbol));
			}
		}
		return cardList;
	}

	/**
	 * Initialize players
	 * 
	 * @param noOfPlayers
	 * @return
	 */
	public List<Player> initializePlayers(int noOfPlayers) {
		List<Player> playerList = new ArrayList<Player>();
		for (int i = 0; i < noOfPlayers; i++) {
			Player playerObj = new Player();
			playerObj.setName("P" + (i + 1));
			playerList.add(playerObj);
		}
		return playerList;
	}

	/**
	 * Distribute cards to players
	 */
	public void distributingCards() {
		int playerIndex = 0;
		for (Card card : cardList) {
			playerList.get(playerIndex).setCards(card);
			playerIndex++;
			if (playerIndex == playerList.size())
				playerIndex = 0;
		}
	}

	/**
	 * play game and return player points
	 * 
	 * @return
	 */
	public Map<Player, Integer> playGame() {
		List<Card> dropCards = new ArrayList<Card>();
		Card previousCard = null;
		int setCount = 1;
		boolean playGame = true;
		while (playGame) {
			System.out.println("------Set:" + setCount++ + "------");
			for (Player player : playerList) {
				Card currentCard = dropCard(player);
				if (currentCard != null) {
					System.out.println("Player " + player.getName() + " : " + currentCard);
					dropCards.add(currentCard);

					if (previousCard != null && previousCard.getValue().equals(currentCard.getValue())) {
						System.out.println("~~~~~~~~~~~~Player " + player.getName() + " Win~~~~~~~~~~~");
						playerPoints.put(player, playerPoints.getOrDefault(player, 0) + 5);
						insertCards(player, dropCards);
						playGame = false;
						break;
					}
					previousCard = currentCard;
				} else {
					System.out.println("-------No one win the game-------");
					break;
				}
			}
			displayPlayerCards();
		}
		return playerPoints;
	}

	/**
	 * Drop cards
	 * 
	 * @param player
	 * @return
	 */
	public Card dropCard(Player player) {
		List<Card> cardList = player.getCards();
		return cardList.remove(cardList.size() - 1);
	}

	/**
	 * Display player cards
	 */
	public void displayPlayerCards() {
		for (Player player : playerList)
			System.out
					.println(player);
	}

	/**
	 * Insert cards to win player
	 * 
	 * @param player
	 * @param winPlayerCards
	 */
	public void insertCards(Player player, List<Card> winPlayerCards) {
		player.getCards().addAll(0, winPlayerCards);
	}

	/**
	 * clear all playerCards
	 */
	public void clearCards() {
		for (Player player : playerList)
			player.getCards().clear();
	}

	/**
	 * Display players points
	 */
	public void displayPoints() {
		System.out.println("==========Player Points=========");
		for (Player player : playerList) {
			if (playerPoints.get(player) != null)
				System.out.println("Player " + player.getName() + " : " + playerPoints.get(player));
		}
	}
}