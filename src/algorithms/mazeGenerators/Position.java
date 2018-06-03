package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
    int row;
    int col;
    Position parent;

    /**
     * Constructor
     * @param row
     * @param col
     * @param position
     */
    public Position(int row, int col, Position position){
        this.row = row;
        this.col = col;
        parent = position;
    }

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * @return row Index
     */
    public int getRowIndex(){
        return row;
    }

    /**
     * @return col Index
     */
    public int getColumnIndex(){
        return col;
    }


    /**
     * String of the current position
     * @return {row,col}
     */
    @Override
    public String toString(){
        return "{"+row+","+col+"}";
    }


    public Position opposite() {
        if (this.compareToRow(parent) != 0)
            return new Position(this.row + this.compareToRow(parent), this.col, this);
        if (this.compareToCol(parent) != 0)
            return new Position(this.row, this.col + this.compareToCol(parent), this);
        return null;
    }

    public int compareToRow(Object o1){
        if(this.row == ((Position) o1).row)
            return 0;
        else{
            if(this.row > ((Position) o1).row)
                return 1;
            else
                return -1;
        }
    }

    public int compareToCol(Object o1){
        if(this.col == ((Position) o1).col)
            return 0;
        else{
            if(this.col > ((Position) o1).col)
                return 1;
            else
                return -1;
        }
    }

}
