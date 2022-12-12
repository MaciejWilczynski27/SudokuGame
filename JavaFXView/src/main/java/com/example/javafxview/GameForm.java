package com.example.javafxview;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.example.BacktrackingSudokuSolver;
import org.example.SudokuBoard;
import org.example.SudokuSolver;


public class GameForm implements Initializable {

    public TextArea obszarSudoku;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void printBoard(Level level) {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();
        level.removeFields(board);
        obszarSudoku.setText(board.print());
    }

}
