package restaurinoPOS;

public class Tisch {
	private final int TISCHGROESSE = 4;
	private Gast[] tischGaeste = new Gast[TISCHGROESSE];
	private Kellner tischZugewiesenerKellner;
	private Restaurant restaurant;

	public Tisch() {
		restaurant.restaurantTischeHinzufuegen(this);
	}
	
	public void tischKellnerZuweisen(Kellner kellner) {
		tischZugewiesenerKellner = kellner;
	}

	public void tischKellnerEntfernen() {
		tischZugewiesenerKellner = null;
	}

	public boolean tischKellnerZugewiesen() {
		if (tischZugewiesenerKellner == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean tischBesetzt() {
		if (tischAnzahlGaeste() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean tischVoll() {
		if (tischAnzahlGaeste() == TISCHGROESSE) {
			return true;
		} else {
			return false;
		}
	}

	public int tischAnzahlGaeste() {
		int anzahlGaesteAnTisch = 0;

		for (int i = 0; i < tischGaeste.length; i++) {
			if (tischGaeste[i] != null) {
				anzahlGaesteAnTisch++;
			}
		}

		return anzahlGaesteAnTisch;
	}

	public void tischGastHinzufuegen(Gast gast) {
		if (tischVoll() == false) {
			for (int i = 0; i < tischGaeste.length; i++) {
				if (tischGaeste[i] == null) {
					tischGaeste[i] = gast;
					break;
				}
			}
		} else {
			System.out.println("Dieser Tisch ist bereits voll. Bitte wählen Sie einen anderen Tisch.");
		}
	}

	public void tischGaesteBezahlen() {
		for (int i = 0; i < tischGaeste.length; i++) {
			if (tischGaeste[i] != null) {
				tischGaeste[i].gastBezahlt();
			}
		}
	}

	public void tischGaesteLeeren() {
		if (tischGaesteRechnungenBezahlt() == true) {
			for (int i = 0; i < tischGaeste.length; i++) {
				tischGaeste[i] = null;
			}
			tischKellnerEntfernen();
			// GÄSTE LÖSCHEN??? \\
			
			System.out.println("Alle Gäste haben ihre Rechnungen bezahlt. Der Tisch wird freigegeben.");
		} else {
			System.out.println("Es haben noch nicht alle Gäste ihre Rechnung beglichen. Bitte erst bezahlen und dann erneut versuchen.");
		}
	}

	public boolean tischGaesteRechnungenBezahlt() {
		boolean gaesteBezahlt = true;

		for (int i = 0; i < tischGaeste.length; i++) {
			if (tischGaeste[i] != null) {
				if (tischGaeste[i].istBezahlt() == false) {
					gaesteBezahlt = false;
				}
			}
		}

		return gaesteBezahlt;
	}
}