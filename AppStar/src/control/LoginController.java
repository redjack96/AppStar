package control;

import boundary.LoginGUI;
import entity.UtenteAmministratore;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.application.Application;
import javafx.stage.Stage;
import persistance.UtenteDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController extends Application{

    public void start(Stage primaryStage) throws Exception{
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.istanziaLoginGUI(primaryStage);
    }

    public void controlloLogin(String userID, String password){
        UtenteRegistrato utente = UtenteConnesso.getInstance(userID, password);
        System.out.println("Benvenuto " + UtenteConnesso.getInstance(null, null).getNome() + " " +
                UtenteConnesso.getInstance(null, null).getCognome());
    }

    public void disconnessione(){
        UtenteConnesso.disconnettiUtente();
    }
}
