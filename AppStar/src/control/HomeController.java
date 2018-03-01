package control;

import boundary.HomeGUI;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.event.Event;

public class HomeController {

    public void istanziaHomeGUI(Event e){
        HomeGUI homeGUI = new HomeGUI();
        homeGUI.istanziaHomeGUIFXML(e);
    }

    public boolean verificaAmministratore(){
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        return utente.isAmministratore();
    }

    public void disconnettiUtente(){
        UtenteConnesso.disconnettiUtente();
    }
}
