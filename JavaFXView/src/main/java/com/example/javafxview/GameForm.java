package com.example.javafxview;


import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.example.*;


public class GameForm implements Initializable {

     @FXML
     public GridPane obszarSudoku;
     @FXML
    public Button bzapiszgre;
     @FXML
    public Button bsprawdz;

    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard board = new SudokuBoard(solver);
    SudokuBoard playerBoard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void printBoard(Level level) throws IOException {

        board.solveGame();
        playerBoard = (SudokuBoard) board.clone();
        level.removeFields(playerBoard);
        TextArea textArea = new TextArea();
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
            }
        }
    }

    public void zapiszGre() throws IOException, ClassNotFoundException {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileSudokuBoardDao;
        fileSudokuBoardDao = factory.getFileDao("zapis");

        for(int i = 0;i < 9;i++) {
            for(int j = 0;j < 9;j++) {
               TextArea tmp = (TextArea) getNodeByRowColumnIndex(i,j,obszarSudoku);
               if(tmp.getText() != "") {
                   playerBoard.setBoard(i, j, Integer.valueOf(tmp.getText()));
               }
               else {
                   playerBoard.setBoard(i, j, 0);
               }
            }
        }
        fileSudokuBoardDao.write(playerBoard);
    }

    public void sprawdzPlansze(ActionEvent actionEvent) {


    }

    public Node getNodeByRowColumnIndex (final int row, final int column,GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
}
