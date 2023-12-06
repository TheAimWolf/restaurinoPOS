package restaurinoPOS;

import java.util.ArrayList;
import java.util.List;

/**
 * Posten auf Speisekarte
 */
public class Posten {
	private double postenPreis;
	private String postenName;
	private List<Posten> allePosten = new ArrayList<>();

	/**
	 * Public Konstruktor Klasse Posten
	 * 
	 * @param name Zum Anlegen eines Postens wird ein Name benötigt. Bsp: "Schnitzel mit Pommes"
	 *        preis Zum Anlegen eines Postens wird ein Preis benötigt.
	 */
	public Posten(String name, double preis) {
		this.postenName = name;
		this.postenPreis = preis;

		allePosten.add(this);
	}

	protected String getPostenName() {
		return postenName;
	}

	protected double getPostenPreis() {
		return postenPreis;
	}
}
