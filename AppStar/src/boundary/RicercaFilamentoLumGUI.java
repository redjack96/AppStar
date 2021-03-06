package boundary;

import control.HomeController;
import control.RicercaFilamentoLumController;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Math.round;

public class RicercaFilamentoLumGUI implements Initializable {
    @FXML
    private Slider slider;
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
    private TableColumn conColumn;
    @FXML
    private TableColumn elliptColumn;
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
    private TextField percentualeText;
    @FXML
    private Label numRic;
    @FXML
    private Label tot;

    private ObservableList<Filamento> listaFilamenti;
    private boolean bloccaPaginaText = false;

    public void istanziaRicercaFilamentoLumGUIFXML(Event e){
        //Lancia l'interfaccia grafica RicercaFilamentoLumGUI.fxml.

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/RicercaFilamentoLumGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di ricerca del filamento per luminosita.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    // funzione di ricerca per i pulsanti precedente, successivo, cerca e per il textfield del numero di pagina
    private void ricerca(RicercaFilamentoLumController controller, int pagina) throws NumberFormatException{
        ArrayList<Integer> result;
        float minEllipticity = Float.parseFloat(range1.getText());
        float maxEllipticity = Float.parseFloat(range2.getText());
        float lum = Float.parseFloat((percentualeText.getText()));
        if (minEllipticity <= 1.0 || maxEllipticity >=10.0 || (minEllipticity > maxEllipticity) ||
                lum < 0.0){
            System.out.println("Errore: hai inserito ellitticita' non valide o luminosita' negativa.");
        }else {
            result = controller.cercaFilamenti(listaFilamenti, tableView, idColumn, nomeColumn,
                    numSegColumn, satColumn, conColumn, elliptColumn, lum,
                    minEllipticity, maxEllipticity, pagina);
            numRic.setText(String.valueOf(result.get(0)));
            tot.setText( " / " + String.valueOf(result.get(1)));
            // evita di far partire la ricerca 2 volte quando viene cambiato il numero di pagina
            bloccaPaginaText = true;
            paginaText.setText(String.valueOf(pagina));
            // toglie il blocco
            bloccaPaginaText = false;
        }
        if (pagina==1){
            precedente.setDisable(true);
        }else {
            precedente.setDisable(false);
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        RicercaFilamentoLumController ricercaFilamentoLumController = new RicercaFilamentoLumController();

        percentualeText.setText("00.00");
        slider.setBlockIncrement(0.01);
        precedente.setDisable(true);

        // slider della percentuale (il suo utilizzo e' opzionale)
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double valore = (double) newValue;
                percentualeText.setText(String.valueOf(round(valore*100.0)/100.0));
            }
        });

        // ricerca automatica dal textfield del numero di pagina
        paginaText.textProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (!bloccaPaginaText) {
                        System.out.println("Sto cercando mentre e' cambiata pagina...");
                        ricerca(ricercaFilamentoLumController, pagina);
                    }
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        }));

        // tasto di ricerca a pagina 1
        cerca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int pagina = 1;
                try{
                    ricerca(ricercaFilamentoLumController, pagina);
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        });

        // ricerca alla pagina precedente, se possibile
        precedente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (pagina>1){
                        pagina -= 1;
                        ricerca(ricercaFilamentoLumController, pagina);
                    }
                }catch (NumberFormatException nFE){
                        System.out.println(nFE.getMessage());
                }
            }
        });

        // ricerca alla pagina successiva
        successivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    pagina += 1;
                    ricerca(ricercaFilamentoLumController, pagina);
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