package control;

import boundary.InfoFilamentiGUI;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.event.Event;

import java.math.BigDecimal;
import java.util.ArrayList;

public class InfoFilamentiController {

    public void istanziaInfoFilamentiGUI(Event e){
        InfoFilamentiGUI infoFilamentiGUI = new InfoFilamentiGUI();
        infoFilamentiGUI.istanziaInfoFilamentiGUIFXML(e);
    }

    public ArrayList calcolaCentroide(String nomeFil, int idFil, String satellite){

        ArrayList centroide;
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        centroide = utente.calcolaCentroide(nomeFil, idFil, satellite);
        return centroide;
    }

    public ArrayList calcolaEstensione(String nomeFil, int idFil, String satellite){

        ArrayList estensione;
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        estensione = utente.calcolaEstensione(nomeFil, idFil, satellite);
        return estensione;
    }
}
