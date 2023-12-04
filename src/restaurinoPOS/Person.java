package restaurinoPOS;

/**
 * Person im Restaurant
 * Stellt Methoden für Klassen Kellner und Gast bereit.
 */
public class Person {
	private String personVorname;
	private String personNachname;

	/**
	 * Public Konstruktor Klasse Person
	 * 
	 * @param vorname Zum Anlegen einer Person wird ein ein Vorname benötigt.
	 * @param nachname Zum Anlegen einer Person wird ein ein Nachname benötigt.
	 */
	public Person(String vorname, String nachname) {
		if (vorname.length() > 0 && nachname.length() > 0) {
			this.personVorname = vorname;
			this.personNachname = nachname;
		} else {
			System.out.println("Person muss einen gültigen Namen haben!");
		}
	}

	protected String getPersonName() {
		return personVorname + " " + personNachname;
	}
}