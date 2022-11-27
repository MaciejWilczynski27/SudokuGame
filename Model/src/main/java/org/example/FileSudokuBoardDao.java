package org.example;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    public String fileName;

    FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    public SudokuBoard read() throws IOException, ClassNotFoundException {
        // Dao<SudokuBoard> s = new FileSudokuBoardDao("plik");
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        FileInputStream fileInput = new FileInputStream(fileName);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        objectInput.readObject();

        return board;
    }

    public void write(SudokuBoard board) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(fileName);
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);


    }
}
