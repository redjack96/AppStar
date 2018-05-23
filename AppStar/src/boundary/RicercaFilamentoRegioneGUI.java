package boundary;

import control.HomeController;
import control.RicercaFilamentoRegioneController;
import entity.Filamento;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RicercaFilamentoRegioneGUI implements Initializable {
    @FXML
    private Button cerca;
    @FXML
    private Button indietro;
    @FXML
    private TableView tableView; //ok
    @FXML
    private TableColumn idColumn; //ok
    @FXML
    private TableColumn nomeColumn; //ok
    @FXML
    private TableColumn numSegColumn; //ok
    @FXML
    private TableColumn satColumn; //ok
    @FXML
    private Button precedente; //ok
    @FXML
    private Button successivo; //ok
    @FXML
    private TextField paginaText; //ok
    @FXML
    private TextField dimText; //ok
    @FXML
    private TextField lonCentroide; //ok
    @FXML
    private TextField latCentroide; //ok
    @FXML
    private RadioButton cerchio; //ok
    @FXML
    private RadioButton quadrato; //ok

    private ObservableList<Filamento> listaFilamenti;
    private boolean bloccaPaginaText = false;

    public void istanziaRicercaFilamentoRegioneGUIFXML(Event e){
        //Lancia l'interfaccia grafica RicercaFilamentoRegioneGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/RicercaFilamentoRegioneGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di ricerca del filamento per luminosita.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    private void ricerca(RicercaFilamentoRegioneController ricercaFilamentoRegioneController, int pagina) throws NumberFormatException{
        long start, elapsed;
        float result;
        boolean geom = false;
        if (quadrato.isSelected()){
            geom = true;    // quadrato = true
            System.out.println("Ricerca in un quadrato. Attendere...");
        } else if (cerchio.isSelected()){
            geom = false;  // cerchio = false
            System.out.println("Ricerca in un cerchio. Attendere...");
        }
        start = System.nanoTime();

        ricercaFilamentoRegioneController.cercaInRegione(listaFilamenti, tableView, idColumn, nomeColumn, satColumn,
                numSegColumn, Float.parseFloat(dimText.getText()), Float.parseFloat(lonCentroide.getText()),
                Float.parseFloat(latCentroide.getText()), geom, pagina);
        elapsed = System.nanoTime();
        result = (elapsed - start)/1000000000;
        System.out.println("Completato. Tempo impiegato: " + result);
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

        RicercaFilamentoRegioneController ricercaFilamentoRegioneController = new RicercaFilamentoRegioneController();

        paginaText.textProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (!bloccaPaginaText) {
                        //System.out.println("Sto cercando dopo aver cambiato il numero di pagina...");
                        ricerca(ricercaFilamentoRegioneController, pagina);
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
                    ricerca(ricercaFilamentoRegioneController, 1);
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
                        ricerca(ricercaFilamentoRegioneController, pagina);
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
                    ricerca(ricercaFilamentoRegioneController, pagina);
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