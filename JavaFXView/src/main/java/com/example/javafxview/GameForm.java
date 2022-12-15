package com.example.javafxview;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.example.BacktrackingSudokuSolver;
import org.example.SudokuBoard;
import org.example.SudokuSolver;


public class GameForm implements Initializable {

    public GridPane obszarSudoku = new GridPane();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void printBoard(Level level) throws IOException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();
        SudokuBoard playerBoard = (SudokuBoard) board.clone();
        level.removeFields(playerBoard);
        TextArea textArea = new TextArea();
        RowConstraints rc = new RowConstraints();
        ColumnConstraints cc = new ColumnConstraints();

        for(int x = 0;x < 9;x++) {
            for(int y = 0;y < 9;y++) {
                if (playerBoard.getBoard(x,y).getFieldValue()!=0) {
                    textArea = new TextArea(String.valueOf(playerBoard.getBoard(x,y).getFieldValue()));
                    textArea.setEditable(false);
                    textArea.setFont(Font.font(14));
                    obszarSudoku.add(textArea, x, y);

                } else {
                    TextArea textArea1 = new TextArea();
                    textArea.setFont(Font.font(14));
                    obszarSudoku.add(textArea1, x, y);
                }
                rc.setPrefHeight(50);
                cc.setPrefWidth(50);
                obszarSudoku.getRowConstraints().add(rc);
                obszarSudoku.getColumnConstraints().add(cc);
            }
        }

        Stage stage = new Stage();
        Scene scene = new Scene(obszarSudoku, 700, 700);
        stage.setTitle("Gra");
        stage.setScene(scene);
        obszarSudoku.setLayoutX(150);
        obszarSudoku.setLayoutY(150);


        stage.show();
    }

}
