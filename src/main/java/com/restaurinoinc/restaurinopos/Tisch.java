package com.restaurinoinc.restaurinopos;

/**
 * Tisch im Restaurant
 * Am Tisch sitzen Gäste, diese werden von einem Kellner bedient.
 */
public class Tisch {
	private int tischnummer;
	private final int TISCHGROESSE = 4;
	private Gast[] tischGaeste = new Gast[TISCHGROESSE];

	public Kellner getTischZugewiesenerKellner() {
		return tischZugewiesenerKellner;
	}

	public void setTischZugewiesenerKellner(Kellner tischZugewiesenerKellner) {
		this.tischZugewiesenerKellner = tischZugewiesenerKellner;
	}

	private Kellner tischZugewiesenerKellner;

	/**
	 * Public Konstruktor Klasse Tisch
	 * 
	 * Erzeugen eines Tisches im Restaurant.
	 */
	public Tisch(int pTischnummer) {
		tischnummer = pTischnummer;
	}

	public Integer getTischnummer() {
		return tischnummer;
	}
	
	protected void tischKellnerZuweisen(Kellner kellner) {
		tischZugewiesenerKellner = kellner;
	}

	private void tischKellnerEntfernen() {
		tischZugewiesenerKellner = null;
	}

	public Gast[] getTischGaeste(){
		return tischGaeste;
	}

	protected boolean tischKellnerZugewiesen() {
		if (tischZugewiesenerKellner == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean tischVoll() {
		if (tischAnzahlGaeste() == TISCHGROESSE) {
			return true;
		} else {
			return false;
		}
	}

	public boolean tischLeer() {
		if (tischAnzahlGaeste() == 0) {
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

	public void tischGaesteLeeren() {
		if (tischGaesteRechnungenBezahlt() == true) {
			for (int i = 0; i < tischGaeste.length; i++) {
				if(tischGaeste != null) {
					Restaurant.restaurantGaeste.remove(tischGaeste[i]);
					tischGaeste[i] = null;
				}
			}
			tischKellnerEntfernen();


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