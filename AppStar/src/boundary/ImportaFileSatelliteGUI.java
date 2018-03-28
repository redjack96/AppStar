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
    private TextField nomeSatellite;
    @FXML
    private TextArea csvInfo;
    @FXML
    private Label errorLabel;

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

        String infoContorni = "La tabella deve contenere le seguenti colonne separate da virgole:\n" +
                " IDFIL,GLON_CONT,GLAT_CONT";
        csvInfo.setText(infoContorni);

        errorLabel.setVisible(false);
        //importaButton.setDisable(true);

        /*nomeSatellite.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {
            @Override
            public void handle(InputMethodEvent event) {
                if (nomeSatellite.getText().length() < 3){
                    errorLabel.setVisible(true);
                }else {
                    importaButton.setDisable(false);
                }

            }
        });*/

        csvInfo.setEditable(false);

        ImportaFileSatelliteController importaFileSatelliteController = new ImportaFileSatelliteController();

        contorni.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                csvInfo.setText(infoContorni);
            }
        });

        filamenti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String infoFilamenti = "La tabella deve contenere le seguenti colonne separate da virgole:\n" +
                        " IDFIL,NAME,TOTAL_FLUX,MEAN_DENS,MEAN_TEMP,ELLIPTICITY,CONTRAST,SATELLITE,INSTRUMENT";
                csvInfo.setText(infoFilamenti);
            }
        });

        scheletri.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String infoScheletri = "La tabella deve contenere le seguenti colonne separate da virgole:\n" +
                        " IDFIL,IDBRANCH,TYPE,GLON_BR,GLAT_BR,N,FLUX";
                csvInfo.setText(infoScheletri);
            }
        });

        stelle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String infoStelle = "La tabella deve contenere le seguenti colonne separate da virgole:\n" +
                        " IDSTAR,NAMESTAR,GLON_ST,GLAT_ST,FLUX_ST,TYPE_ST";
                csvInfo.setText(infoStelle);
            }
        });
        //TODO: AttenderePopUp: Mostra un popUp mentre l'applicazione importa un file e lo distribuisce nel DB
        //TODO: (opzionale): su AttenderePopUp mostrare percentuale di completamento importazione (righe copiate/righe da copiare*100%)
        //TODO: ImportatoPopUp: Mostra una schermata popUp(cambia testo AttenderePopUp) se l'importazione avviene correttamente
        //TODO: NonImportatoPopUp: Mostra una schermata popUp se l'importazione fallisce (Possibilmente con l'errore della query)
        importaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file;
                Stage stage = new Stage();
                int RB = 1;

                if (contorni.isSelected()){
                    stage.setTitle("Importa catalogo strutture estese...");
                    RB = 1;
                }else if (filamenti.isSelected()){
                    stage.setTitle("Importa file posizioni contorni...");
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
                    importaFileSatelliteController.importaFile(file, RB);
                }catch (NullPointerException nPE){
                    System.out.println(nPE.getCause());
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
