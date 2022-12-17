package org.example;

import java.util.List;

public interface Dao<T> {

      T read() throws  DataCorruptException, GameBuildFailException;

      void write(T obj) throws DataCorruptException, GameBuildFailException;

    void saveBoards(T obj1, T obj2)throws GameBuildFailException;
    List<T> loadBoards() throws GameBuildFailException;
}
