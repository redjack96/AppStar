package control;

import boundary.RicercaStelleInFilamentoGUI;
import entity.Stella;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class RicercaStelleInFilamentoController {

    public void istanziaRicercaStelleInFilamentoGUI(Event e){
        RicercaStelleInFilamentoGUI ricercaStelleInFilamentoGUI = new RicercaStelleInFilamentoGUI();
        ricercaStelleInFilamentoGUI.istanziaRicercaStelleInFilamentoGUIFXML(e);
    }

    public ArrayList<Float> cercaInFilamento(ObservableList<Stella> stella, TableView tableView, TableColumn id,
                                             TableColumn nameStar, TableColumn glon, TableColumn glat,
                                             TableColumn flux, TableColumn type, int idFil, String satellite){
        ArrayList<Float> arrayList;
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        arrayList = utente.cercaInFilamento(stella, tableView, id, nameStar, glon, glat, flux, type, idFil, satellite);
        return arrayList;
    }
}
