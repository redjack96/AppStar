package control;

import boundary.RicercaFilamentoSegGUI;
import entity.Filamento;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class RicercaFilamentoSegController {

    public void istanziaRicercaFilamentoSegGUI(Event e){
        RicercaFilamentoSegGUI ricercaFilamentoSegGUI = new RicercaFilamentoSegGUI();
        ricercaFilamentoSegGUI.istanziaRicercaFilamentoSegGUIFXML(e);
    }

    public int cercaFilamentiSeg(ObservableList<Filamento> filamento, TableView tableView, TableColumn id,
                                                TableColumn nome, TableColumn satellite, TableColumn numSeg, int seg1,
                                                int seg2, int pagina){
        int result;
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        result = utente.cercaFilamentiSeg(filamento, tableView, id, nome, satellite, numSeg, seg1, seg2, pagina);
        return result;
    }
}
