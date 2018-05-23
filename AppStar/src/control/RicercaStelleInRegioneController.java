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

        float h, b, lon, lat;

        try{
            h = Float.parseFloat(hField.getText());
            b = Float.parseFloat(bField.getText());
            lon = Float.parseFloat(lonField.getText());
            lat = Float.parseFloat(latField.getText());
        }catch (NumberFormatException nFE){
            nFE.printStackTrace();
            h = b = lon = lat = 0;
        }

        arrayList = utente.cercaInRegione(stella, tableView, id, nameStar, glon, glat, flux, type, h, b, lon, lat,
                pagina);


        double unboundIn = arrayList.get(0); double prestellarIn = arrayList.get(1); double protostellarIn = arrayList.get(2);
        double unboundOut = arrayList.get(3); double prestellarOut = arrayList.get(4); double protostellarOut = arrayList.get(5);
        double totaleIn = unboundIn + prestellarIn + protostellarIn;
        double totaleOut = unboundOut + prestellarOut + protostellarOut;
        double totale = totaleIn + totaleOut;

        String totaleInPerc = String.valueOf(100*totaleIn/totale);
        String totaleOutPerc = String.valueOf(100*totaleOut/totale);
        String unboundInPerc = String.valueOf(100*unboundIn/totaleIn);
        String prestellarInPerc = String.valueOf(100*prestellarIn/totaleIn);
        String protostellarInPerc = String.valueOf(100*protostellarIn/totaleIn);
        String unboundOutPerc = String.valueOf(100*unboundOut/totaleOut);
        String prestellarOutPerc = String.valueOf(100*prestellarOut/totaleOut);
        String protostellarOutPerc = String.valueOf(100*protostellarOut/totaleOut);

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
