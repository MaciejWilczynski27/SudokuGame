package com.example.javafxview;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import org.example.*;



public class GameForm implements Initializable {

     @FXML
     public GridPane obszarSudoku;
     @FXML
     public Button bzapiszgre;
     @FXML
     public Button bsprawdz;
     @FXML
     public Text twynik;
    private ResourceBundle resourceBundle;

    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard board = new SudokuBoard(solver);
    SudokuBoard playerBoard;
    SudokuBoard playerBoardClone;
    SudokuBoard[] bothSudoku;

    private Logger logger = Logger.getLogger(GameForm.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void printBoard(Level level) throws GameBuildFailException {
        board.solveGame();
        playerBoard = (SudokuBoard) board.clone();

        level.removeFields(playerBoard);
        playerBoardClone = (SudokuBoard) playerBoard.clone();
        TextArea textArea;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (playerBoard.getBoard(y, x).getFieldValue()
                        != 0) {
                    textArea = new TextArea(String.valueOf(playerBoard.getBoard(y, x).getFieldValue()));
                    textArea.setEditable(false);
                    textArea.setFont(Font.font(14));
                    obszarSudoku.add(textArea, x, y);

                } else {
                    TextArea textArea1 = new TextArea();
                    textArea1.setFont(Font.font(14));
                    textArea1.setStyle("-fx-background-color: red");
                    obszarSudoku.add(textArea1, x, y);

                }
            }
        }

    }

    public void zapiszGre() throws GameBuildFailException, CantSaveException {

        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileSudokuBoardDao;
        try {
        fileSudokuBoardDao = factory.getFileDao("save");
            if (playerBoard == null) {
                throw new CantSaveException(resourceBundle.getString("cantSave"));
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    TextArea tmp = (TextArea) getNodeByRowColumnIndex(i, j, obszarSudoku);

                    if (tmp.getText() != "" && tmp.getText().chars().allMatch(Character::isDigit)
                            && Integer.valueOf(tmp.getText()) >= 1
                            && Integer.valueOf(tmp.getText()) <= 9) {
                        playerBoard.setBoard(i, j, Integer.valueOf(tmp.getText()));
                    } else {
                        playerBoard.setBoard(i, j, 0);
                    }
                }
            }
            List<SudokuBoard> sudokuBoardList = new ArrayList<>();
            sudokuBoardList.add(playerBoard);
            sudokuBoardList.add(playerBoardClone);
          fileSudokuBoardDao.saveBoards(sudokuBoardList);
        } catch (CantSaveException e) {
            logger.error(resourceBundle.getString("cantSave"));
            throw new CantSaveException(resourceBundle.getString("cantSave"));
        }
    }

    public void sprawdzPlansze() {
        SudokuBox box;
        SudokuColumn cl;
        SudokuRow rw;
        boolean boxFlag = true;
        boolean clFlag = true;
        boolean rwFlag = true;
        boolean zeroFlag = false;
        for (int i = 0;i < 9;i++) {
            for (int j = 0;j < 9;j++) {
                TextArea tmp = (TextArea) getNodeByRowColumnIndex(i,j,obszarSudoku);
                if (tmp.getText() != "" && tmp.getText().chars().allMatch(Character::isDigit)
                        && Integer.valueOf(tmp.getText()) >= 1
                        && Integer.valueOf(tmp.getText()) <= 9) {
                    playerBoard.setBoard(i, j, Integer.valueOf(tmp.getText()));
                   } else {
                    playerBoard.setBoard(i, j, 0);
                    zeroFlag = true;
                }
            }
        }
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                box = playerBoard.getBox(x, y);
                if (!box.verify()) {
                    boxFlag = false;
                    break;
                }
            }
        }
        for (int x = 0;x < 9;x++) {
                cl = playerBoard.getColumn(x);

                if (!cl.verify()) {
                    clFlag = false;
                    break;
                }
        }
        for (int x = 0;x < 9;x++) {
            rw = playerBoard.getRow(x);
            if (!rw.verify()) {
                rwFlag = false;
                break;
            }
        }
        if (boxFlag && rwFlag && clFlag && zeroFlag) {
            twynik.setText(resourceBundle.getString("notFullyCorrect"));
        } else if (boxFlag && rwFlag && clFlag) {

            twynik.setText(resourceBundle.getString("correctYouWon"));
        } else {
            twynik.setText(resourceBundle.getString("boardErrors"));
        }
    }

    public Node getNodeByRowColumnIndex(final int row, final int column,GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public void wczytajGre() throws MissingSaveException, GameBuildFailException {

        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileSudokuBoardDao;
        File file = new File("save.txt");
        if (!file.exists()) {
            throw new MissingSaveException(resourceBundle.getString("lackOfSaveFile"));
        } else {
            fileSudokuBoardDao = factory.getFileDao("save");

            playerBoard = null;
            playerBoard = fileSudokuBoardDao.loadBoards().get(0);
            playerBoardClone = fileSudokuBoardDao.loadBoards().get(1);

            TextArea textArea;
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (playerBoardClone.getBoard(y, x).getFieldValue() != 0) {
                        textArea = new TextArea(String.valueOf(playerBoard.getBoard(y, x).getFieldValue()));
                        textArea.setEditable(false);
                        textArea.setFont(Font.font(14));
                        obszarSudoku.add(textArea, x, y);

                    } else if (playerBoardClone.getBoard(y,x).getFieldValue() == 0
                            && playerBoard.getBoard(y,x).getFieldValue() != 0
                            && playerBoard.getBoard(y, x).getFieldValue() > 0
                            && playerBoard.getBoard(y, x).getFieldValue() < 10) {
                        textArea = new TextArea(String.valueOf(playerBoard.getBoard(y, x).getFieldValue()));
                        textArea.setFont(Font.font(14));
                        textArea.setStyle("-fx-background-color: red");
                        obszarSudoku.add(textArea, x, y);
                    } else {
                        TextArea textArea1 = new TextArea();
                        textArea1.setFont(Font.font(14));
                        textArea1.setStyle("-fx-background-color: red");
                        obszarSudoku.add(textArea1, x, y);
                    }
                }
            }
        }

    }

    @FXML
    public void changeLanguage(String temp) {
        resourceBundle = ResourceBundle.getBundle("bundle_" + temp);
        bzapiszgre.setText(resourceBundle.getString("saveGame"));
        bsprawdz.setText(resourceBundle.getString("check"));

    }
}
