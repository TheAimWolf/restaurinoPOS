package restaurinoPOS;
import java.util.LinkedList;
import java.util.ListIterator;

public class Restaurant 
{
	int anzahlGaeste;
	int warteNr;
	
	//Queue, um zu sehen, welche Gäste im Wartebereich stehen. Können wenn sie gehen gelöscht werden
	LinkedList <Person> gaesteImRestaurant = new LinkedList<>();
	Kellner [] alleKellner = new Kellner[4];
	Tisch [] alleTische = new Tisch[6];
	
	//Methoden
	public void createPersonengruppe(int personenzahl)
	{
		for(int i = 0; i < personenzahl; i++)
		{
			createGast("EingegebenerVorname", "EingegebenerNachname");
		}
		
		gastTischZuweisen();
		kellnerUpdate();
	}
	
	public void createGast(String vorname, String nachname)
	{
		gaesteImRestaurant.add(new Gast(vorname, nachname, warteNr));
		anzahlGaeste++;
	}
	
	public Kellner freienKellnerAussuchen()
	{
		for(int i = 0; i < alleKellner.length; i++)
		{
			if(alleKellner[i].tischZugewiesen == false)
			{
				return alleKellner[i];
			}
		}
		return null;
	}
	
	//Gast wird per Zahl auf Bildschirm direkt einem Tisch zugewiesen (digitale 2 auf Screen)
	// Ich habe mir noch nichts überlegt, was ist, sollten alle Tische belegt sein
	public void gastTischZuweisen()
	{
		for(int i = 0; i < alleTische.length; i++)
		{
			if(alleTische[i].isBesetzt() == false)
			{
				ListIterator iterator = gaesteImRestaurant.listIterator();
				int j = 0;
				
				while(iterator.hasNext())
				{
					Gast aktGast = (Gast) iterator.next();
					if(aktGast.gruppenzahl == warteNr)
					{
						alleTische[i].gaesteAmTisch[j] = aktGast;
						j++;
					}
				}
				alleTische[i].besetzt = true;
			}
		}
	}
	
	public void kellnerUpdate()
	{
		Kellner aktKellner = freienKellnerAussuchen();
		
		//Was passiert, wenn kein freier Kellner verfügbar?
		for(int i = 0; i < alleTische.length; i++)
		{
			if(alleTische[i].isBesetzt() == true && alleTische[i].hatKellner == false)
			{
				aktKellner.tischZuweisen(alleTische[i]);
				alleTische[i].hatKellner = true;
				aktKellner.tischZugewiesen = true;
			}
		}
	}
}