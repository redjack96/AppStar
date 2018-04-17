package boundary;

import control.HomeController;
import control.ImportaFileSatelliteController;
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
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
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
    private RadioButton contorni;
    @FXML
    private RadioButton filamenti;
    @FXML
    private RadioButton scheletri;
    @FXML
    private RadioButton stelle;
    @FXML
    private TextArea csvInfo;
    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Herschel", "Spitzer");
    @FXML
    private ChoiceBox<String> choiceBox;



    public void istanziaImportaFileSatelliteGUIFXML(Event e){
        //Lancia l'interfaccia grafica ImportaFileSatelliteGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/ImportaFileSatelliteGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di ImportaFileSatellite.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        choiceBox.setItems(choiceBoxList);
        choiceBox.setValue("Herschel");

        String infoFilamenti = "Il file deve contenere le seguenti colonne separate da virgole:\n" +
                "IDFIL,NAME,TOTAL_FLUX,MEAN_DENS,MEAN_TEMP,ELLIPTICITY,CONTRAST,SATELLITE,INSTRUMENT\n" +
                "L'importazione richiede circa 2 secondi.\nInoltre il nome del file deve contenere " + choiceBox.getValue();
        csvInfo.setText(infoFilamenti);

        csvInfo.setEditable(false);

        ImportaFileSatelliteController importaFileSatelliteController = new ImportaFileSatelliteController();

        contorni.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String infoContorni = "Il file deve contenere le seguenti colonne separate da virgole:\n" +
                        "IDFIL,GLON_CONT,GLAT_CONT\n" +
                        "L'importazione richiede 200 secondi nel caso peggiore.\nInoltre il nome del file deve contenere " + choiceBox.getValue();
                csvInfo.setText(infoContorni);
            }
        });

        filamenti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String infoFilamenti = "Il file deve contenere le seguenti colonne separate da virgole:\n" +
                        "IDFIL,NAME,TOTAL_FLUX,MEAN_DENS,MEAN_TEMP,ELLIPTICITY,CONTRAST,SATELLITE,INSTRUMENT\n" +
                        "L'importazione richiede circa 2 secondi.\nInoltre il nome del file deve contenere " + choiceBox.getValue();
                csvInfo.setText(infoFilamenti);
            }
        });

        scheletri.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String infoScheletri = "Il file deve contenere le seguenti colonne separate da virgole:\n" +
                        "IDFIL,IDBRANCH,TYPE,GLON_BR,GLAT_BR,N,FLUX\n" +
                        "L'importazione richiede circa 120 secondi nel caso peggiore.\nInoltre il nome del file deve contenere " + choiceBox.getValue();
                csvInfo.setText(infoScheletri);
            }
        });

        stelle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String infoStelle = "Il file deve contenere le seguenti colonne separate da virgole:\n" +
                        "IDSTAR,NAMESTAR,GLON_ST,GLAT_ST,FLUX_ST,TYPE_ST\n" +
                        "L'importazione richiede circa 7 secondi.\nInoltre il nome del file deve contenere " + choiceBox.getValue();
                csvInfo.setText(infoStelle);
            }
        });
        //TODO: AttenderePopUp: Mostra un popUp mentre l'applicazione importa un file e lo distribuisce nel DB
        //TODO: (opzionale): su AttenderePopUp mostrare percentuale di completamento importazione (righe copiate/righe da copiare*100%)
        importaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) throws NullPointerException{

                String satellite = choiceBox.getValue();
                FileChooser fileChooser = new FileChooser();
                File file;
                Stage stage = new Stage();
                // nel file fxml e' preselezionato il radio button filamenti
                int RB = 2;

                if (contorni.isSelected()){
                    stage.setTitle("Importa file posizioni contorni...");
                    RB = 1;
                }else if (filamenti.isSelected()){
                    stage.setTitle("Importa file dati filamenti ...");
                    RB = 2;
                }else if (scheletri.isSelected()){
                    stage.setTitle("Importa file posizioni scheletro...");
                    RB = 3;
                }else if (stelle.isSelected()){
                    stage.setTitle("Importa file posizioni stelle...");
                    RB = 4;
                }

                try{
                    file = fileChooser.showOpenDialog(stage);
                    System.out.println(RB+file.getPath());
                    importaFileSatelliteController.importaFile(file, RB, satellite);
                }catch (NullPointerException nPE){
                    System.out.println(nPE.getMessage()); // prima senza .getMessage()
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
