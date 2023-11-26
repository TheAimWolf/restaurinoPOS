package restaurinoPOS;

import java.util.ArrayList;
import java.util.List;

public class Gast extends Person
{
	private Tisch gastZugewiesenerTisch;
	private List <Bestellung> gastBestellungen = new ArrayList<>();
	private double gastBestellungenSumme;
	
	public Gast(String vorname, String nachname, Tisch tisch)
	{
		super(vorname, nachname);
		tisch.tischGastHinzufuegen(this);
	}
	
	public void gastBestellt(Bestellung Bestellung)
	{
		// TODO
	}
	
	public void gastBezahlt()
	{
		System.out.println("Gast: " + getPersonName());
		
		for (int i = 0; i < gastBestellungen.size(); i++) {
			System.out.println(gastBestellungen.get(i));
		}
		
		// RECHNUNGSSUMME DYNAMISCH AUSRECHNEN \\
		System.out.println("Rechnungssumme: " + gastBestellungenSumme);
		
		// EINGABE ABFRAGEN, OB BEZAHLVORGANG ERFOLGREICH \\
		gastBestellungenSumme = 0;
	}
	
	public boolean istBezahlt() {
		if (gastBestellungenSumme == 0) {
			return true;
		} else {
			return false;
		}
	}
}