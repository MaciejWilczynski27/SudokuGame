package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private String filename;

    public FileSudokuBoardDao(String filename) {
        this.filename = filename + ".txt";
    }

    @Override
    public SudokuBoard read() throws DataCorruptException, GameBuildFailException {
    SudokuBoard s;
        try (FileInputStream fileInputStream = new FileInputStream(filename);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            s = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new DataCorruptException("dataError",e);
        } catch (IOException e) {
            throw new GameBuildFailException("loadGameError",e);
        }
        return s;

    }

    @Override
    public void write(SudokuBoard obj) throws GameBuildFailException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
             objectOutputStream.writeObject(obj);

        } catch (IOException e) {
            throw new GameBuildFailException("buildGameError",e);
        }
    }

    @Override
    public void close() {

    }
}
