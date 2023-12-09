package com.restaurinoinc.restaurinopos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant, Programm wird ausgeführt
 */
public class Restaurant extends Application {

	private static File gesamtumsatzFile;
	private static double gesamtumsatzAbrechnung;
	public static List<Tisch> restaurantTische = new ArrayList<>();
	public static List<Kellner> restaurantKellner = new ArrayList<>();
	public static List<Gast> restaurantGaeste = new ArrayList<>();
	public static List<Posten> restaurantPosten = new ArrayList<>();


	public static void main(String[] args) {

		Posten schnitzelMitPommes = new Posten("Schnitzel mit Pommes", 9.9);
		Posten grosserSalat = new Posten("Großer Salat", 6.8);
		Posten pizzaHawaii = new Posten("Pizza Hawaii", 5.9);
		Posten forelleMuellerin = new Posten("Forelle Müllerin", 12.5);
		Posten getraenkKlein = new Posten("Getränk klein", 2.1);
		Posten getraenkGross = new Posten("Getränk groß", 3.9);

		restaurantPosten.add(schnitzelMitPommes);
		restaurantPosten.add(grosserSalat);
		restaurantPosten.add(pizzaHawaii);
		restaurantPosten.add(forelleMuellerin);
		restaurantPosten.add(getraenkKlein);
		restaurantPosten.add(getraenkGross);

		Kellner kellner = new Kellner("Karsten", "Stahl");
		restaurantKellner.add(kellner);
		Tisch tisch = new Tisch(1);
		restaurantTische.add(tisch);

		tisch.tischKellnerZuweisen(kellner);

		Gast gast1 = new Gast("Max", "Mustermann", tisch);
		Gast gast2 = new Gast("Tom", "Riddle", tisch);
		restaurantGaeste.add(gast1);
		restaurantGaeste.add(gast2);
		launch();
		try{
			restaurantClose();

		} catch (NotYetPaidException | IOException e) {
			System.out.println("Ein unerwarteter Fehler ist aufgetreten");
		}
	}

	public static void restaurantClose() throws NotYetPaidException, IOException{

		for (int i = 0; i < restaurantTische.size(); i++) {
			if (restaurantTische.get(i).tischGaesteRechnungenBezahlt() == false) {
				throw new NotYetPaidException();
			}
		}

		try {
			restaurantGesamtumsatz();
		}
		catch (IOException e) {
			throw e;
		}
		System.exit(0);

	}

	public static double getGesamtumsatzAbrechnung(){
		return gesamtumsatzAbrechnung;
	}

	private static void restaurantGesamtumsatz() throws IOException {
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
		}
	}

	private static void restaurantSchreibeDatei(String string) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(gesamtumsatzFile, true));
		writer.write(string);
		writer.newLine();
		writer.close();
	}

	public static void restaurantGesamtumsatzHinzufuegen(Double umsatz) {
		gesamtumsatzAbrechnung = gesamtumsatzAbrechnung + umsatz;
	}

	private static double restaurantGesamtumsatzGet() {
		return gesamtumsatzAbrechnung;
	}


	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(Restaurant.class.getResource("hello-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
		stage.setTitle("Restaurino POS");
		stage.setScene(scene);
		stage.show();
	}
}