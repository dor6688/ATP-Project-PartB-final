package algorithms.search;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MazeState extends AState implements Serializable {
    int currPosX;
    int currPosY;
    ArrayList son;
    boolean visited;

    /**
     * Constructor
     * @param row
     * @param col
     */
    public MazeState(int row , int col){
        super();
        currPosX = row;
        currPosY = col;
        visited = false;
        son = new ArrayList();
    }

    /**
     * Constructor default
     */
    public MazeState() {
        super();
        currPosX = -1;
        currPosY = -1;
        visited = false;
        son = new ArrayList();
    }

    /**
     * updates the current state
     * @param newRow
     * @param newCol
     */
    public void updateState(int newRow, int newCol){
        currPosX = newRow;
        currPosY = newCol;
    }

    /**
     * @return list of all the current state sons
     */
    public List getSons(){
        return son;
    }
    
    /**
     * @return the previous node of the current state
     */
    public AState getParents(){
        return cameFrom;
    }

    /**
     * Copy oldState to newState
     * @param oldState
     */
    public void copyMazeState(MazeState oldState){
        this.currPosX = oldState.currPosX;
        this.currPosY= oldState.currPosY;
        this.visited = oldState.visited;
    }

    /**
     * @return toString
     */
    public String toString(){
        return "{" + currPosX + "," + currPosY + "}";
    }

    ////// Getters /////

    /**
     * @return arrived TRUE or FALSE
     */
    public boolean getArrived(){
        return arrived;
    }

    /**
     * @return Cost to move
     */
    public int getCost(){
        return costToMove;
    }

    ///// Setters /////

    /**
     * Set new array with son's
     * @param neighbor - list of son's
     */
    public void setSon(List neighbor){
        son.addAll(neighbor);
    }

    /**
     * Set new Parent
     * @param parents
     */
    public void setParent(MazeState parents){
        if(cameFrom == null)
            cameFrom = parents;
    }
    /**
     * sets the current state as arrived
     */
    public void setArrived(){
        arrived = true;
    }


}