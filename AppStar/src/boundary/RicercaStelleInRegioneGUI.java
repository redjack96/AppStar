package boundary;

import control.HomeController;
import control.RicercaStelleInRegioneController;
import entity.Stella;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RicercaStelleInRegioneGUI implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cerca;
    @FXML
    private Button indietro;
    private ObservableList<Stella> listaStelle;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nomeColumn;
    @FXML
    private TableColumn latColumn;
    @FXML
    private TableColumn lonColumn;
    @FXML
    private TableColumn fluxColumn;
    @FXML
    private TableColumn tipoColumn;
    @FXML
    private Button precedente;
    @FXML
    private Button successivo;
    @FXML
    private TextField paginaText;
    @FXML
    private TextField baseText;
    @FXML
    private TextField altezzaText;
    @FXML
    private TextField lonCentroide;
    @FXML
    private TextField latCentroide;
    @FXML
    private Label inFilamentiPerc;
    @FXML
    private Label unboundInPerc;
    @FXML
    private Label prestellarInPerc;
    @FXML
    private Label protostellarInPerc;
    @FXML
    private Label outFilamentiPerc;
    @FXML
    private Label unboundOutPerc;
    @FXML
    private Label prestellarOutPerc;
    @FXML
    private Label protostellarOutPerc;

    private boolean bloccaPaginaText = false;

    public void istanziaRicercaStelleInRegioneGUIFXML(Event e){
        //Lancia l'interfaccia grafica RicercaStelleInRegioneGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/RicercaStelleInRegioneGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di ricerca delle stelle in una regione.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    private void ricerca(RicercaStelleInRegioneController controller, int pagina) throws NumberFormatException{
        long start, elapsed;
        float result;
        start = System.nanoTime();
        ArrayList<String> percentuali = controller.cercaStelleRegione(listaStelle, tableView, idColumn, nomeColumn,
                lonColumn, latColumn, fluxColumn, tipoColumn, altezzaText, baseText, lonCentroide, latCentroide, pagina);
        elapsed = System.nanoTime();
        result = (elapsed - start)/1000000000;
        System.out.println("Completato. Tempo impiegato: " + result);
        inFilamentiPerc.setText(percentuali.get(0));
        unboundInPerc.setText(percentuali.get(1));
        prestellarInPerc.setText(percentuali.get(2));
        protostellarInPerc.setText(percentuali.get(3));
        outFilamentiPerc.setText(percentuali.get(4));
        unboundOutPerc.setText(percentuali.get(5));
        prestellarOutPerc.setText(percentuali.get(6));
        protostellarOutPerc.setText(percentuali.get(7));
        if (pagina == 1){
            precedente.setDisable(true);
        }else {
            precedente.setDisable(false);
        }
        // evita di far partire la ricerca quando viene cambiato il numero di pagina
        bloccaPaginaText = true;
        paginaText.setText(String.valueOf(pagina));
        // toglie il blocco
        bloccaPaginaText = false;
    }
    public void initialize(URL location, ResourceBundle resources) {

        RicercaStelleInRegioneController controller = new RicercaStelleInRegioneController();
        paginaText.textProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (!bloccaPaginaText) {
                        //System.out.println("Sto cercando dopo aver cambiato il numero di pagina...");
                        ricerca(controller, pagina);
                    }
                } catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        }));

        cerca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    //System.out.println("Sto cercando dopo aver premuto \"Cerca\"...");
                    ricerca(controller, 1);
                }catch (NumberFormatException nFE ){
                    System.out.println(nFE.getMessage());
                }
            }
        });

        precedente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{

                    int pagina = Integer.parseInt(paginaText.getText());
                    if (pagina>1){
                        pagina -= 1;
                        //System.out.println("Sto cercando la pagina precedente");
                        ricerca(controller, pagina);
                    }
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        });

        successivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    pagina +=1;
                    //System.out.println("Sto cercando la pagina successiva");
                    ricerca(controller, pagina);
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        });

        indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HomeController homeController = new HomeController();
                homeController.istanziaHomeGUI(event);
            }
        });
    }
}
