package control;

import boundary.InserisciDatiStrumentiGUI;
import entity.UtenteAmministratore;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.event.Event;

public class InserisciDatiStrumentiController {

    public void istanziaInserisciFileDatiSatelliteGUI(Event e){
        InserisciDatiStrumentiGUI inserisciDatiStrumentiGUI = new InserisciDatiStrumentiGUI();
        inserisciDatiStrumentiGUI.istanziaInserisciDatiStrumentiGUIFXML(e);
    }

    public void nuoviDatiStrumento(float banda, String strumento, String satellite){
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        if (utente.isAmministratore()){
            UtenteAmministratore amministratore = new UtenteAmministratore(utente.getNome(), utente.getCognome(),
                    utente.getUserID(), utente.getPassword(), utente.getEmail());
            amministratore.inserisciNuoviDatiStrumento(banda, strumento, satellite);
        }
    }
}
