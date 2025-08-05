package com.dhyan.cards;

public class Player {
	private String name;
	private Card[] cards = new Card[52];

	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get cards
	 * 
	 * @return
	 */
	public Card[] getCards() {
		return cards;
	}

	/**
	 * Set cards
	 * 
	 * @param cardsArr
	 */
	public void setCards(Card[] cardsArr) {
		this.cards = cardsArr;
	}

	/**
	 * Return name as String
	 */
	public String toString() {
		return name;
	}

}