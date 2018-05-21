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
                                             TableColumn flux, TableColumn type, int idFil, String satellite,
                                             int pagina){
        ArrayList<Integer> arrayList;
        ArrayList<Float> arrayPercentuali = new ArrayList<>(4);

        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        arrayList = utente.cercaInFilamento(stella, tableView, id, nameStar, glon, glat, flux, type, idFil, satellite,
                pagina);

        float prestellar = Float.parseFloat(arrayList.get(0).toString());
        float protostellar = Float.parseFloat(arrayList.get(1).toString());
        float unbound = Float.parseFloat(arrayList.get(2).toString());

         float totale = unbound + prestellar + protostellar;
         float unboundPerc = (unbound/totale)*100;
         float prestellarPerc = (prestellar/totale)*100;
         float protostellarPerc = (protostellar/totale)*100;
         arrayPercentuali.add(0, totale); arrayPercentuali.add(1, unboundPerc);
        arrayPercentuali.add(2, prestellarPerc); arrayPercentuali.add(3, protostellarPerc);
            //arrayPercentuali contiene [totale stelle trovate, percentuale unbound, percentule prestellar,
             // percentuale protostellar].
        return arrayPercentuali;
    }
}
