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

    public ArrayList<String> cercaStelleRegione(ObservableList<Stella> stella, TableView<Stella> tableView, TableColumn<Stella, Integer> id,
                                                TableColumn<Stella, String> nameStar, TableColumn<Stella, Float> glon, TableColumn<Stella, Float> glat,
                                                TableColumn<Stella, Float> flux, TableColumn<Stella, String> type, TextField hField, TextField bField,
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


        double unboundIn = arrayList.get(0);
        double prestellarIn = arrayList.get(1);
        double protostellarIn = arrayList.get(2);
        double unboundOut = arrayList.get(3);
        double prestellarOut = arrayList.get(4);
        double protostellarOut = arrayList.get(5);
        double totaleIn = unboundIn + prestellarIn + protostellarIn;
        double totaleOut = unboundOut + prestellarOut + protostellarOut;
        double totale = totaleIn + totaleOut;

        //Arrotondo le percentuali con 2 cifre decimali
        double uInP = round(10000.0*unboundIn/totaleIn)/100.0;
        double preInP = round(10000.0*prestellarIn/totaleIn)/100.0;
        double proInP = round(10000.0*protostellarIn/totaleIn)/100.0;
        double uOutP = round(10000.0*unboundOut/totaleOut)/100.0;
        double preOutP = round(10000.0*prestellarOut/totaleOut)/100.0;
        double proOutP = round(10000.0*protostellarOut/totaleOut)/100.0;
        double totInP = round(10000.0*totaleIn/totale)/100.0;
        double totOutP = round(10000.0*totaleOut/totale)/100.0;

        // risolvo gli errori di arrotondamento
        if (totaleIn !=0) {
            double delta1 = 100.0 - (uInP + preInP + proInP);
            if (delta1 != 0) {
                uInP += delta1;
            }
        }

        if (totaleOut !=0) {
            double delta2 = 100.0 - (uOutP + preOutP + proOutP);
            if (delta2 != 0) {
                uOutP += delta2;
            }
        }
        if (totale !=0) {
            double delta3 = 100.0 - (totInP + totOutP);
            if (delta3 != 0){
                totInP += delta3;
            }
        }

        String totaleInPerc = String.valueOf(totInP);
        String totaleOutPerc = String.valueOf(totOutP);
        String unboundInPerc = String.valueOf(uInP);
        String prestellarInPerc = String.valueOf(preInP);
        String protostellarInPerc = String.valueOf(proInP);
        String unboundOutPerc = String.valueOf(uOutP);
        String prestellarOutPerc = String.valueOf(preOutP);
        String protostellarOutPerc = String.valueOf(proOutP);

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
