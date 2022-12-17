package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {
    private final SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private SudokuBoard sudokuBoard = new SudokuBoard(solver);

    private Dao<SudokuBoard> fileSudokuBoardDao;
    private SudokuBoard sudokuBoardSecond;


    @Test
    public void writeReadTest() throws DataCorruptException, GameBuildFailException {
        sudokuBoard.solveGame();
        fileSudokuBoardDao = factory.getFileDao("writeReadTest");
        fileSudokuBoardDao.write(sudokuBoard);
        sudokuBoardSecond = fileSudokuBoardDao.read();
        assertEquals(fileSudokuBoardDao.read(), fileSudokuBoardDao.read());

    }

    @Test(expected = RuntimeException.class)
    public void readIOExceptionTest() throws DataCorruptException, GameBuildFailException {

        fileSudokuBoardDao = factory.getFileDao("readIOExceptionTest");
        fileSudokuBoardDao.read();
    }

    @Test(expected = RuntimeException.class)
    public void writeIOExceptionTest() {
        try {
            fileSudokuBoardDao = factory.getFileDao("?");
            fileSudokuBoardDao.write(sudokuBoard);
        } catch (DataCorruptException e) {

        } catch (GameBuildFailException e) {

        }
    }


    @Test
        public void getFileDaoTest() {
            SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
            assertNotNull(factory.getFileDao("getFileDaoTest"));
        }

        @Test
    public void saveLoadBoardTest() throws GameBuildFailException {
            sudokuBoard.solveGame();
            sudokuBoardSecond = (SudokuBoard) sudokuBoard.clone();
            fileSudokuBoardDao = factory.getFileDao("writeReadTest");
            fileSudokuBoardDao.saveBoards(sudokuBoard,sudokuBoardSecond);
            List<SudokuBoard> list;
            list = fileSudokuBoardDao.loadBoards();
            assertTrue(list.get(0).equals(list.get(1)));
        }
    }

