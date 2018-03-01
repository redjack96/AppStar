package control;

import boundary.HomeGUI;
import boundary.LoginGUI;
<<<<<<< HEAD
import javafx.application.Application;
import javafx.stage.Stage;
=======
import entity.UtenteAmministratore;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import persistance.UtenteDao;

import java.sql.SQLException;
import java.util.ArrayList;
>>>>>>> Branch_Lorenzo

public class LoginController extends Application{

    public void start(Stage primaryStage) throws Exception{
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.istanziaLoginGUIFXML(primaryStage);
    }
<<<<<<< HEAD
=======

    public void istanziaLoginGUI(Event e){
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.istanziaLoginGUIFXML(e);
    }

    public void controlloLogin(String userID, String password){
        UtenteRegistrato utente = UtenteConnesso.getInstance(userID, password);
        System.out.println("Benvenuto " + utente.getNome() + " " + utente.getCognome());
    }
>>>>>>> Branch_Lorenzo
}
