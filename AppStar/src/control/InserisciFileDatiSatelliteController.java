package control;

import boundary.InserisciFileDatiSatelliteGUI;
import entity.UtenteAmministratore;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.event.Event;
import java.time.LocalDate;
import java.time.Period;

public class InserisciFileDatiSatelliteController {

    public void istanziaInserisciFileDatiSatelliteGUI(Event e){
        InserisciFileDatiSatelliteGUI inserisciFileDatiSatelliteGUI = new InserisciFileDatiSatelliteGUI();
        inserisciFileDatiSatelliteGUI.istanziaInserisciFileDatiSatelliteGUIFXML(e);
    }

    public void nuoviDatiSatellite(String nomeAgenzia, String nomeSatellite, LocalDate dataInizio, Period durata){
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        if (utente.isAmministratore()){
            UtenteAmministratore amministratore = new UtenteAmministratore(utente.getNome(), utente.getCognome(),
                    utente.getUserID(), utente.getPassword(), utente.getEmail());
            amministratore.inserisciNuoviDatiSatellite(nomeAgenzia, nomeSatellite, dataInizio, durata);
        }
    }
}
