package com.dhyan.cards;

import java.util.*;

public class Game {
	private Card[] cardsArr;
	private Player[] playerArr;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the Number of players");
		int noOfPlayers = s.nextInt();
		Game gameObj = new Game();
		gameObj.initializeCards();
		gameObj.shuffleCards();
		gameObj.initializePlayer(noOfPlayers);
		gameObj.distributingCards();
		gameObj.displayPlayerCards();
		gameObj.playGame();
	}

	/**
	 * Initialize cards
	 */
	public void initializeCards() {
		String[] values = new String[] { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king" };
		String[] symbols = new String[] { "spade", "Hearts", "Diamonds", "Clubs" };
		int sizeOfCards = values.length * symbols.length;
		cardsArr = new Card[sizeOfCards];
		int cardIndex = 0;
		for (int i = 0; i < symbols.length; i++) {
			for (int j = 0; j < values.length; j++, cardIndex++) {
				cardsArr[cardIndex] = new Card();
				cardsArr[cardIndex].setValue(values[j]);
				cardsArr[cardIndex].setSymbol(symbols[i]);
			}
		}
	}

	/**
	 * shuffle cards
	 */
	public void shuffleCards() {
		Random rand = new Random();

		for (int i = 0; i < cardsArr.length; i++) {
			int random = rand.nextInt(cardsArr.length);
			Card temp = cardsArr[random];
			cardsArr[random] = cardsArr[i];
			cardsArr[i] = temp;
		}
		for (Card card : cardsArr)
			System.out.println(card);
	}

	/**
	 * Initialize players
	 * 
	 * @param noOfPlayers
	 */
	public void initializePlayer(int noOfPlayers) {
		playerArr = new Player[noOfPlayers];
		for (int playerIndex = 0; playerIndex < noOfPlayers; playerIndex++) {
			playerArr[playerIndex] = new Player();
			playerArr[playerIndex].setName("P" + (playerIndex + 1));
		}
	}

	/**
	 * Distribute cards to player
	 */
	public void distributingCards() {
		int playerCardIndex = 0;
		int cardIndex = 0;
		while (cardIndex < cardsArr.length) {
			for (Player player : playerArr) {
				player.getCards()[playerCardIndex] = cardsArr[cardIndex];
				cardIndex++;
				if (cardIndex == cardsArr.length)
					break;
			}
			playerCardIndex++;
		}
	}

	/**
	 * Game play
	 */
	public void playGame() {
		Card[] droppedCards = new Card[cardsArr.length];
		Card previousCard = null;
		int droppedCardIndex = 0;
		int setCount = 1;
		boolean isGameOver = false;
		while (!isGameOver) {
			System.out.println("------Set:" + setCount + "------");
			for (Player player : playerArr) {
				Card currentCard = dropCard(player);

				if (currentCard != null) {
					System.out.println("Player " + player.getName() + " : " + currentCard);
					droppedCards[droppedCardIndex] = currentCard;
					droppedCardIndex++;

					// If player wins
					if (previousCard != null && previousCard.getValue().equals(currentCard.getValue())) {
						System.out.println("--------Player " + player.getName() + " Win--------");
						insertCards(player, droppedCards);
						isGameOver = true;
						break;
					}
					previousCard = currentCard;
				} else { // If draw
					System.out.println("-------No one win the game-------");
					isGameOver = true;
					break;
				}
			}
			setCount++;
			displayPlayerCards();
		}
	}

	/**
	 * player drop card
	 * 
	 * @param player
	 * @return
	 */
	public Card dropCard(Player player) {
		Card dropCard = null;
		int sizeOfCards = player.getCards().length;
		for (int i = sizeOfCards - 1; i >= 0; i--) {
			if (player.getCards()[i] != null) {
				dropCard = player.getCards()[i];
				player.getCards()[i] = null;
				break;
			}
		}

		return dropCard;
	}

	/**
	 * Insert cards to winning player
	 * 
	 * @param dropCards
	 * @param player
	 */
	public void insertCards(Player player, Card[] dropCards) {
		Card[] newCards = new Card[player.getCards().length + dropCards.length];
		int playerCardIndex = 0;
		for (Card card : dropCards) {
			if (card != null) {
				newCards[playerCardIndex] = card;
				playerCardIndex++;
			}
		}
		for (Card card : player.getCards()) {
			if (card != null) {
				newCards[playerCardIndex] = card;
				playerCardIndex++;
			}
		}
		player.setCards(newCards);
	}

	public void displayPlayerCards() {
		for (Player player : playerArr) {
			int cardSize = 0;
			System.out.print("Player " + player.getName() + " :  [");
			for (Card card : player.getCards()) {
				if (card != null) {
					System.out.print(card + ", ");
					cardSize++;
				}
			}
			System.out.println("] " + cardSize);
		}
	}
}