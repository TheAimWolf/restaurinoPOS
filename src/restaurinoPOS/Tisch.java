package restaurinoPOS;

public class Tisch 
{
	boolean rechnungBezahlt;
	double rechnungsbetrag;
	int personenzahl;
	boolean besetzt;
	boolean hatKellner;
	Gast [] gaesteAmTisch = new Gast[4];
	
	
	
	
	
	
	
	
	
	
	
	
	
	// getters und setters
	
	public boolean isBesetzt()
	{
		return besetzt;
	}
	
	public double getRechnungsbetrag()
	{
		return rechnungsbetrag;
	}
	
	public void addPersonen()
	{
		//TODO
		// gastTischZuweisen() der Klasse Restaurant könnte so mehr objektorientiert gemacht werden
	}
}
