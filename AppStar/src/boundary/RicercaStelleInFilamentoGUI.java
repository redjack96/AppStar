package boundary;

import control.HomeController;
import control.RicercaStelleInFilamentoController;
import entity.Stella;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

public class RicercaStelleInFilamentoGUI implements Initializable {
    @FXML
    private TextField idText;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TableView<Stella> tableView;
    @FXML
    private TableColumn<Stella, Integer> idColumn;
    @FXML
    private TableColumn<Stella, String> nomeColumn;
    @FXML
    private TableColumn<Stella, Float> lonColumn;
    @FXML
    private TableColumn<Stella, Float> latColumn;
    @FXML
    private TableColumn<Stella, Float> fluxColumn;
    @FXML
    private TableColumn<Stella, String> tipoColumn;
    @FXML
    private Label numRic;
    @FXML
    private Label unbound;
    @FXML
    private Label prestellar;
    @FXML
    private Label protostellar;
    @FXML
    private Button indietro;
    @FXML
    private Button precedente;
    @FXML
    private Button successivo;
    @FXML
    private TextField paginaText;
    @FXML
    private Button cerca;

    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Herschel", "Spitzer");
    private ObservableList<Stella> listaStelle = null;
    private boolean bloccaPaginaText = false;

    public void istanziaRicercaStelleInFilamentoGUIFXML(Event e){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/RicercaStelleInFilamentoGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.out.println(er.getMessage());
        }
    }
    // funzione di ricerca per i pulsanti precedente, successivo, cerca e per il textfield del numero di pagina
    private void ricerca(RicercaStelleInFilamentoController controller, int pagina) throws NumberFormatException{
        ArrayList<Double> result;
        result = controller.cercaInFilamento(listaStelle, tableView, idColumn, nomeColumn, lonColumn,
                latColumn, fluxColumn, tipoColumn, Integer.parseInt(idText.getText()), choiceBox.getValue(), pagina);
        System.out.println(Integer.parseInt(idText.getText()));
        System.out.println("Numero stelle mostrate: " + result.get(0));
        System.out.println("% unbound" + result.get(1));
        System.out.println("% prestellar" + result.get(2));
        System.out.println("% protostellar" + result.get(3));
        double delta = 100.0 - (result.get(1)+ result.get(2)+result.get(3));
        if (delta != 0.0){
            double x = result.get(1);
            x += delta; // fa in modo che la percentuale totale sia 100% se causa errori di approssimazione non e' 100%
            result.set(1,x);
        }

        numRic.setText(result.get(0).toString().substring(0, result.get(0).toString().length()-2));
        unbound.setText(String.valueOf(result.get(1)) + " %");
        prestellar.setText(String.valueOf(result.get(2)) + " %");
        protostellar.setText(String.valueOf(result.get(3)) + " %");
        // la somma fa 100.0 !
        //System.out.println("100 ?: "+ (result.get(1) + result.get(2) + result.get(3)));

        if (pagina==1){
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

    public void initialize(URL location, ResourceBundle resource){

        RicercaStelleInFilamentoController controller = new RicercaStelleInFilamentoController();

        choiceBox.setItems(choiceBoxList);
        choiceBox.setValue("Herschel");

        precedente.setDisable(true);
        // ricerca automatica dal textfield del numero di pagina
        paginaText.textProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (!bloccaPaginaText) {
                        System.out.println("Sto cercando dopo un cambio pagina...");
                        ricerca(controller, pagina);
                    }
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        }));
        // ricerca a pagina 1
        cerca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    ricerca(controller, 1);
                }catch (NumberFormatException nFE){
                    nFE.printStackTrace();
                }
            }
        });
        // ricerca a pagina precedente, se possibile
        precedente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (pagina>1){
                        pagina -= 1;
                        System.out.println("Sto cercando la pagina precedente...");
                        ricerca(controller, pagina);
                    }
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        });
        // ricerca a pagina successiva
        successivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    pagina += 1;
                    System.out.println("Sto cercando la pagina successiva...");
                    ricerca(controller, pagina);
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }

            }
        });
        // torna al menu home
        indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HomeController homeController = new HomeController();
                homeController.istanziaHomeGUI(event);
            }
        });
    }
}
