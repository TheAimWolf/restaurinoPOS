package restaurinoPOS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Gast im Restaurant
 */
public class Gast extends Person {
	private List<Posten> gastBestellungen = new ArrayList<>();
	private Tisch tisch;
	private Restaurant restaurant;

	/**
	 * Public Konstruktor Klasse Gast
	 * 
	 * @see Person#Person(String, String)
	 * @param vorname Zum Anlegen des Gastes wird ein Vorname benötigt.
	 * @param nachname Zum Anlegen des Gastes wird ein Nachname benötigt.
	 * @param tisch Zum Anlegen eines Gastes wird diesem direkt einem Tisch im Restaurant zugewiesen.
	 */
	public Gast(String vorname, String nachname, Tisch tisch) {
		super(vorname, nachname);

		this.tisch = tisch;
		tisch.tischGastHinzufuegen(this);
	}

	protected void gastBestellt(Posten posten) {
		if (tisch.tischKellnerZugewiesen() == true) {
			gastBestellungen.add(posten);
		} else {
			System.out.println("Dem Tisch ist kein Kellner zugewiesen, daher keine Bestellungen möglich. Bitte Tisch-Kellner-Zuweisung überprüfen!");
		}
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

			System.out.println("Ist die Rechnung bezahlt worden?");
			Scanner sc = new Scanner(System.in);
			String usereingabe = sc.next();

			if (usereingabe.equalsIgnoreCase("ja")) {
				restaurant.restaurantGesamtumsatzHinzufuegen(gastBestellungenGetSumme());
				gastBestellungenLeeren();
				sc.close();
			}
		}

	}

	private double gastBestellungenGetSumme() {
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