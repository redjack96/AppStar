package boundary;

import control.HomeController;
import control.RegistraUtenteController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistraUtenteGUI implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label campiLabel;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField userID;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private RadioButton notAdmin;
    @FXML
    private RadioButton admin;
    @FXML
    private Button registraButton;
    @FXML
    private Button indietroButton;

    public void istanziaRegistraUtenteGUIFXML(Event e){
        //Lancia l'interfaccia grafica RegistraUtente.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/RegistraUtenteGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di RegistraUtente.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources){

        campiLabel.setVisible(false);

        RegistraUtenteController registraUtenteController = new RegistraUtenteController();
        //TODO: NuovoUtenteRegistratoPopUP: Mostrare un pop up se il nuovo utente e' registrato correttamente.
        registraButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nome.getLength()==0 || cognome.getLength()==0 || userID.getLength()<6 || password.getLength()<6 ||
                        email.getLength()==0){
                    campiLabel.setVisible(true);
                }else {
                    if (notAdmin.isSelected()){
                        registraUtenteController.registraUtente(nome.getText(), cognome.getText(), userID.getText(),
                                password.getText(), email.getText(), false);
                        campiLabel.setVisible(false);
                    }else if (admin.isSelected()){
                        registraUtenteController.registraUtente(nome.getText(), cognome.getText(), userID.getText(),
                                password.getText(), email.getText(), true);
                        campiLabel.setVisible(false);
                    }
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
