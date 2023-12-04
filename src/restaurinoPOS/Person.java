package restaurinoPOS;

public class Person {
	private String personVorname;
	private String personNachname;

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