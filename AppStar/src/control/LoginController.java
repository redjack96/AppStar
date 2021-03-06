package control;

import boundary.LoginGUI;
import boundary.errorPopUp.LoginPopUp;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistence.Connessione;

import java.sql.SQLException;

public class LoginController extends Application{

    public void start(Stage primaryStage) throws Exception{
        //metodo della classe astratta Application.

        LoginGUI loginGUI = new LoginGUI();
        loginGUI.istanziaLoginGUIFXML(primaryStage);
        //istanzia il controller grafico LoginGUI dell'interfaccia grafica LoginGUI.fxml.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            try{
                Connessione.CONN.close();
                System.out.print("Connessione al database chiusa. AppStar Terminata.");
            }catch(java.lang.NullPointerException | SQLException e){
                    System.out.println("AppStar Terminata");
            }}
        });

    }

    public void istanziaLoginGUI(Event e){
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.istanziaLoginGUIFXML(e);
        //istanzia il controller grafico LoginGUI dell'interfaccia grafica LoginGUI.fxml.
    }

    public boolean controlloLogin(String userID, String password){
        //Verifica l'esistenza dell'utente avente userID e password come credenziali.

        UtenteRegistrato utente = UtenteConnesso.getInstance(userID, password);
        if (utente == null){
            LoginPopUp loginPopUp = new LoginPopUp();
            loginPopUp.istanziaLoginPopUpFXML();
            System.out.println("Utente non riconosciuto!");
            return false;
        }else {
            System.out.println("Benvenuto " + utente.getNome() + " " + utente.getCognome());
            return true;
        }


    }
}
