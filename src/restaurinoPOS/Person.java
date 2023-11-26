package restaurinoPOS;

public class Person
{
	private String personVorname;
	private String personNachname;
	
	public Person(String vorname, String nachname)
	{
		// TODO: VORNAME, NACHNAME LENGTH > 0 \\
		this.personVorname = vorname;
		this.personNachname = nachname;
	}
	
	public String getPersonName()
	{
		return personVorname + " " + personNachname;
	}
}