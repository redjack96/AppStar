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

import static java.lang.Math.round;

public class RicercaStelleInRegioneController {
    public void istanziaRicercaStelleInRegioneGUI(Event e){
        RicercaStelleInRegioneGUI ricercaStelleInRegioneGUI = new RicercaStelleInRegioneGUI();
        ricercaStelleInRegioneGUI.istanziaRicercaStelleInRegioneGUIFXML(e);
    }

    public ArrayList<String> cercaStelleRegione(ObservableList<Stella> stella, TableView tableView, TableColumn id,
                                                TableColumn nameStar, TableColumn glon, TableColumn glat,
                                                TableColumn flux, TableColumn type, TextField hField, TextField bField,
                                                TextField lonField, TextField latField, int pagina){

        ArrayList<Integer> arrayList;
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

        //Arrotondo le percentuali con 2 cifre decimali
        String totaleInPerc = String.valueOf(round(10000.0*totaleIn/totale)/100.0);
        String totaleOutPerc = String.valueOf(round(10000.0*totaleOut/totale)/100.0);
        String unboundInPerc = String.valueOf(round(10000.0*unboundIn/totaleIn)/100.0);
        String prestellarInPerc = String.valueOf(round(10000.0*prestellarIn/totaleIn)/100.0);
        String protostellarInPerc = String.valueOf(round(10000.0*protostellarIn/totaleIn)/100.0);
        String unboundOutPerc = String.valueOf(round(10000.0*unboundOut/totaleOut)/100.0);
        String prestellarOutPerc = String.valueOf(round(10000.0*prestellarOut/totaleOut)/100.0);
        String protostellarOutPerc = String.valueOf(round(10000.0*protostellarOut/totaleOut)/100.0);

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
