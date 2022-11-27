package org.example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class SudokuShape implements Serializable {

    private List<SudokuField> fields = Arrays.asList(new SudokuField[9]);

    public SudokuShape() {
        AtomicInteger a = new AtomicInteger(0);
        fields.forEach(f -> fields.set(a.getAndIncrement(),new SudokuField()));
    }

    public int get(int x) {
        return fields.get(x).getFieldValue();
    }


    public void set(int x, int value) {
        this.fields.get(x).setFieldValue(value);
        this.verify();
    }

    public boolean verify() {
        boolean[] flags = new boolean[9];
        AtomicInteger counter = new AtomicInteger(0);

        fields.forEach(f -> {
            if (f.getFieldValue() == 0) {
                counter.incrementAndGet();

            } else {
                flags[f.getFieldValue() - 1] = true;
            }
        });
        for (boolean flag : flags) {
            if (flag) {
                counter.incrementAndGet();
            }
        }
        if (counter.get() == 9) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SudokuShape{"
                +
                "fields=" + fields
                +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SudokuShape that)) {
            return false;
        }
        return fields.equals(that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }
}
