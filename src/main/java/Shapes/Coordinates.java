package Shapes;

import java.util.Objects;

public class Coordinates {
    private int row;
    private int col;

    public Coordinates(int col, int row){
        this.col = col;
        this.row = row;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public String toString(){
        return "(" + col + ":" + row + ")";
    }
}
