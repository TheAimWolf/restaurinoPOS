package com.restaurinoinc.restaurinopos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HelloController {

    @FXML
    private TableView<Kellner> kellnerTabelle;
    @FXML
    private TableView<Gast> gaesteTabelle;
    @FXML
    private TableView<Tisch> tischTabelle;
    @FXML
    private TableView<Posten> postenTabelle;
    @FXML
    private ComboBox<Gast> gaesteComboBox;
    @FXML
    private ComboBox<Posten> postenComboBox;
    @FXML
    private ChoiceBox<Tisch> tischChoiceBox;
    @FXML
    private Pagination gastRechnungPagination;
    @FXML
    private Button tischFreigebenButton;
    @FXML
    private Label tagesumsatzText;

    @FXML
    public void initialize() {
        tischTabelle.getColumns().clear();
        kellnerTabelle.getColumns().clear();
        gaesteTabelle.getColumns().clear();
        postenTabelle.getColumns().clear();

        StringConverter<Tisch> tischConverter = new StringConverter<Tisch>() {
            @Override
            public String toString(Tisch tisch) {
                // Gib die Tischnummer als String zurück
                return tisch == null ? null : "Tisch " + String.valueOf(tisch.getTischnummer());
            }

            @Override
            public Tisch fromString(String string) {
                // Finde das Tischobjekt anhand der Tischnummer
                return Restaurant.restaurantTische.stream()
                        .filter(tisch -> String.valueOf(tisch.getTischnummer()).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        };

        tischChoiceBox.setItems(FXCollections.observableArrayList(
                Restaurant.restaurantTische.stream()
                        .filter(tisch -> !tisch.tischLeer()) // Filtere die Tische, die nicht leer sind
                        .collect(Collectors.toList())
        ));
        tischChoiceBox.setConverter(tischConverter);

        // Listener für Tisch-Auswahl
        tischChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, alterTisch, neuerTisch) -> {
            if (neuerTisch != null) {
                zeigeGastRechnungen(neuerTisch);
                aktualisiereTischFreigebenButton(neuerTisch);
            }
        });

        //Kellner
        TableColumn<Kellner, String> vornameSpalteKellner = new TableColumn<>("Vorname");
        vornameSpalteKellner.setCellValueFactory(new PropertyValueFactory<>("personVorname"));
        TableColumn<Kellner, String> nachnameSpalteKellner = new TableColumn<>("Nachname");
        nachnameSpalteKellner.setCellValueFactory(new PropertyValueFactory<>("personNachname"));

        kellnerTabelle.getColumns().addAll(vornameSpalteKellner, nachnameSpalteKellner);
        kellnerTabelle.setItems(FXCollections.observableArrayList(Restaurant.restaurantKellner));

        //Posten
        TableColumn<Posten, String> nameSpaltePosten = new TableColumn<>("Bezeichnung");
        nameSpaltePosten.setCellValueFactory(new PropertyValueFactory<>("postenName"));
        TableColumn<Posten, Double> preisSpaltePosten = new TableColumn<>("Preis");
        preisSpaltePosten.setCellValueFactory(new PropertyValueFactory<>("postenPreis"));

        postenTabelle.getColumns().addAll(nameSpaltePosten, preisSpaltePosten);
        postenTabelle.setItems(FXCollections.observableArrayList(Restaurant.restaurantPosten));

        //Gast
        TableColumn<Gast, String> vornameSpalteGast = new TableColumn<>("Vorname");
        vornameSpalteGast.setCellValueFactory(new PropertyValueFactory<>("personVorname"));
        TableColumn<Gast, String> nachnameSpalteGast = new TableColumn<>("Nachname");
        nachnameSpalteGast.setCellValueFactory(new PropertyValueFactory<>("personNachname"));
        TableColumn<Gast, String> tischSpalteGast = new TableColumn<>("Tisch");
        tischSpalteGast.setCellValueFactory(new PropertyValueFactory<>("tischnummer"));

        gaesteTabelle.getColumns().addAll(vornameSpalteGast, nachnameSpalteGast, tischSpalteGast);
        gaesteTabelle.setItems(FXCollections.observableArrayList(Restaurant.restaurantGaeste));

        //Tische
        TableColumn<Tisch, Integer> tischnummerSpalte = new TableColumn<>("Tischnummer");
        tischnummerSpalte.setCellValueFactory(new PropertyValueFactory<>("tischnummer"));
        TableColumn<Tisch, Kellner> kellnerSpalte = new TableColumn<>("Kellner");
        kellnerSpalte.setCellValueFactory(new PropertyValueFactory<>("tischZugewiesenerKellner"));

        kellnerSpalte.setCellFactory(tc -> new TableCell<Tisch, Kellner>() {
            ComboBox<Kellner> combo = new ComboBox<>();

            @Override
            protected void updateItem(Kellner kellner, boolean empty) {
                super.updateItem(kellner, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    combo.setItems(FXCollections.observableArrayList(Restaurant.restaurantKellner));
                    combo.setConverter(new StringConverter<Kellner>() {
                        @Override
                        public String toString(Kellner kellner) {
                            return kellner == null ? "" : kellner.getPersonName();
                        }

                        @Override
                        public Kellner fromString(String string) {
                            // Hier kannst du definieren, wie ein String in ein Kellner-Objekt umgewandelt wird, falls nötig
                            return null;
                        }
                    });
                    combo.setValue(kellner);
                    combo.valueProperty().addListener((obs, oldVal, newVal) -> {
                        Tisch tisch = getTableView().getItems().get(getIndex());
                        tisch.tischKellnerZuweisen(newVal); // Aktualisiere das Tisch-Objekt
                    });
                    setGraphic(combo);
                }
            }
        });

        tischTabelle.getColumns().addAll(tischnummerSpalte, kellnerSpalte);
        tischTabelle.setItems(FXCollections.observableArrayList(Restaurant.restaurantTische));
        // Initialisiere die Gäste-ComboBox
        gaesteComboBox.setItems(FXCollections.observableArrayList(Restaurant.restaurantGaeste));
        gaesteComboBox.setConverter(new StringConverter<Gast>() {
            @Override
            public String toString(Gast gast) {
                return gast == null ? "" : gast.getPersonVorname() + " " + gast.getPersonNachname();
            }

            @Override
            public Gast fromString(String string) {
                // Diese Methode ist für die ComboBox nicht relevant
                return null;
            }
        });
        // Initialisiere die Posten-ComboBox
        postenComboBox.setItems(FXCollections.observableArrayList(Restaurant.restaurantPosten));
        postenComboBox.setConverter(new StringConverter<Posten>() {
            @Override
            public String toString(Posten posten) {
                return posten == null ? "" : posten.getPostenName();
            }

            @Override
            public Posten fromString(String string) {
                // Diese Methode ist für die ComboBox nicht relevant
                return null;
            }
        });
    }
    @FXML
    protected void onKellnerHinzufuegenClick() {
        TextField vorname = new TextField();
        vorname.setPromptText("Vorname");
        GridPane.setConstraints(vorname, 0, 0);

        TextField nachname = new TextField();
        nachname.setPromptText("Nachname");
        GridPane.setConstraints(nachname, 1, 0);

        Button hinzufuegen = new Button("Hinzufügen");
        GridPane.setConstraints(hinzufuegen, 2, 0);

        Stage newWindow = neuesEingabeFenster("Neuen Kellner hinzufügen", vorname, nachname, hinzufuegen);

        hinzufuegen.setOnMouseClicked(mouseEvent -> {
            String vornameText = vorname.getText();
            String nachnameText = nachname.getText();
            System.out.println(vornameText + nachnameText);
            if(!Objects.equals(vornameText, "") && !Objects.equals(nachnameText, "")) {
                Kellner neuerKellner = new Kellner(vornameText, nachnameText);
                Restaurant.restaurantKellner.add(neuerKellner);
                kellnerTabelle.getColumns().clear();
                initialize();
                newWindow.close();
            }else{
                zeigePopUp("Bitte gebe einen Namen an");
            }
        });
    }

    @FXML
    protected void onPostenHinzufuegenClick() {
        TextField name = new TextField();
        name.setPromptText("Bezeichnung");
        GridPane.setConstraints(name, 0, 0);

        TextField preis = new TextField();
        preis.setPromptText("Preis");
        GridPane.setConstraints(preis, 1, 0);

        Button hinzufuegen = new Button("Hinzufügen");
        GridPane.setConstraints(hinzufuegen, 2, 0);

        Stage newWindow = neuesEingabeFenster("Neuen Posten hinzufügen", name, preis, hinzufuegen);

        hinzufuegen.setOnMouseClicked(mouseEvent -> {
            String nameText = name.getText();
            String preisText = preis.getText();

            if(!Objects.equals(nameText, "") && !Objects.equals(preisText, "")) {
                try {
                    double preisDouble = Double.parseDouble(preisText);
                    Posten neuerPosten = new Posten(nameText, preisDouble);
                    Restaurant.restaurantPosten.add(neuerPosten);
                    kellnerTabelle.getColumns().clear();
                    initialize();
                    newWindow.close();
                } catch (NumberFormatException e) {
                    zeigePopUp("Bitte gebe einen gültigen Preis ein");
                }
            } else {
                zeigePopUp("Bitte gebe einen Namen und einen Preis an");
            }
        });
    }

    @FXML
    protected void onGastHinzufuegenClick() {
        TextField vorname = new TextField();
        vorname.setPromptText("Vorname");
        GridPane.setConstraints(vorname, 0, 0);

        TextField nachname = new TextField();
        nachname.setPromptText("Nachname");
        GridPane.setConstraints(nachname, 1, 0);

        ComboBox<Tisch> tischComboBox = new ComboBox<>();
        tischComboBox.setCellFactory(lv -> new ListCell<Tisch>() {
            @Override
            protected void updateItem(Tisch tisch, boolean empty) {
                super.updateItem(tisch, empty);
                setText(empty ? null : "Tisch Nr. " + tisch.getTischnummer());
            }
        });
        tischComboBox.setButtonCell(new ListCell<Tisch>() {
            @Override
            protected void updateItem(Tisch tisch, boolean empty) {
                super.updateItem(tisch, empty);
                setText(empty ? null : "Tisch Nr. " + tisch.getTischnummer());
            }
        });

        for (Tisch tisch : Restaurant.restaurantTische) {
            if (!tisch.tischVoll()) {
                tischComboBox.getItems().add(tisch);
            }
        }
        GridPane.setConstraints(tischComboBox, 2, 0);

        Button hinzufuegen = new Button("Hinzufügen");
        GridPane.setConstraints(hinzufuegen, 3, 0);

        Stage newWindow = neuesEingabeFenster("Neuen Gast hinzufügen", vorname, nachname, tischComboBox, hinzufuegen);

        hinzufuegen.setOnMouseClicked(mouseEvent -> {
            String vornameText = vorname.getText();
            String nachnameText = nachname.getText();
            Tisch ausgewaehlterTisch = tischComboBox.getSelectionModel().getSelectedItem();
            System.out.println(vornameText + nachnameText);
            if(!Objects.equals(vornameText, "") && !Objects.equals(nachnameText, "") && ausgewaehlterTisch != null) {
                Gast gast = new Gast(vornameText, nachnameText, ausgewaehlterTisch);
                Restaurant.restaurantGaeste.add(gast);
                gaesteTabelle.getColumns().clear();
                initialize();
                newWindow.close();
            } else {
                zeigePopUp("Bitte gebe einen Namen und wähle einen Tisch aus");
            }
        });
    }

    @FXML
    protected void onTischHinzufuegenClick() {
        TextField tischnummer = new TextField();
        tischnummer.setPromptText("Tischnummer");
        GridPane.setConstraints(tischnummer, 0, 0);

        Button hinzufuegen = new Button("Hinzufügen");
        GridPane.setConstraints(hinzufuegen, 1, 0);

        Stage newWindow = neuesEingabeFenster("Neuen Kellner hinzufügen", tischnummer, hinzufuegen);

        hinzufuegen.setOnMouseClicked(mouseEvent -> {
            int tischnummerText = 0;
            try{
                tischnummerText = Integer.parseInt(tischnummer.getText());
                int finalTischnummerText = tischnummerText;
                boolean tischnummerVergeben = Restaurant.restaurantTische.stream().anyMatch(restaurantTisch -> restaurantTisch.getTischnummer() == finalTischnummerText);
                if(tischnummerVergeben){
                    zeigePopUp("Diese Nummer ist bereits vergeben");
                }else{
                    Tisch tisch = new Tisch(tischnummerText);
                    Restaurant.restaurantTische.add(tisch);
                    Restaurant.restaurantTische.forEach(moin -> {
                        System.out.println(moin.getTischnummer());
                    });
                    initialize();
                    newWindow.close();
                }
            }
            catch (NumberFormatException err){
                zeigePopUp("Bitte eine Zahle eingeben");
            }
        });
    }

    @FXML
    protected void onBestellenButtonClick() {
        Gast ausgewaehlterGast = gaesteComboBox.getSelectionModel().getSelectedItem();
        Posten ausgewaehlterPosten = postenComboBox.getSelectionModel().getSelectedItem();

        if (ausgewaehlterGast != null && ausgewaehlterPosten != null) {
            if(ausgewaehlterGast.getTisch().tischKellnerZugewiesen()) {
                ausgewaehlterGast.gastBestellt(ausgewaehlterPosten);
                initialize();
                zeigePopUp("Der Kunde "+ ausgewaehlterGast.getPersonName() + " hat " + ausgewaehlterPosten.getPostenName() + " bestellt.");
            } else {
                zeigePopUp("Dem Tisch " + ausgewaehlterGast.getTisch().getTischnummer() + " ist aktuell kein Kellner zugewiesen. Bitte Kellner Tisch zuweisung überprüfen");
            }
            // Weitere Aktionen, wie das Aktualisieren der Ansicht oder das Anzeigen einer Bestätigung
        } else {
            zeigePopUp("Bitte wähle einen Gast und einen Posten aus");
        }
    }

    protected Stage neuesEingabeFenster(String fensterTitel, Node... elemente){
        // Neues Stage-Objekt für das neue Fenster
        Stage newWindow = new Stage();

        // Inhalt für das neue Fenster
        GridPane secondaryLayout = new GridPane();
        secondaryLayout.setPadding(new Insets(10));
        secondaryLayout.setVgap(10);
        secondaryLayout.setHgap(10);
        secondaryLayout.getChildren().addAll(elemente);
        Scene secondScene = new Scene(secondaryLayout, 450, 50);

        newWindow.setScene(secondScene);
        // Titel für das neue Fenster setzen
        newWindow.setTitle(fensterTitel);

        // Das Fenster anzeigen
        newWindow.show();
        return newWindow;
    }

    protected void zeigePopUp(String fehler){
        Stage fehlerFenster = new Stage();
        VBox layout = new VBox();
        Text fehlerText = new Text(fehler);
        layout.getChildren().add(fehlerText);
        Scene secondScene = new Scene(layout, 450, 50);

        fehlerFenster.setScene(secondScene);

        // Titel für das neue Fenster setzen
        fehlerFenster.setTitle("Info");

        fehlerFenster.show();
    }

    private void zeigeGastRechnungen(Tisch tisch) {
        Gast[] gaeste = tisch.getTischGaeste(); // Methode, um Gäste eines Tisches zu erhalten
        gastRechnungPagination.setPageCount(tisch.tischAnzahlGaeste());
        gastRechnungPagination.setPageFactory(pageIndex -> erstelleRechnungsSeite(gaeste[pageIndex], tisch));
    }

    private VBox erstelleRechnungsSeite(Gast gast, Tisch tisch) {
        VBox vbox = new VBox();
        vbox.setSpacing(5); // Setze Abstand zwischen Elementen

        Label rechnungHeader = new Label("Rechnungsdetails für " + gast.getPersonName());
        vbox.getChildren().add(rechnungHeader);

        ScrollPane scrollPane = new ScrollPane();
        VBox postenListe = new VBox();
        postenListe.setSpacing(2); // Setze Abstand zwischen Posten

        // Füge die Posten zur VBox hinzu
        List<Posten> gastBestellungen = gast.getGastBestellungen();
        for (Posten posten : gastBestellungen) {
            Label postenLabel = new Label(posten.getPostenName() + " - " + posten.getPostenPreis() + "€");
            postenListe.getChildren().add(postenLabel);
        }

        scrollPane.setContent(postenListe);
        vbox.getChildren().add(scrollPane);

        Label rechnungsSumme = new Label(String.valueOf(gast.gastBestellungenGetSumme()) + "€");
        vbox.getChildren().add(rechnungsSumme);

        Button bezahlenButton = new Button("Bezahlen");
        bezahlenButton.setOnAction(event -> {
            gast.gastBezahlt();
            postenListe.getChildren().clear();
            rechnungsSumme.setText(String.valueOf(gast.gastBestellungenGetSumme()) + "€");
            bezahlenButton.setDisable(gast.gastHatBezahlt());
            aktualisiereTischFreigebenButton(tisch);
            tagesUmsatzAktualisieren();
        });
        bezahlenButton.setDisable(gast.gastHatBezahlt());

        vbox.getChildren().add(bezahlenButton);

        return vbox;
    }
    @FXML
    protected void onFreigebenButtonClick() {
        Tisch ausgewaehlterTisch = tischChoiceBox.getSelectionModel().getSelectedItem();
        if (ausgewaehlterTisch != null) {
            ausgewaehlterTisch.tischGaesteLeeren(); // Ruft die tischFreigeben Methode für den ausgewählten Tisch auf
            // Aktualisiere die ChoiceBox und die Tischansicht
            tischChoiceBox.setItems(FXCollections.observableArrayList(
                    Restaurant.restaurantTische.stream()
                            .filter(tisch -> !tisch.tischLeer())
                            .collect(Collectors.toList())
            ));
            initialize();

            // Weitere Aktionen, wie Benachrichtigung an den Benutzer, dass der Tisch freigegeben wurde
            // ...
        } else {
            zeigePopUp("Bitte wähle zuerst einen Tisch aus.");
        }
    }

    @FXML
    protected void onKassenabschlussClick(){
        try {
            Restaurant.restaurantClose();
        } catch (NotYetPaidException | IOException e) {
            zeigePopUp(e.getMessage());
        }
    }

    private void tagesUmsatzAktualisieren(){
        tagesumsatzText.setText(String.valueOf(Restaurant.getGesamtumsatzAbrechnung()) + "€");
    }


    private void aktualisiereTischFreigebenButton(Tisch tisch) {
        tischFreigebenButton.setDisable(!tisch.tischGaesteRechnungenBezahlt());
    }
}