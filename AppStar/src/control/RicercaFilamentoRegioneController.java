package control;

import boundary.RicercaFilamentoRegioneGUI;
import entity.Filamento;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RicercaFilamentoRegioneController {

    public void istanziaRicercaFilamentoRegioneGUI(Event e){
        RicercaFilamentoRegioneGUI ricercaFilamentoRegioneGUI = new RicercaFilamentoRegioneGUI();
        ricercaFilamentoRegioneGUI.istanziaRicercaFilamentoRegioneGUIFXML(e);
    }

    public void cercaInRegione(ObservableList<Filamento> filamento, TableView tableView, TableColumn id, TableColumn
            nome, TableColumn satellite, TableColumn numSeg, float lungh, float centLon, float
                                       centLat, boolean geom, int pagina){
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        utente.cercaInRegione(filamento, tableView, id, nome, satellite, numSeg, lungh, centLon, centLat, geom, pagina);
    }
}