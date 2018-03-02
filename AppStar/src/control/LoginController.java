package control;

import boundary.HomeGUI;
import boundary.LoginGUI;
import entity.UtenteAmministratore;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import persistance.UtenteDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController extends Application{

    public void start(Stage primaryStage) throws Exception{
        //metodo della classe astratta Application. Lanciato dalla main()

        LoginGUI loginGUI = new LoginGUI();
        loginGUI.istanziaLoginGUIFXML(primaryStage);
        //istanzia il controller grafico LoginGUI dell'interfaccia grafica LoginGUI.fxml.
    }

    public void istanziaLoginGUI(Event e){
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.istanziaLoginGUIFXML(e);
        //istanzia il controller grafico LoginGUI dell'interfaccia grafica LoginGUI.fxml.
    }

    public void controlloLogin(String userID, String password){
        //Verifica l'esistenza dell'utente avente userID e password come credenziali.

        UtenteRegistrato utente = UtenteConnesso.getInstance(userID, password);
        System.out.println("Benvenuto " + utente.getNome() + " " + utente.getCognome());
    }
}
