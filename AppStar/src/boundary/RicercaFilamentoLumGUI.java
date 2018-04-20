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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class RicercaFilamentoLumGUI implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Slider slider;
    @FXML
    private TextField range1;
    @FXML
    private TextField range2;
    @FXML
    private ObservableList<Filamento> listaFilamenti;  //TODO_ non serve listaFilamenti
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
    private Button scarica;
    @FXML
    private RadioButton uguale;
    @FXML
    private RadioButton minore;
    @FXML
    private RadioButton maggiore;
    @FXML
    private Button precedente;
    @FXML
    private Button successivo;
    @FXML
    private Label paginaLabel;
    @FXML
    private Label percentuale;

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

    public void initialize(URL location, ResourceBundle resources) {

        RicercaFilamentoLumController ricercaFilamentoLumController = new RicercaFilamentoLumController();

        uguale.setSelected(true);
        precedente.setDisable(true);

        /*slider.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                percentuale.setText(String.valueOf(slider.getValue()) + " %");
                System.out.println(percentuale.getText());
            }
        });*/

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                percentuale.setText(String.valueOf(newValue.toString() + " %"));
            }
        });

        cerca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int pagina = 1;
                paginaLabel.setText(String.valueOf(pagina));
                String simbolo;
                if (uguale.isSelected()){
                    simbolo = "uguale";
                }else if (minore.isSelected()){
                    simbolo = "minore";
                }else {
                    simbolo = "maggiore";
                }

                ricercaFilamentoLumController.cercaFilamenti(listaFilamenti, tableView, idColumn, nomeColumn,
                        numSegColumn, satColumn, new BigDecimal(slider.getValue()),
                        new BigDecimal((range1.getText())),
                        new BigDecimal((range2.getText())), simbolo, pagina);
                System.out.println(slider.getValue());
            }
        });

        precedente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int pagina = Integer.parseInt(paginaLabel.getText());
                if (pagina>1){
                    String simbolo;
                    if (uguale.isSelected()){
                        simbolo = "uguale";
                    }else if (minore.isSelected()){
                        simbolo = "minore";
                    }else {
                        simbolo = "maggiore";
                    }
                    pagina -= 1;
                    ricercaFilamentoLumController.cercaFilamenti(listaFilamenti, tableView, idColumn, nomeColumn,
                            numSegColumn, satColumn, new BigDecimal(slider.getValue()),
                            new BigDecimal(range1.getText()),
                            new BigDecimal(range2.getText()), simbolo, pagina);
                    paginaLabel.setText(String.valueOf(pagina));
                    if (pagina == 1){
                        precedente.setDisable(true);
                    }
                }
            }
        });

        successivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int pagina = Integer.parseInt(paginaLabel.getText());
                String simbolo;
                if (uguale.isSelected()){
                    simbolo = "uguale";
                }else if (minore.isSelected()){
                    simbolo = "minore";
                }else {
                    simbolo = "maggiore";
                }
                pagina += 1;
                ricercaFilamentoLumController.cercaFilamenti(listaFilamenti, tableView, idColumn, nomeColumn,
                        numSegColumn, satColumn, new BigDecimal(slider.getValue()),
                        new BigDecimal(range1.getText()),
                        new BigDecimal(range2.getText()), simbolo, pagina);
                paginaLabel.setText(String.valueOf(pagina));
                precedente.setDisable(false);
            }
        });
        // TODO: non serve "scarica"
        scarica.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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