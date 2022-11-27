package org.example;

import java.io.IOException;

public interface Dao<T> {

      T read() throws ClassNotFoundException,IOException;

      void write(T obj) throws IOException,ClassNotFoundException;
}
