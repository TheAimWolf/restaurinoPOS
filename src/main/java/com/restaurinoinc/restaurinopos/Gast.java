package com.restaurinoinc.restaurinopos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Gast im Restaurant
 */
public class Gast extends Person {
	private List<Posten> gastBestellungen = new ArrayList<>();
	private Tisch tisch;

	/**
	 * Public Konstruktor Klasse Gast
	 *
	 * @param vorname  Zum Anlegen des Gastes wird ein Vorname benötigt.
	 * @param nachname Zum Anlegen des Gastes wird ein Nachname benötigt.
	 * @param tisch    Zum Anlegen eines Gastes wird diesem direkt einem Tisch im Restaurant zugewiesen.
	 * @see Person#Person(String, String)
	 */
	public Gast(String vorname, String nachname, Tisch tisch) {
		super(vorname, nachname);

		this.tisch = tisch;
		tisch.tischGastHinzufuegen(this);
	}

	/**
	 * Getter für Tisch Objekt
	 * @return Tisch
	 */
	public Tisch getTisch() {
		return tisch;
	}

	protected void gastBestellt(Posten posten) {
		if (tisch.tischKellnerZugewiesen() == true) {
			gastBestellungen.add(posten);
		} else {
			System.out.println("Dem Tisch ist kein Kellner zugewiesen, daher keine Bestellungen möglich. Bitte Tisch-Kellner-Zuweisung überprüfen!");
		}
	}

	/**
	 * Getter für die Liste der Gastbestellungen
	 * @return List<Posten> Gast-Bestellungen
	 */
	public List<Posten> getGastBestellungen(){
		return gastBestellungen;
	}

	/**
	 * Getter für Gast-Tischnummer
	 * @return int Tischnummer
	 */
	public int getTischnummer(){
		return tisch.getTischnummer();
	}

	protected void gastBezahlt() {
		System.out.println("Gast: " + getPersonName());

		if (gastBestellungenGetSumme() == 0) {
			System.out.println("Gast hat nichts bestellt.");
		} else {
			for (int i = 0; i < gastBestellungen.size(); i++) {
				System.out.println(gastBestellungen.get(i).getPostenName());
			}

			System.out.println("Rechnungssumme: " + gastBestellungenGetSumme() + "€");

			Restaurant.restaurantGesamtumsatzHinzufuegen(gastBestellungenGetSumme());
			gastBestellungenLeeren();
		}

	}

	/**
	 * Summe der Gast-Bestellung zurückgeben
	 * @return int Summe der Gast-Rechnung
	 */
	public double gastBestellungenGetSumme() {
		double gastBestellungenSumme = 0;

		for (int i = 0; i < gastBestellungen.size(); i++) {
			gastBestellungenSumme = gastBestellungenSumme + gastBestellungen.get(i).getPostenPreis();
		}

		return Math.round(gastBestellungenSumme * 100.0) / 100.0;
	}

	private void gastBestellungenLeeren() {
		gastBestellungen.clear();
	}

	protected boolean gastHatBezahlt() {
		if (gastBestellungenGetSumme() == 0) {
			return true;
		} else {
			return false;
		}
	}
}