package control;

import boundary.ImportaFileSatelliteGUI;
import boundary.successPopUp.FileSatImportPopUp;
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
    //chiamato alla pressione del pulsante IMPORTA! in boundary.ImportaFileSatelliteGUI
    public void importaFile(File csv, int RB, String satellite){
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        int r;
        if (utente.isAmministratore()){
            UtenteAmministratore amministratore = new UtenteAmministratore(utente.getNome(), utente.getCognome(),
                    utente.getUserID(), utente.getPassword(), utente.getEmail());
            r = amministratore.importaFileCSV(csv, RB, satellite);
        } else {
            r = -1;
        }
        if (r == 1){
            boundary.successPopUp.FileSatImportPopUp importSuccess = new boundary.successPopUp.FileSatImportPopUp();
            importSuccess.istanziaFileSatImportPopUpFXML();
        } else{
            System.out.println("Errore importazione in imp");
            boundary.errorPopUp.FileSatImportPopUp importError = new boundary.errorPopUp.FileSatImportPopUp();
            importError.istanziaFileSatImportPopUpFXML();
        }
    }

    //TODO: metodo troncaTabelleImp()
    /* public void troncaTabelleImp(){
    *   //TRUNCATE contorni_imp
    *   //TRUNCATE filamenti_imp
    *   //TRUNCATE scheletri_imp
    *   //TRUNCATE stelle_imp
    *
    *   //execute tutti e 4
    * }
    * */
}
