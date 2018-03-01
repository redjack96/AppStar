package control;

import boundary.HomeGUI;
import boundary.RegistraUtenteGUI;
import entity.UtenteAmministratore;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.event.Event;
import persistance.UtenteDao;

import java.sql.SQLException;

public class RegistraUtenteController {

    public void istanziaRegistraUtenteGUI(Event e){
        RegistraUtenteGUI registraUtenteGUI = new RegistraUtenteGUI();
        registraUtenteGUI.istanziaRegistraUtenteGUIFXML(e);
        //istanzia il controller grafico HomeGUI dell'interfaccia grafica HomeGUI.fxml.
    }

    public void registraUtente(String nome, String cognome, String userID, String password, String email,
                               boolean admin){
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        if (utente.isAmministratore()){
            UtenteAmministratore amministratore = new UtenteAmministratore(utente.getNome(), utente.getCognome(),
                    utente.getUserID(), utente.getPassword(), utente.getEmail());
            amministratore.registraNuovoUtente(nome, cognome, userID, password, email, admin);
        }
    }
}
