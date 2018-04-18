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

    public ArrayList<String> calcolaCentroide(String nomeFil, String idFil, String satellite){

        ArrayList<String> centroide;
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        centroide = utente.calcolaCentroide(nomeFil, idFil, satellite);
        return centroide;
    }
}
