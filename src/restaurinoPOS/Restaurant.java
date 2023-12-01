package restaurinoPOS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {

	private static File gesamtumsatzFile;
	private static double gesamtumsatzAbrechnung;
	private static List<Tisch> restaurantTische = new ArrayList<>();

	public static void main(String[] args) {
		Posten pizza = new Posten("Pizza", 2.90);
		Posten cola = new Posten("Cola", 28.20);

		Kellner kellner = new Kellner("A", "B");
		Tisch tisch = new Tisch();

		tisch.tischKellnerZuweisen(kellner);

		Gast gast1 = new Gast("Gast", "1", tisch);
		Gast gast2 = new Gast("Gast", "2", tisch);

		gast1.gastBestellt(cola);
		gast1.gastBestellt(pizza);

		gast2.gastBestellt(cola);

		tisch.tischGaesteBezahlen();
		tisch.tischGaesteLeeren();

		restaurantClose();
	}

	private static void restaurantClose() {
		boolean tischeBezahlt = true;

		for (int i = 0; i < restaurantTische.size(); i++) {
			if (restaurantTische.get(i).tischGaesteRechnungenBezahlt() == false) {
				tischeBezahlt = false;
				System.out.println("Tisch " + i + " hat nicht bezahlt. Programm wird nicht beendet. Bitte erst alle Tische schließen!");
			}
		}

		if (tischeBezahlt == true) {
			if (restaurantGesamtumsatz() == false) {
				System.exit(0);
			} else {
				System.out.println("Es ist ein Fehler aufgetreten. Das Programm wird nicht beendet. Bitte Abrechnung erneut ausführen.");
			}
		}
	}

	private static boolean restaurantGesamtumsatz() {
		boolean fehler = false;

		DateTimeFormatter dateTimeFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
		DateTimeFormatter dateTimeText = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		LocalDateTime dateTimeNow = LocalDateTime.now();

		String gesamtumsatzDateiname = "Abrechnung_" + dateTimeFile.format(dateTimeNow) + ".txt";
		gesamtumsatzFile = new File(gesamtumsatzDateiname);

		if (gesamtumsatzFile.exists() == false) {
			restaurantSchreibeDatei("===ABRECHNUNG===");
			restaurantSchreibeDatei("Abrechungszeit: " + dateTimeText.format(dateTimeNow));
			restaurantSchreibeDatei("Abrechnung erfolgreich.");
			restaurantSchreibeDatei("Kasse wird geschlossen.");
			restaurantSchreibeDatei("Gesamtumsatz: " + restaurantGesamtumsatzGet());
			restaurantSchreibeDatei("Programm wird beendet. Auf Wiedersehen!");
		} else {
			System.out.println("Datei " + gesamtumsatzDateiname + "existiert bereits! Bitte Datei löschen.");
			fehler = true;
		}

		return fehler;
	}

	private static void restaurantSchreibeDatei(String string) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(gesamtumsatzFile, true))) {
			writer.write(string);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void restaurantGesamtumsatzHinzufuegen(Double umsatz) {
		gesamtumsatzAbrechnung = gesamtumsatzAbrechnung + umsatz;
	}

	private static double restaurantGesamtumsatzGet() {
		return gesamtumsatzAbrechnung;
	}

	public void restaurantTischeHinzufuegen(Tisch tisch) {
		restaurantTische.add(tisch);
	}

}