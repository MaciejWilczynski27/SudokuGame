package com.example.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {
    public void loadGame(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GameForm.fxml"));
        Scene scene = new Scene(root, 600, 500);
        Stage stage = new Stage();
        stage.setTitle("SudokuGame");
        stage.setScene(scene);
        stage.show();
    }
}
