package com.example.javafxview;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Menu {
    @FXML
    public Button bpoziom3;
    @FXML
    public Button bpoziom1;
    @FXML
    public Button bpoziom2;

    static
    GameForm gameForm = null;

    public void setPoziom1() throws IOException {
        openForm(Level.EASY);
    }

    public void setPoziom2() throws IOException {
        openForm(Level.MEDIUM);
    }

    public void setPoziom3() throws IOException {
        openForm(Level.HARD);
    }

    public void showStage() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("SudokuGame");
        stage.setScene(scene);

        stage.show();
    }

    public void openForm(Level level) throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Gra");
        stage.setScene(scene);
        gameForm = (GameForm) loader.getController();
        gameForm.printBoard(level);
        stage.show();

    }

}