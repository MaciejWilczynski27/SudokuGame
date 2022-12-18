package org.example;

import java.util.List;

public interface Dao<T> {

      T read() throws  ProblemWithFileException;

      void write(T obj) throws ProblemWithFileException;

    void saveBoards(List<T> list) throws ProblemWithFileException;

    List<T> loadBoards() throws ProblemWithFileException;
}
