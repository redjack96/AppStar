package control;

import boundary.CalcolaDistanzeSegConGUI;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CalcolaDistanzeSegConController {
    public void istanziaCalcolaDistanzeSegConGUI(Event e){
        CalcolaDistanzeSegConGUI calcolaDistanzeSegConGUI= new CalcolaDistanzeSegConGUI();
        calcolaDistanzeSegConGUI.istanziaCalcolaDistanzeSegConGUIFXML(e);
    }

    public ArrayList<String> calcolaDistanze(ChoiceBox<Integer> idSegField, TextField idFilField, ChoiceBox<String> satelliteCB){
        ArrayList<Float> distanze;
        ArrayList<String> distanzeString = new ArrayList<>(2);
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        int idSeg = idSegField.getValue();
        int idFil = Integer.parseInt(idFilField.getText());
        String satellite = satelliteCB.getValue();
        distanze = utente.calcolaDistanzeSegCon(idSeg, idFil, satellite);
        distanzeString.add(0, distanze.get(0).toString());
        distanzeString.add(1, distanze.get(1).toString());
        return distanzeString;
    }
    public ObservableList<Integer> trovaSegmenti(TextField idFilField, ChoiceBox<String> satelliteCB){
        ObservableList<Integer> listaChoicebox = FXCollections.observableArrayList(0);
        listaChoicebox.remove(0);

        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        int idFil = Integer.parseInt(idFilField.getText());
        String satellite = satelliteCB.getValue();
        ArrayList<Integer> lista = utente.trovaSegmenti(idFil, satellite);

        listaChoicebox.addAll(lista);

        return listaChoicebox;
    }
}
