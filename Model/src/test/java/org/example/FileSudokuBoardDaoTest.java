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

    @Test(expected = GameBuildFailException.class)
    public void readGBFExceptionTest() throws DataCorruptException, GameBuildFailException {
        fileSudokuBoardDao = factory.getFileDao("readGBFExceptionTest");
        fileSudokuBoardDao.read();
    }
//    @Test(expected = DataCorruptException.class)
//    public void readDCExceptionTest() throws DataCorruptException, GameBuildFailException {
//        fileSudokuBoardDao = factory.getFileDao("readDCExceptionTest");
//
//
//        fileSudokuBoardDao.read();
//    }

    @Test(expected = GameBuildFailException.class)
    public void writeGBFExceptionTest() throws GameBuildFailException{
        try {
            fileSudokuBoardDao = factory.getFileDao("?");
            fileSudokuBoardDao.write(sudokuBoard);
        } catch (GameBuildFailException | DataCorruptException e) {
                throw new GameBuildFailException("writeTest",e);
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
            List<SudokuBoard> sudokuBoardList = new ArrayList<>();
            sudokuBoardList.add(sudokuBoard);
            sudokuBoardList.add(sudokuBoardSecond);
            fileSudokuBoardDao = factory.getFileDao("writeReadTest");
            fileSudokuBoardDao.saveBoards(sudokuBoardList);
            List<SudokuBoard> list = fileSudokuBoardDao.loadBoards();
            assertTrue(list.get(0).equals(list.get(1)));
            sudokuBoardSecond.setBoard(1,1,0);
            List<SudokuBoard> sudokuBoardList2 = new ArrayList<>();
            sudokuBoardList2.add(sudokuBoard);
            sudokuBoardList2.add(sudokuBoardSecond);
            fileSudokuBoardDao = factory.getFileDao("writeReadTest");
            fileSudokuBoardDao.saveBoards(sudokuBoardList2);
            List<SudokuBoard> list2= fileSudokuBoardDao.loadBoards();
            assertFalse(list2.get(0).equals(list2.get(1)));
        }
        @Test(expected = GameBuildFailException.class)
        public void saveBoardsGBFETest() throws GameBuildFailException{
            try {
                fileSudokuBoardDao = factory.getFileDao("?");
                sudokuBoard.solveGame();
                sudokuBoardSecond = (SudokuBoard) sudokuBoard.clone();
                List<SudokuBoard> sudokuBoardList = new ArrayList<>();
                sudokuBoardList.add(sudokuBoard);
                sudokuBoardList.add(sudokuBoardSecond);
                fileSudokuBoardDao.saveBoards(sudokuBoardList);
            } catch (GameBuildFailException e) {
                throw new GameBuildFailException("exception thrown correctly",e);

            }
        }

    @Test(expected = GameBuildFailException.class)
    public void loadBoardsGBFETest() throws GameBuildFailException{
        try {
            fileSudokuBoardDao = factory.getFileDao("?");
            fileSudokuBoardDao.loadBoards();
        } catch (GameBuildFailException e) {
            throw new GameBuildFailException("exception thrown correctly",e);
        }
    }
    }

