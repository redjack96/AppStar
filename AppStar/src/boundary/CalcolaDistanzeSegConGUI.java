package boundary;

import control.CalcolaDistanzeSegConController;
import control.HomeController;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalcolaDistanzeSegConGUI implements Initializable {
/*
    @FXML
    private ResourceBundle resource;
    @FXML
    private URL location;
*/
    @FXML
    private ChoiceBox<Integer> idSegCB;
    @FXML
    private TextField idFilText;
    @FXML
    private ChoiceBox<String> choiceBox;
    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Herschel", "Spitzer");
    @FXML
    private Label distanza1;
    @FXML
    private Label distanza2;
    @FXML
    private Button indietro;
    @FXML
    private Button cerca;

    public void istanziaCalcolaDistanzeSegConGUIFXML(Event e){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/CalcolaDistanzeSegConGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.out.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        CalcolaDistanzeSegConController controller = new CalcolaDistanzeSegConController();

        choiceBox.setItems(choiceBoxList);
        choiceBox.setValue("Herschel");


        idFilText.textProperty().addListener((new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    ObservableList<Integer> array = controller.trovaSegmenti(idFilText, choiceBox);
                    idSegCB.setItems(array);
                    if (!array.isEmpty()) {
                        idSegCB.setValue(array.get(0));
                    }

                }catch (NumberFormatException nFE){
                    nFE.printStackTrace();
                } catch (java.lang.IndexOutOfBoundsException oob) {
                    oob.printStackTrace();
                    System.out.println("Filamento inserito non e' nel database");
                }
            }
        }));

        // quando scegli un altro satellite
        choiceBox.valueProperty().addListener((new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    ObservableList<Integer> array = controller.trovaSegmenti(idFilText, choiceBox);
                    idSegCB.setItems(array);
                    if (!array.isEmpty()) {
                        idSegCB.setValue(array.get(0));
                    }

                }catch (NumberFormatException nFE){
                    System.out.println(nFE.getMessage());
                } catch (java.lang.IndexOutOfBoundsException oob) {
                oob.printStackTrace();
                System.out.println("Il satellite scelto non ha rilevato il satellite inserito");
            }
            }
        }));

        cerca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    ArrayList<String> distanze;
                    distanze = controller.calcolaDistanze(idSegCB, idFilText, choiceBox);
                    distanza1.setText(distanze.get(0));
                    distanza2.setText(distanze.get(1));
                }catch (NumberFormatException nFE){
                    nFE.printStackTrace();
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
