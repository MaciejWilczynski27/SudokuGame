package org.example;


import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {
    private final SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private SudokuBoard sudokuBoard = new SudokuBoard(solver);

    private Dao<SudokuBoard> fileSudokuBoardDao;
    private SudokuBoard sudokuBoardSecond;


    @Test
    public void writeReadTest() throws ClassNotFoundException, IOException {
        sudokuBoard.solveGame();
        fileSudokuBoardDao = factory.getFileDao("writeReadTest");
        fileSudokuBoardDao.write(sudokuBoard);
        sudokuBoardSecond = fileSudokuBoardDao.read();


        assertEquals(fileSudokuBoardDao.read(), fileSudokuBoardDao.read()); // ???

    }

    @Test(expected = RuntimeException.class)
    public void readIOExceptionTest() throws ClassNotFoundException, IOException {

        fileSudokuBoardDao = factory.getFileDao("readIOExceptionTest");
        fileSudokuBoardDao.read();
    }

    @Test(expected = RuntimeException.class)
    public void writeIOExceptionTest() throws IOException, ClassNotFoundException {
        fileSudokuBoardDao = factory.getFileDao("?");
        fileSudokuBoardDao.write(sudokuBoard);
    }


    @Test
        public void getFileDaoTest() {
            SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
            assertNotNull(factory.getFileDao("getFileDaoTest"));
        }

    }

