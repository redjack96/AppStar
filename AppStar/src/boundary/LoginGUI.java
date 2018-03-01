package boundary;

import control.HomeController;
import control.LoginController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginGUI implements Initializable {

    @FXML
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

    public void istanziaLoginGUIFXML(Stage Stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/boundary/LoginGUI.fxml"));
        Stage.setTitle("AppStar");
        Stage.setScene(new Scene(root, 800, 450));
        Stage.show();
    }

    public void istanziaLoginGUIFXML(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/LoginGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources){

        LoginController loginController = new LoginController();

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginController.controlloLogin(userIDTextField.getText(), passwordPasswordField.getText());
                HomeController homeController = new HomeController();
                homeController.istanziaHomeGUI(event);
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
