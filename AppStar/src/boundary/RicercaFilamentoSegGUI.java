package boundary;

import control.HomeController;
import control.RicercaFilamentoSegController;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RicercaFilamentoSegGUI implements Initializable {
    @FXML
    private TextField range1;
    @FXML
    private TextField range2;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nomeColumn;
    @FXML
    private TableColumn numSegColumn;
    @FXML
    private TableColumn satColumn;
    @FXML
    private Button indietro;
    @FXML
    private Button cerca;
    @FXML
    private Button precedente;
    @FXML
    private Button successivo;
    @FXML
    private TextField paginaText;
    @FXML
    private Label numRic;

    private ObservableList<Filamento> listaFilamenti = null;
    private boolean bloccaPaginaText = false;

    public void istanziaRicercaFilamentoSegGUIFXML(Event e){
        //Lancia l'interfaccia grafica RicercaFilamentoSegGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/RicercaFilamentoSegGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di ricerca del filamento per luminosita.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }
    // funzione di ricerca per i pulsanti precedente, successivo, cerca e per il textfield del numero di pagina
    private void ricerca(RicercaFilamentoSegController controller, int pagina) throws NumberFormatException{
        int result;
        int minSeg = Integer.parseInt(range1.getText());
        int maxSeg = Integer.parseInt(range2.getText());
        if (minSeg < 0 || (maxSeg - minSeg <= 2)){
            System.out.println("Errore: dimensione intervallo deve essere maggiore di 2!");
        }else {
            // numero di ricorrenze e popolamento tabella
            result = controller.cercaFilamentiSeg(listaFilamenti, tableView, idColumn,
                    nomeColumn, satColumn, numSegColumn, Integer.parseInt(range1.getText()),
                    Integer.parseInt(range2.getText()), pagina);
            numRic.setText(String.valueOf(result));
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
    }

    public void initialize(URL location, ResourceBundle resources) {

        RicercaFilamentoSegController ricercaFilamentoSegController = new RicercaFilamentoSegController();

        precedente.setDisable(true);
        // ricerca automatica dal textfield del numero di pagina
        paginaText.textProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (!bloccaPaginaText) {
                        System.out.println("Sto cercando dopo un cambio pagina...");
                        ricerca(ricercaFilamentoSegController, pagina);
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
                int pagina = 1;
                try{
                    System.out.println("Sto cercando dopo aver premuto \"Cerca\"...");
                    ricerca(ricercaFilamentoSegController, pagina);
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
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
                        ricerca(ricercaFilamentoSegController, pagina);
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
                    ricerca(ricercaFilamentoSegController, pagina);
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