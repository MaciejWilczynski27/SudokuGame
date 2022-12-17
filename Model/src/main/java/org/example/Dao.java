package org.example;

public interface Dao<T> {

      T read() throws  DataCorruptException, GameBuildFailException;

      void write(T obj) throws DataCorruptException, GameBuildFailException;
}
