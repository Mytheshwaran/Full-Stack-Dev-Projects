package com.dhyan.cards;

public class Card {
	private String value;
	private String symbol;

	/**
	 * Get Value
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set value
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * Set symbol
	 * 
	 * @param symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Return symbol and value as string
	 */
	public String toString() {
		return symbol + " " + value;
	}
}
