package boundary;

import control.HomeController;
import control.InserisciFileDatiSatelliteController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.Period;
import java.util.ResourceBundle;

public class InserisciFileDatiSatelliteGUI implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button indietroButton;
    @FXML
    private Button inserisciButton;
    @FXML
    private TextField nomeAgenzia;
    @FXML
    private TextField nomeSatellite;
    @FXML
    private DatePicker dataInizio;
    @FXML
    private DatePicker dataFine;
    @FXML
    private Label campiLabel;

    public void istanziaInserisciFileDatiSatelliteGUIFXML(Event e){
        //Lancia l'interfaccia grafica InserisciFileDatiSatelliteGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/InserisciFileDatiSatelliteGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di ImportaFileDatiSatellite.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        campiLabel.setVisible(false);

        InserisciFileDatiSatelliteController inserisciFileDatiSatelliteController =
                new InserisciFileDatiSatelliteController();

        inserisciButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nomeAgenzia.getLength()==0 || nomeSatellite.getLength()==0 || dataInizio.getValue() == null) {
                    campiLabel.setVisible(true);
                }else {
                    campiLabel.setVisible(false);
                    System.out.println(dataInizio.getValue());
                    if (dataFine.getValue() != null){
                        Period durata = dataInizio.getValue().until(dataFine.getValue()); //durata != null
                        System.out.println(durata);
                        inserisciFileDatiSatelliteController.nuoviDatiSatellite(nomeAgenzia.getText(),
                                nomeSatellite.getText(), dataInizio.getValue(), durata);
                    }else {
                        inserisciFileDatiSatelliteController.nuoviDatiSatellite(nomeAgenzia.getText(),
                                nomeSatellite.getText(), dataInizio.getValue(), null);
                    }
                    nomeAgenzia.setText(null);
                    nomeSatellite.setText(null);
                    dataInizio.setValue(null);
                    dataFine.setValue(null);
                }
            }
        });

        indietroButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HomeController homeController = new HomeController();
                homeController.istanziaHomeGUI(event);
            }
        });
    }
}
