package control;

import boundary.ImportaFileSatelliteGUI;
import entity.UtenteAmministratore;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.event.Event;

import java.io.File;

public class ImportaFileSatelliteController {

    public void istanziaImportaFileSatelliteGUI(Event e){
        ImportaFileSatelliteGUI importaFileSatelliteGUI = new ImportaFileSatelliteGUI();
        importaFileSatelliteGUI.istanziaImportaFileSatelliteGUIFXML(e);
    }

    public void importaFile(File csv, int RB){
        //TODO: Importazione del file in formato csv.
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        if (utente.isAmministratore()){
            UtenteAmministratore amministratore = new UtenteAmministratore(utente.getNome(), utente.getCognome(),
                    utente.getUserID(), utente.getPassword(), utente.getEmail());
            amministratore.importaFileCSV(csv, RB);
        }
    }
}
