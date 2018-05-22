package control;

import boundary.RicercaStelleInRegioneGUI;
import entity.Stella;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class RicercaStelleInRegioneController {
    public void istanziaRicercaStelleInRegioneGUI(Event e){
        RicercaStelleInRegioneGUI ricercaStelleInRegioneGUI = new RicercaStelleInRegioneGUI();
        ricercaStelleInRegioneGUI.istanziaRicercaStelleInRegioneGUIFXML(e);
    }

    public ArrayList<String> cercaStelleRegione(ObservableList<Stella> stella, TableView tableView, TableColumn id,
                                                TableColumn nameStar, TableColumn glon, TableColumn glat,
                                                TableColumn flux, TableColumn type, TextField hField, TextField bField,
                                                TextField lonField, TextField latField, int pagina){

        ArrayList<Integer> arrayList = new ArrayList<>(6);
        ArrayList<String > stringArrayList = new ArrayList<>(8);

        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);

        float h = Float.parseFloat(hField.getText());
        float b = Float.parseFloat(bField.getText());
        float lon = Float.parseFloat(lonField.getText());
        float lat = Float.parseFloat(latField.getText());
        arrayList = utente.cercaInRegione(stella, tableView, id, nameStar, glon, glat, flux, type, h, b, lon, lat,
                pagina);

        int unboundIn = arrayList.get(0); int prestellarIn = arrayList.get(1); int protostellarIn = arrayList.get(2);
        int unboundOut = arrayList.get(3); int prestellarOut = arrayList.get(4); int protostellarOut = arrayList.get(5);
        int totaleIn = unboundIn + prestellarIn + protostellarIn;
        int totaleOut = unboundOut + prestellarOut + protostellarOut;
        int totale = totaleIn + totaleOut;

        String totaleInPerc = String.valueOf(totaleIn/totale);
        String totaleOutPerc = String.valueOf(totaleOut/totale);
        String unboundInPerc = String.valueOf(unboundIn/totaleIn);
        String prestellarInPerc = String.valueOf(prestellarIn/totaleIn);
        String protostellarInPerc = String.valueOf(protostellarIn/totaleIn);
        String unboundOutPerc = String.valueOf(unboundOut/totaleOut);
        String prestellarOutPerc = String.valueOf(prestellarOut/totaleOut);
        String protostellarOutPerc = String.valueOf(protostellarOut/totaleOut);

        stringArrayList.add(0, totaleInPerc);
        stringArrayList.add(1, totaleOutPerc);
        stringArrayList.add(2, unboundInPerc);
        stringArrayList.add(3, prestellarInPerc);
        stringArrayList.add(4, protostellarInPerc);
        stringArrayList.add(5, unboundOutPerc);
        stringArrayList.add(6, prestellarOutPerc);
        stringArrayList.add(7, protostellarOutPerc);

        return stringArrayList;
    }
}
