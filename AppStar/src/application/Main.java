package application;

import control.LoginController;
import javafx.application.Application;

public class Main {

    public static void main(String[] args){
        Application.launch(LoginController.class, // Lancia l'applicazione a partire dal login controller(non dall'interfaccia)
                args);
    }
}