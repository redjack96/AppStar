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

import static java.lang.Math.round;

public class RicercaStelleInFilamentoController {

    public void istanziaRicercaStelleInFilamentoGUI(Event e){
        RicercaStelleInFilamentoGUI ricercaStelleInFilamentoGUI = new RicercaStelleInFilamentoGUI();
        ricercaStelleInFilamentoGUI.istanziaRicercaStelleInFilamentoGUIFXML(e);
    }

    public ArrayList<Double> cercaInFilamento(ObservableList<Stella> stella, TableView<Stella> tableView, TableColumn<Stella, Integer> id,
                                             TableColumn<Stella, String> nameStar, TableColumn<Stella, Float> glon, TableColumn<Stella, Float> glat,
                                             TableColumn<Stella, Float> flux, TableColumn<Stella, String> type, int idFil, String satellite,
                                             int pagina){
        ArrayList arrayList;
        ArrayList<Double> arrayPercentuali = new ArrayList<>(4);

        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        arrayList = utente.cercaInFilamento(stella, tableView, id, nameStar, glon, glat, flux, type, idFil, satellite,
                pagina);

        double prestellar = Double.parseDouble(arrayList.get(0).toString());
        double protostellar = Double.parseDouble(arrayList.get(1).toString());
        double unbound = Double.parseDouble(arrayList.get(2).toString());

        double totale = unbound + prestellar + protostellar;
        double unboundPerc = round((unbound/totale)*10000.0)/100.0;
        double prestellarPerc = round((prestellar/totale)*10000.0)/100.0;
        double protostellarPerc = round((protostellar/totale)*10000.0)/100.0;
        arrayPercentuali.add(0, totale); arrayPercentuali.add(1, unboundPerc);
        arrayPercentuali.add(2, prestellarPerc); arrayPercentuali.add(3, protostellarPerc);
        //arrayPercentuali contiene [totale stelle trovate, percentuale unbound, percentuale prestellar,
        // percentuale protostellar].
        return arrayPercentuali;
    }
}
