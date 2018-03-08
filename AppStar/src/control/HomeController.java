package control;

import boundary.HomeGUI;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.event.Event;

public class HomeController {

    public void istanziaHomeGUI(Event e){
        HomeGUI homeGUI = new HomeGUI();
        homeGUI.istanziaHomeGUIFXML(e);
        //istanzia il controller grafico HomeGUI dell'interfaccia grafica HomeGUI.fxml.
    }

    public UtenteRegistrato getUtente(){
        //Ritorna utente che contiene l'istanza dell'utente connesso attualmente.
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        return utente;
    }

    public boolean verificaAmministratore(){
        //Salva in utente l'istanza generata dalla UtenteConnesso (Singleton) e restituisce un booleano (isAdmin).
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        return utente.isAmministratore();
    }

    public void disconnettiUtente(){
        //Assegna il valore null all'istanza della classe UtenteConnesso (Singleton).
        UtenteConnesso.disconnettiUtente();
    }
}
