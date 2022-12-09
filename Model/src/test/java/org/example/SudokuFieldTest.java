package org.example;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {
    SudokuField field1 = new SudokuField();
    SudokuField field2 = new SudokuField();
    @Test
    public void hashCodeEqualsTest() {
        assertEquals(field1.hashCode(), field2.hashCode());
        assertEquals(equals(field1), equals(field2));
        field1.setFieldValue(1);
        field2.setFieldValue(1);
        assertEquals(equals(field1), equals(field2));
        assertEquals(field1.hashCode(), field2.hashCode());
        field1.setFieldValue(5);
        assertEquals(field1.hashCode(), 634);
        field2.setFieldValue(8);
        assertEquals(field2.hashCode(), 637);
        assertFalse(equals(field1) != equals(field2));


        assertTrue(field1.equals(field1));
        SudokuShape s1 = new SudokuShape();
        assertEquals(field1.equals(s1), false);
        assertFalse(field1.equals(null));
    }

    @Test
    public void toStringTest() {
        assertEquals(field1.toString(), field1.toString());
        field1.setFieldValue(2);
        assertEquals(field1.toString(), field1.toString());
    }
}
