package control;

import boundary.CalcolaDistanzeStellaSpinaGUI;
import entity.StellaSpina;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CalcolaDistanzeStellaSpinaController {
    public void istanziaCalcolaDistanzeStellaSpinaGUI(Event e){
        CalcolaDistanzeStellaSpinaGUI calcolaDistanzeStellaSpinaGUI= new CalcolaDistanzeStellaSpinaGUI();
        calcolaDistanzeStellaSpinaGUI.istanziaCalcolaDistanzeStellaSpinaGUIFXML(e);
    }
    //REQ 12
    public void calcolaDistanze(ObservableList<StellaSpina> listaStelle, TableView tableView, TableColumn id,
                                TableColumn nameStar, TableColumn glon, TableColumn glat,
                                TableColumn flux, TableColumn type, TableColumn distanza, TextField idFilField,
                                ChoiceBox<String> cb, String ord, int pagina){
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        String satellite = cb.getValue();
        int idFil = Integer.parseInt(idFilField.getText());
        utente.calcolaDistanzeStellaSpina(listaStelle, tableView, id, nameStar, glon, glat, flux, type, distanza, idFil,
                satellite, ord, pagina);
    }
}
