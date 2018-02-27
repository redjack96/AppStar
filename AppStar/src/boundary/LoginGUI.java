package boundary;  //Boundary --> interfaccia grafica

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;      //Stage e' un pannello

import java.net.URL;
import java.util.ResourceBundle;

public class LoginGUI implements Initializable {

    @FXML  //Mettili sempre
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField userIDTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button chiudiButton;
    //Stage e' un pannello
    public void istanziaLoginGUI(Stage Stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/boundary/LoginGUI.fxml"));
        Stage.setTitle("AppStar - Login");
        Stage.setScene(new Scene(root, 800, 450));
        Stage.show();
    }
    //Scrivi sempre!!!
    public void initialize(URL location, ResourceBundle resources){

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        chiudiButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }
}
