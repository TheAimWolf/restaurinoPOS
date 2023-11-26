package restaurinoPOS;

public class Kellner extends Person
{
	boolean tischzugewiesen = false;
	Tisch zugewiesen;
	
	public Kellner(String vorname, String nachname)
	{
		super(vorname, nachname);
	}
	
	
	
	
	
	
	//getter und setter
	
	public boolean istKellnerTischZugewiesen()
	{
		return tischzugewiesen;
	}
	public void tischZuweisen(Tisch einTisch)
	{
		if(tischzugewiesen == false)
		{
			zugewiesen = einTisch;
		}
		else
		{
			System.out.println("Kellner " + getName() + " ist schon einem Tisch zugewiesen");
		}
	}
	
}
