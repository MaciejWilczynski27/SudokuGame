package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void saveBoards(List<SudokuBoard> list) throws GameBuildFailException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            for (int i = 0; i< list.size();i++) {
                objectOutputStream.writeObject(list.get(i));
            }
        } catch (IOException e) {
            throw new GameBuildFailException("buildGameError", e);
        }
    }
    public List<SudokuBoard> loadBoards() throws GameBuildFailException{
        List<SudokuBoard> list= new ArrayList<>();
        try (FileInputStream fileInputStream= new FileInputStream(this.filename);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            try {
                while (true) {
                    list.add((SudokuBoard) objectInputStream.readObject());
                }
            } catch (EOFException e) {
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new GameBuildFailException("buildGameError", e);
        }
        return list;
    }
}
