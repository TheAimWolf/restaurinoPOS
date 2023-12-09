package com.restaurinoinc.restaurinopos;

/**
 * Kellner im Restaurant
 * Kellner bedient Tisch/Gast
 */
public class Kellner extends Person {
	
	/**
	 * Public Konstruktor Klasse Kellner
	 * 
	 * @see Person#Person(String, String)
	 * @param vorname Zum Anlegen eines Kellners wird ein Vorname benötigt.
	 * @param nachname Zum Anlegen eines Kellners wird ein Nachname benötigt.
	 */
	public Kellner(String vorname, String nachname) {
		super(vorname, nachname);
	}
}