package com.example.javafxview;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;



public class App extends Application {

    @Override public void start(Stage stage) throws IOException {
        Menu menu = new Menu();
        menu.showStage();

    }

    public static void main(String[] args) {
        launch();
    }
}