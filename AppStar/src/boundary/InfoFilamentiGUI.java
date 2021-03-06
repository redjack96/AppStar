package boundary;

import control.HomeController;
import control.InfoFilamentiController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InfoFilamentiGUI implements Initializable {
    @FXML
    private TextField nomeFil;
    @FXML
    private TextField idFil;
    @FXML
    private Button opCentroide;
    @FXML
    private Button opEstensione;
    @FXML
    private Button opNumSeg;
    @FXML
    private TextArea lonCentroide;
    @FXML
    private TextArea latCentroide;
    @FXML
    private TextArea estFilamento;
    @FXML
    private TextArea numSegFilamento;
    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Herschel", "Spitzer");
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button indietro;

    public void istanziaInfoFilamentiGUIFXML(Event e){
        //Lancia l'interfaccia grafica InfoFilamentiGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/InfoFilamentiGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di InfoFilamenti.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        choiceBox.setItems(choiceBoxList);
        choiceBox.setValue("Herschel");
        lonCentroide.setEditable(false);
        latCentroide.setEditable(false);
        estFilamento.setEditable(false);
        numSegFilamento.setEditable(false);

        InfoFilamentiController infoFilamentiController = new InfoFilamentiController();
        // pulsante del calcolo del centroide
        opCentroide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList centroide;
                String  filamento;
                int idfil;
                String id = idFil.getText();
                if (id.equals("")) {
                    idfil = 0;
                } else{
                    try {
                        idfil = Integer.parseInt(id);
                    } catch (java.lang.NumberFormatException j){
                        System.out.println(j.getMessage());
                        idfil = -1;
                    }
                }
                if (!nomeFil.getText().equals("")){
                    filamento = nomeFil.getText();
                } else if(!idFil.getText().equals("")) {
                    filamento = idFil.getText();
                } else filamento = "?";

                centroide = infoFilamentiController.calcolaCentroide(nomeFil.getText(), idfil, choiceBox.getValue());
                lonCentroide.setText(centroide.get(0) + "\nFilamento: " + filamento);
                latCentroide.setText(centroide.get(1) + "\nFilamento: " + filamento);
            }
        });
        // pulsante del calcolo dell'estensione
        opEstensione.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList estensione;
                String filamento;
                int idfil;
                String id = idFil.getText();
                if (id.equals("")) {
                    idfil = 0;
                } else{
                    try {
                        idfil = Integer.parseInt(id);
                    } catch (java.lang.NumberFormatException j){
                        System.out.println(j.getMessage());
                        idfil = -1;
                    }
                }
                if (!nomeFil.getText().equals("")){
                    filamento = nomeFil.getText();
                } else if(!idFil.getText().equals("")) {
                    filamento = idFil.getText();
                } else filamento = "?";

                estensione = infoFilamentiController.calcolaEstensione(nomeFil.getText(), idfil, choiceBox.getValue());
                String result = "Il filamento " + filamento + " si estende per " + estensione.get(0) + " gradi di longitudine e per " + estensione.get(1) +  " gradi di longitudine.";
                estFilamento.setText(result);
            }});

        // pulsante del calcolo del numero dei segmenti
        opNumSeg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String filamento;
                int idfil;
                String id = idFil.getText();
                if (id.equals("")) { // in caso di stringa vuota evita l'errore
                    idfil = 0;
                } else{
                    try {
                    idfil = Integer.parseInt(id);
                    } catch (java.lang.NumberFormatException j){
                        System.out.println(j.getMessage());
                        idfil = -1;
                    }
                }
                if (!nomeFil.getText().equals("")){
                    filamento = nomeFil.getText();
                } else if(!idFil.getText().equals("")) {
                    filamento = idFil.getText();
                } else filamento = "?";

                if (idfil != -1){
                    int num_seg = infoFilamentiController.calcolaNumSeg(nomeFil.getText(), idfil, choiceBox.getValue());
                    String result = "Il filamento " + filamento + " ha " + num_seg + " segmenti. ";
                    numSegFilamento.setText(result);
                } else {
                    String result = "Filamento " + filamento + " NON TROVATO. ";
                    numSegFilamento.setText(result);
                }
            }});

        // torna al menu Home
        indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HomeController homeController = new HomeController();
                homeController.istanziaHomeGUI(event);
            }
        });
    }
}
