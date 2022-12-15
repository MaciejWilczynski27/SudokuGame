package com.example.javafxview;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle_pl_PL");

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @FXML
    public Button bpoziom3;
    @FXML
    public Button bpoziom1;
    @FXML
    public Button bpoziom2;

    @FXML
    public Button eng;

    @FXML
    public Button pl;

    @FXML
    public Text langchoose;

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
    @FXML
    public void languageChange(String language) {
        setResourceBundle(ResourceBundle.getBundle("bundle_" + language));
        bpoziom1.setText(getResourceBundle().getString("Level") + " 1");
        bpoziom2.setText(getResourceBundle().getString("Level") + " 2");
        bpoziom3.setText(getResourceBundle().getString("Level") + " 3");
        langchoose.setText(getResourceBundle().getString("langchoose"));
    }
    public void setEnglish() throws IOException {
        languageChange("en_EN");
    }
    public void setPolish() throws IOException {
        languageChange("pl_PL");
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
        gameForm = loader.getController();
        gameForm.printBoard(level);
        stage.show();

    }

}