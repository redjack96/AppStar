package boundary;

import control.HomeController;
import control.ImportaFileSatelliteController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ImportaFileSatelliteGUI implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button indietroButton;
    @FXML
    private Button importaButton;
    @FXML
    private RadioButton importaStruttureEstese;
    @FXML
    private RadioButton importaPosizioniContorni;
    @FXML
    private RadioButton importaPosizioniScheletro;
    @FXML
    private RadioButton importaPosizioniStelle;

    public void istanziaImportaFileSatelliteGUIFXML(Event e){
        //Lancia l'interfaccia grafica ImportaFileSatelliteGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/ImportaFileSatelliteGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di Home.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        ImportaFileSatelliteController importaFileSatelliteController = new ImportaFileSatelliteController();

        importaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = null;
                Stage stage = new Stage();
                int RB = -1;

                if (importaStruttureEstese.isSelected()){
                    stage.setTitle("Importa catalogo strutture estese...");
                    file = fileChooser.showOpenDialog(stage);
                    RB = 1;
                }else if (importaPosizioniContorni.isSelected()){
                    stage.setTitle("Importa file posizioni contorni...");
                    file = fileChooser.showOpenDialog(stage);
                    RB = 2;
                }else if (importaPosizioniScheletro.isSelected()){
                    stage.setTitle("Importa file posizioni scheletro...");
                    file = fileChooser.showOpenDialog(stage);
                    RB = 3;
                }else if (importaPosizioniStelle.isSelected()){
                    stage.setTitle("Importa file posizioni stelle...");
                    file = fileChooser.showOpenDialog(stage);
                    RB = 4;
                }
                importaFileSatelliteController.importaFile(file, RB); //TODO.
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
