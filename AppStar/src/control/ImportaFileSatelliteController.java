package control;

import boundary.ImportaFileSatelliteGUI;
import javafx.event.Event;

import java.io.File;

public class ImportaFileSatelliteController {

    public void istanziaImportaFileSatelliteGUI(Event e){
        ImportaFileSatelliteGUI importaFileSatelliteGUI = new ImportaFileSatelliteGUI();
        importaFileSatelliteGUI.istanziaImportaFileSatelliteGUIFXML(e);
    }

    public void importaFile(File csv, int RB){
        //TODO: Importazione del file in formato csv.
    }
}
