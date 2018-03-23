package boundary;

import control.HomeController;
import control.InserisciDatiStrumentiController;
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

import java.net.URL;
import java.util.ResourceBundle;

public class InserisciDatiStrumentiGUI implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button indietroButton;
    @FXML
    private Button inserisciButton;
    @FXML
    private TextField banda;
    @FXML
    private TextField strumento;
    @FXML
    private Label campiLabel;

    public void istanziaInserisciDatiStrumentiGUIFXML(Event e){
        //Lancia l'interfaccia grafica InserisciDatiStrumentiGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/InserisciDatiStrumentiGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di InserisciDatiStrumenti.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        campiLabel.setVisible(false);

        InserisciDatiStrumentiController inserisciDatiStrumentiController = new InserisciDatiStrumentiController();

        inserisciButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (banda.getLength() == 0 || strumento.getLength() == 0 ) {
                    campiLabel.setVisible(true);
                }else{
                    String bandaText = banda.getText();
                    String strumentoText = strumento.getText();
                    float bandaFloat = Float.valueOf(bandaText);
                    inserisciDatiStrumentiController.nuoviDatiStrumento(bandaFloat, strumentoText);
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
