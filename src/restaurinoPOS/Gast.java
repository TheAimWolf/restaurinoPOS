package restaurinoPOS;

public class Gast extends Person
{
	boolean hatTisch;
	int gruppenzahl; //dadurch geht man sicher, dass Pers aus gleicher Gruppe zu gleichem Tisch kommen
	
	
	
	public Gast(String vorname, String nachname, int gruppenzahl)
	{
		super(vorname, nachname);
		hatTisch = false;
		this.gruppenzahl = gruppenzahl;
	}
		
		
		
		
		
		
		
		
		
		
		
		
		//Methoden
	
		public void bestellen(Bestellung eineBestellung)
		{
			// TODO
		}
		
		public void tischVerlassen()
		{
			//TODO
			// Der Gast muss aus der LinkedList im Restaurant gelöscht werden
			// die Attribute der Klase Tisch müssen auf false ggesetzt werden
			// Der Kellner muss wieder auf verfügbar gesetzt werden
			// Es muss vorher schon bezahlt worden sein
			// Anzahl Gäste auch --
		}
		
		public void bezahlen()
		{
			//TODO
			// Speisen und Getränke werden dargestellt und Summe ermittelt
			// in der Klasse Tisch gibt es extra dafür 2 Rechnungsattribute
		}
	
}
