package boundary;

import control.CalcolaDistanzeStellaSpinaController;
import control.HomeController;
import entity.StellaSpina;
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
import java.util.ResourceBundle;

public class CalcolaDistanzeStellaSpinaGUI  implements Initializable {
    @FXML
    private ResourceBundle resource;
    @FXML
    private URL locationd;
    @FXML
    private TextField idText;
    @FXML
    private ChoiceBox<String> choiceBox;
    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Herschel", "Spitzer");
    private ObservableList<StellaSpina> listaStelle;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nomeColumn;
    @FXML
    private TableColumn lonColumn;
    @FXML
    private TableColumn latColumn;
    @FXML
    private TableColumn fluxColumn;
    @FXML
    private TableColumn tipoColumn;
    @FXML
    private TableColumn distanzaColumn;
    @FXML
    private RadioButton distRB;
    @FXML
    private RadioButton fluxRB;
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

    private boolean bloccaPaginaText = false;

    public void istanziaCalcolaDistanzeStellaSpinaGUIFXML(Event e){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/CalcolaDistanzeStellaSpinaGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.out.println(er.getMessage());
        }
    }

    private void ricerca(CalcolaDistanzeStellaSpinaController controller, int pagina) throws NumberFormatException{
        /*controller.cercaInFilamento(listaStelle, tableView, idColumn, nomeColumn, lonColumn,
                latColumn, fluxColumn, tipoColumn, Integer.parseInt(idText.getText()), choiceBox.getValue(), pagina);*/

        String ord;
        if (distRB.isSelected()){
            ord = "distanza";
        }else{
            ord = "flusso";
        }

        controller.calcolaDistanze(listaStelle, tableView, idColumn, nomeColumn, lonColumn, latColumn, fluxColumn,
                tipoColumn, distanzaColumn, idText, choiceBox, ord, pagina);

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

        CalcolaDistanzeStellaSpinaController controller = new CalcolaDistanzeStellaSpinaController();

        choiceBox.setItems(choiceBoxList);
        choiceBox.setValue("Herschel");

        precedente.setDisable(true);

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

        indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HomeController homeController = new HomeController();
                homeController.istanziaHomeGUI(event);
            }
        });
    }
}
