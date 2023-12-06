package restaurinoPOS;

/**
 * Tisch im Restaurant
 * Am Tisch sitzen Gäste, diese werden von einem Kellner bedient.
 */
public class Tisch {
	private final int TISCHGROESSE = 4;
	private Gast[] tischGaeste = new Gast[TISCHGROESSE];
	private Kellner tischZugewiesenerKellner;
	private Restaurant restaurant;

	/**
	 * Public Konstruktor Klasse Tisch
	 * 
	 * Erzeugen eines Tisches im Restaurant.
	 */
	public Tisch() {
		restaurant.restaurantTischeHinzufuegen(this);
	}
	
	protected void tischKellnerZuweisen(Kellner kellner) {
		tischZugewiesenerKellner = kellner;
	}

	private void tischKellnerEntfernen() {
		tischZugewiesenerKellner = null;
	}

	protected boolean tischKellnerZugewiesen() {
		if (tischZugewiesenerKellner == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean tischVoll() {
		if (tischAnzahlGaeste() == TISCHGROESSE) {
			return true;
		} else {
			return false;
		}
	}

	private int tischAnzahlGaeste() {
		int anzahlGaesteAnTisch = 0;

		for (int i = 0; i < tischGaeste.length; i++) {
			if (tischGaeste[i] != null) {
				anzahlGaesteAnTisch++;
			}
		}

		return anzahlGaesteAnTisch;
	}

	protected void tischGastHinzufuegen(Gast gast) {
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

	protected void tischGaesteBezahlen() {
		for (int i = 0; i < tischGaeste.length; i++) {
			if (tischGaeste[i] != null) {
				tischGaeste[i].gastBezahlt();
			}
		}
	}

	protected void tischGaesteLeeren() {
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

	protected boolean tischGaesteRechnungenBezahlt() {
		boolean gaesteBezahlt = true;

		for (int i = 0; i < tischGaeste.length; i++) {
			if (tischGaeste[i] != null) {
				if (tischGaeste[i].gastHatBezahlt() == false) {
					gaesteBezahlt = false;
				}
			}
		}

		return gaesteBezahlt;
	}
}