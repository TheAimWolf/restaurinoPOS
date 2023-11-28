package restaurinoPOS;

public class Restaurant {

	public static void main(String[] args) {
        Posten pizza = new Posten("Pizza", 2.90);
        Posten cola = new Posten("Cola", 28.20);
		
		Kellner kellner = new Kellner("A", "B");
        Tisch tisch = new Tisch();
        
        tisch.tischKellnerZuweisen(kellner);
        
        Gast gast1 = new Gast("Gast", "1", tisch);
        Gast gast2 = new Gast("Gast", "2", tisch);
        Gast gast3 = new Gast("Gast", "3", tisch);
        
        gast1.gastBestellt(cola);
        gast1.gastBestellt(pizza);
        
        tisch.tischGaesteBezahlen();
        tisch.tischGaesteLeeren();
    }

}