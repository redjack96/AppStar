package boundary;

import control.HomeController;
import control.RicercaFilamentoLumController;
import entity.Filamento;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
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
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
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
    private ObservableList<Filamento> listaFilamenti;
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

        percentualeText.setText("00.00");
        slider.setBlockIncrement(0.01);
        precedente.setDisable(true);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                /*percentuale.setText(String.valueOf(newValue.toString() + " %"));*/
                percentualeText.setText(String.valueOf(newValue.toString()));
            }
        });

        paginaText.textProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ArrayList<Integer> result = new ArrayList<>(2);
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    float minEllipticity = Float.parseFloat(range1.getText());
                    float maxEllipticity = Float.parseFloat(range2.getText());
                    float lum = Float.parseFloat(percentualeText.getText());
                    if (minEllipticity <= 1.0 || maxEllipticity >=10.0 || (minEllipticity > maxEllipticity) ||
                            lum < 0.0){
                        System.out.println("Errore");
                    }else {
                        result = ricercaFilamentoLumController.cercaFilamenti(listaFilamenti, tableView, idColumn, nomeColumn,
                                numSegColumn, satColumn, conColumn, elliptColumn, lum,
                                minEllipticity, maxEllipticity, pagina);
                        numRic.setText(String.valueOf(result.get(0)));
                        tot.setText( " / " + String.valueOf(result.get(1)));
                        if (pagina==1){
                            precedente.setDisable(true);
                        }else {
                            precedente.setDisable(false);
                        }
                    }
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        }));

        cerca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int pagina = 1;
                ArrayList<Integer> result = new ArrayList<>(2);
                //paginaLabel.setText(String.valueOf(pagina));
                try{
                    paginaText.setText(String.valueOf(pagina));
                    float minEllipticity = Float.parseFloat(range1.getText());
                    float maxEllipticity = Float.parseFloat(range2.getText());
                    float lum = Float.parseFloat((percentualeText.getText()));
                    if (minEllipticity <= 1.0 || maxEllipticity >=10.0 || (minEllipticity > maxEllipticity) ||
                            lum < 0.0){
                        System.out.println("Errore");
                    }else {
                        result = ricercaFilamentoLumController.cercaFilamenti(listaFilamenti, tableView, idColumn, nomeColumn,
                                numSegColumn, satColumn, conColumn, elliptColumn, lum,
                                minEllipticity, maxEllipticity, pagina);
                        numRic.setText(String.valueOf(result.get(0)));
                        tot.setText( " / " + String.valueOf(result.get(1)));
                    }
                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                }
            }
        });

        precedente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //int pagina = Integer.parseInt(paginaLabel.getText());
                ArrayList<Integer> result = new ArrayList<>(2);
                try{
                    int pagina = Integer.parseInt(paginaText.getText());
                    if (pagina>1){
                        pagina -= 1;
                        float minEllipticity = Float.parseFloat(range1.getText());
                        float maxEllipticity = Float.parseFloat(range2.getText());
                        float lum = Float.parseFloat(percentualeText.getText());
                        if (minEllipticity <= 1.0 || maxEllipticity >=10.0 || (minEllipticity > maxEllipticity) ||
                                lum < 0.0){
                            System.out.println("Errore");
                        }else {
                            result = ricercaFilamentoLumController.cercaFilamenti(listaFilamenti, tableView, idColumn, nomeColumn,
                                    numSegColumn, satColumn, conColumn, elliptColumn, lum,
                                    minEllipticity, maxEllipticity, pagina);
                            numRic.setText(String.valueOf(result.get(0)));
                            tot.setText( " / " + String.valueOf(result.get(1)));
                            paginaText.setText(String.valueOf(pagina));
                            if (pagina == 1){
                                precedente.setDisable(true);
                            }
                        }
                    }
                }catch (NumberFormatException nFE){
                        System.out.println(nFE.getMessage());
                }
            }
        });

        successivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Integer> result = new ArrayList<>(2);
                //int pagina = Integer.parseInt(paginaLabel.getText());
                int pagina = Integer.parseInt(paginaText.getText());

                try{
                    pagina += 1;
                    float minEllipticity = Float.parseFloat(range1.getText());
                    float maxEllipticity = Float.parseFloat(range2.getText());
                    float lum = Float.parseFloat(percentualeText.getText());
                    if (minEllipticity <= 1.0 || maxEllipticity >=10.0 || (minEllipticity > maxEllipticity) ||
                            lum < 0.0){
                        System.out.println("Errore");
                    }else {
                        result = ricercaFilamentoLumController.cercaFilamenti(listaFilamenti, tableView, idColumn, nomeColumn,
                                numSegColumn, satColumn, conColumn, elliptColumn, lum,
                                minEllipticity, maxEllipticity, pagina);
                        numRic.setText(String.valueOf(result.get(0)));
                        tot.setText( " / " + String.valueOf(result.get(1)));
                        paginaText.setText(String.valueOf(pagina));
                        precedente.setDisable(false);
                    }
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