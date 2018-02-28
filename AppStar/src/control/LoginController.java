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
        System.out.println(UtenteConnesso.getInstance(null, null).getNome() +
                UtenteConnesso.getInstance(null, null).getCognome());
        /*try{
            ArrayList<String> infoUtente = UtenteDao.controlloAccount(userID, password);
            if (infoUtente.get(5).equals("amministratore")){
                UtenteAmministratore utenteAmministratore = new UtenteAmministratore(infoUtente.get(0),
                        infoUtente.get(1), infoUtente.get(2), infoUtente.get(3), infoUtente.get(4));
            }else if (infoUtente.get(5).equals("notAmministratore")){
                UtenteRegistrato utenteRegistrato = new UtenteAmministratore(infoUtente.get(0),
                        infoUtente.get(1), infoUtente.get(2), infoUtente.get(3), infoUtente.get(4));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }*/
    }
}
