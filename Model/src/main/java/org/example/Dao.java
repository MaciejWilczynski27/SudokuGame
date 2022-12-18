package org.example;

import java.util.List;

public interface Dao<T> {

      T read() throws  DataCorruptException, GameBuildFailException;

      void write(T obj) throws DataCorruptException, GameBuildFailException;

    void saveBoards(List<T> list)throws GameBuildFailException;

    List<T> loadBoards() throws GameBuildFailException;
}
