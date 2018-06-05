package control;

import boundary.RicercaFilamentoLumGUI;
import entity.Filamento;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;

public class RicercaFilamentoLumController {

    public void istanziaRicercaFilamentoLumGUI(Event e){
        RicercaFilamentoLumGUI ricercaFilamentoLumGUI = new RicercaFilamentoLumGUI();
        ricercaFilamentoLumGUI.istanziaRicercaFilamentoLumGUIFXML(e);
    }

    public ArrayList<Integer> cercaFilamenti(ObservableList<Filamento> filamento, TableView<Filamento> tableView, TableColumn<Filamento, Integer> id, TableColumn<Filamento, String> nome,
                               TableColumn<Filamento, Integer> numSeg, TableColumn<Filamento, String> satellite, TableColumn<Filamento, Float> con, TableColumn<Filamento, Float> ell,
                               float lum, float ellipt1, float ellipt2, int pagina){
        ArrayList<Integer> result;
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        result = utente.cercaFilamenti(filamento, tableView, id, nome, numSeg, satellite, con, ell, lum, ellipt1, ellipt2,
                pagina);
        return result;
    }
}
