package com.dhyan.cards;

import java.util.*;

public class Player {
	private String name;
	private List<Card> cards;

	/**
	 * Get name
	 * 
	 * @return
	 */
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
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * Set cards
	 * 
	 * @param playerCard
	 */
	public void setCards(Card playerCard) {
		if (cards == null)
			cards = new ArrayList<Card>();
		cards.add(playerCard);
	}
	
	@Override
	public String toString() {
		return "Player " + this.getName() + " : " + this.getCards().size() + " " + this.getCards();
	}
}