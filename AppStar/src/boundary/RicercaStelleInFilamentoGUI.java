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
    private ResourceBundle resource;
    @FXML
    private URL locationd;
    @FXML
    private TextField idText;
    private ChoiceBox<String> choiceBox;
    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Herscel", "Spitzer");
    private ObservableList<Stella> listaStelle;
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
    private Button cerca;

    public void istanziaRicercaStelleInFilamentoGUIFXML(Event e){

        try{
            Parent root = FXMLLoader.load(getClass().getResource(("/boundary.RicercaStelleInFilamentoGUI.fxml")));
            ((javafx.scene.Node) (e.getSource())).getScene().setRoot((root));
        }catch (Exception er){
            System.out.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        RicercaStelleInFilamentoController controller = new RicercaStelleInFilamentoController();

        choiceBox.setItems(choiceBoxList);
        choiceBox.setValue("Herschel");

        cerca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Float> result = new ArrayList<>(4);
                try{
                    result = controller.cercaInFilamento(listaStelle, tableView, idColumn, nomeColumn, lonColumn,
                            latColumn, fluxColumn, tipoColumn, Integer.parseInt(idText.getText()), choiceBox.getValue());
                    numRic.setText(String.valueOf(result.get(0)));
                    unbound.setText(String.valueOf(result.get(1)) + " %");
                    prestellar.setText(String.valueOf(result.get(2)) + " %");
                    protostellar.setText(String.valueOf(result.get(4)) + " %");
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
