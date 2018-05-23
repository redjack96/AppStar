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

    public ArrayList<Double> cercaInFilamento(ObservableList<Stella> stella, TableView tableView, TableColumn id,
                                             TableColumn nameStar, TableColumn glon, TableColumn glat,
                                             TableColumn flux, TableColumn type, int idFil, String satellite,
                                             int pagina){
        ArrayList<Integer> arrayList;
        ArrayList<Double> arrayPercentuali = new ArrayList<>(4);

        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        arrayList = utente.cercaInFilamento(stella, tableView, id, nameStar, glon, glat, flux, type, idFil, satellite,
                pagina);

        double prestellar = Double.parseDouble(arrayList.get(0).toString());
        double protostellar = Double.parseDouble(arrayList.get(1).toString());
        double unbound = Double.parseDouble(arrayList.get(2).toString());

        double totale = unbound + prestellar + protostellar;
        double unboundPerc = (unbound/totale)*100;
        double prestellarPerc = (prestellar/totale)*100;
        double protostellarPerc = (protostellar/totale)*100;
        arrayPercentuali.add(0, totale); arrayPercentuali.add(1, unboundPerc);
        arrayPercentuali.add(2, prestellarPerc); arrayPercentuali.add(3, protostellarPerc);
            //arrayPercentuali contiene [totale stelle trovate, percentuale unbound, percentule prestellar,
             // percentuale protostellar].
        return arrayPercentuali;
    }
}
