package org.example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;




public class SudokuShape implements Serializable,Cloneable {

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
        return new ToStringBuilder(this)
                .append("fields", fields)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SudokuShape that = (SudokuShape) o;

        return new EqualsBuilder().append(fields, that.fields).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(fields).toHashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        SudokuShape shape = new SudokuShape();
            for(int i=0;i<0;i++) {
                shape.set(i,this.get(i));
            }

        return shape;
    }
}
