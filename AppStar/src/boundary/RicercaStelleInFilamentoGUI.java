package boundary;

import control.HomeController;
import control.RicercaStelleInFilamentoController;
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

public class RicercaStelleInFilamentoGUI implements Initializable {
    @FXML
    private ResourceBundle resource;
    @FXML
    private URL locationd;
    @FXML
    private TextField idText;
    @FXML
    private TextField paginaText;
    @FXML
    private ChoiceBox<String> choiceBox;
    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Herscel", "Spitzer");
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn tipoColumn;
    @FXML
    private TableColumn PercentualeColumn;
    @FXML
    private Button indietro;
    @FXML
    private Button precedente;
    @FXML
    private Button successivo;
    @FXML
    private Button cerca;

    public void istanziaRicercaStelleInFilamentoGUIFXML(Event e){

        try{
            Parent root = FXMLLoader.load(getClass().getResource(("/boundary.RicercaStelleInFilamentoGUI.fxml")));
            ((javafx.scene.Node) (e.getSource())).getScene().setRoot((root));
        }catch (Exception er){
            System.out.println(er.getMessage());
        }
    }

    public float ricerca(RicercaStelleInFilamentoController controller, int pagina){
        float result = 0;
        //result = funzione(...)
        if (pagina == 1){
            precedente.setDisable(true);
        }else{
            precedente.setDisable(true);
        }
        return result;
    }

    public void initialize(URL location, ResourceBundle resource){

        RicercaStelleInFilamentoController ricercaStelleInFilamentoController = new RicercaStelleInFilamentoController();

        choiceBox.setItems(choiceBoxList);
        choiceBox.setValue("Herschel");

        paginaText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                float result;
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    ricerca(ricercaStelleInFilamentoController, pagina);
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        });

        cerca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int pagina = 1;
                float result;
                try{
                    paginaText.setText(String.valueOf(pagina));
                    ricerca(ricercaStelleInFilamentoController, pagina);
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        });

        precedente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float result;
                try {
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (pagina > 1) {
                        pagina -= 1;
                        result = ricerca(ricercaStelleInFilamentoController, pagina);
                    }
                }catch (NumberFormatException nFE){
                        System.out.println(nFE.getMessage());
                    }
                }
        });

        successivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float result;
                int pagina = Integer.parseInt(paginaText.getText());
                try{
                    pagina+=1;
                    result = ricerca(ricercaStelleInFilamentoController, pagina);
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
