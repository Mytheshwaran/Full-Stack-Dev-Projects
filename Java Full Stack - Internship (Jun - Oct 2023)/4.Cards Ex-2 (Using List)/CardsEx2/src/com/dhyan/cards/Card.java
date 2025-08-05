package com.dhyan.cards;

public class Card {
	private String value;
	private String symbol;

	/**
	 * Set values and cards
	 * 
	 * @param value
	 * @param symbol
	 */
	public Card(String value, String symbol) {
		this.value = value;
		this.symbol = symbol;
	}

	/**
	 * Get value
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Get symbol
	 * 
	 * @return
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Return symbol and value as string
	 */
	public String toString() {
		return symbol + " " + value;
	}
}