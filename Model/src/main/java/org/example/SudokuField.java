package org.example;


import java.util.Objects;

public class SudokuField  {

    private int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value1) {
        value = value1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return value == that.value;
    }

    @Override
    public String toString() {
        return "SudokuField{"
                +
                "value=" + value
                +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
