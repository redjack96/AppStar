<<<<<<< HEAD
package boundary;  //Boundary --> interfaccia grafica

import javafx.application.Application;
=======
package boundary;

import control.HomeController;
import control.LoginController;
>>>>>>> Branch_Lorenzo
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
<<<<<<< HEAD
import javafx.stage.Stage;      //Stage e' un pannello
=======
import javafx.stage.Stage;
>>>>>>> Branch_Lorenzo

import java.net.URL;
import java.util.ResourceBundle;

public class LoginGUI implements Initializable {

<<<<<<< HEAD
    @FXML  //Mettili sempre
=======
    @FXML
>>>>>>> Branch_Lorenzo
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
<<<<<<< HEAD
    //Stage e' un pannello
=======

<<<<<<< HEAD
>>>>>>> Branch_Lorenzo
    public void istanziaLoginGUI(Stage Stage) throws Exception{
=======
    public void istanziaLoginGUIFXML(Stage Stage) throws Exception{
>>>>>>> Branch_Lorenzo
        Parent root = FXMLLoader.load(getClass().getResource("/boundary/LoginGUI.fxml"));
        Stage.setTitle("AppStar");
        Stage.setScene(new Scene(root, 800, 450));
        Stage.show();
    }
<<<<<<< HEAD
    //Scrivi sempre!!!
=======

<<<<<<< HEAD
>>>>>>> Branch_Lorenzo
=======
    public void istanziaLoginGUIFXML(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/LoginGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

>>>>>>> Branch_Lorenzo
    public void initialize(URL location, ResourceBundle resources){

        LoginController loginController = new LoginController();

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
<<<<<<< HEAD
<<<<<<< HEAD

=======
                LoginController loginController = new LoginController();
=======
>>>>>>> Branch_Lorenzo
                loginController.controlloLogin(userIDTextField.getText(), passwordPasswordField.getText());
<<<<<<< HEAD
>>>>>>> Branch_Lorenzo
=======
                HomeController homeController = new HomeController();
                homeController.istanziaHomeGUI(event);
>>>>>>> Branch_Lorenzo
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
