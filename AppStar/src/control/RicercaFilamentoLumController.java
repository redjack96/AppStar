package control;

import boundary.RicercaFilamentoLumGUI;
import entity.Filamento;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;

public class RicercaFilamentoLumController {

    public void istanziaRicercaFilamentoLumGUI(Event e){
        RicercaFilamentoLumGUI ricercaFilamentoLumGUI = new RicercaFilamentoLumGUI();
        ricercaFilamentoLumGUI.istanziaRicercaFilamentoLumGUIFXML(e);
    }

    public void cercaFilamenti(ObservableList<Filamento> filamento, TableView tableView, TableColumn id, TableColumn nome,
                               TableColumn numSeg, TableColumn satellite, BigDecimal lum, BigDecimal ellipt1,
                               BigDecimal ellipt2, String simbolo, int pagina){
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        utente.cercaFilamenti(filamento, tableView, id, nome, numSeg, satellite, lum, ellipt1, ellipt2, simbolo, pagina);
    }
}
