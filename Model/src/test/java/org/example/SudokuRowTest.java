package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuRowTest {
    private SudokuSolver bc = new BacktrackingSudokuSolver();
    private SudokuBoard board = new SudokuBoard(bc);
    @Test
    public void toStringTest(){
        board.solveGame();
        String text = "SudokuShape{fields=[";
        for (int i = 0;i < 9;i++){
            text += "SudokuField{value="+board.getRow(2).get(i)+"}";
            if (i != 8) {
                text +=", ";
            }
        }
        text += "]}";
        assertEquals(board.getRow(2).toString(),text);
    }
    @Test
    public void equalsTest(){
        board.solveGame();
        SudokuRow a = board.getRow(3);

            assertTrue(board.getRow(2).equals(board.getRow(2)));
            assertTrue(a.equals(a));
            assertFalse(board.getRow(2).equals(6));
            assertFalse(board.getRow(2).equals(board.getRow(5)));
            assertFalse(board.getRow(2).equals(null));

    }
    @Test
    public void hashCodeTest(){
        SudokuRow s = board.getRow(1);
        board.solveGame();
        assertNotEquals(s.hashCode(),board.getRow(1).hashCode());
    }
}
