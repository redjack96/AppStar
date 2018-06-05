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
    public void calcolaDistanze(ObservableList<StellaSpina> listaStelle, TableView<StellaSpina> tableView, TableColumn<StellaSpina, Integer> id,
                                TableColumn<StellaSpina, String> nameStar, TableColumn<StellaSpina, Float> glon, TableColumn<StellaSpina, Float> glat,
                                TableColumn<StellaSpina, Float> flux, TableColumn<StellaSpina, String> type, TableColumn<StellaSpina, Float> distanza,
                                TextField idFilField, ChoiceBox<String> cb, String ord, int pagina){
        // ottiene l'istanza del singleton e il satellite scelto
        UtenteRegistrato utente = UtenteConnesso.getInstance(null, null);
        String satellite = cb.getValue();

        int idFil = Integer.parseInt(idFilField.getText());
        utente.calcolaDistanzeStellaSpina(listaStelle, tableView, id, nameStar, glon, glat, flux, type, distanza, idFil,
                satellite, ord, pagina);
    }
}
