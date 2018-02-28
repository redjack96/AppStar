package control;

import boundary.LoginGUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class LoginController extends Application{

    public void start(Stage primaryStage) throws Exception{
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.istanziaLoginGUI(primaryStage);
    }
}
