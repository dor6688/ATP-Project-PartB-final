package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class SearchableMaze implements ISearchable {

    Maze searchableMaze;
    MazeState startPosition;
    MazeState endPosition;

    /**
     * Constructor
     * @param maze
     */
    public SearchableMaze(Maze maze){
        searchableMaze = maze;
        startPosition = new MazeState(maze.getStartPosition().getRowIndex(),maze.getStartPosition().getColumnIndex());
        endPosition = new MazeState(maze.getGoalPosition().getRowIndex(),maze.getGoalPosition().getColumnIndex());

    }


    /**
     *
     * @param s the current state
     * @param x number of cells to jump in rows
     * @param y number of cells to jump in coll
     * @param visited hash set of cell which have been visited
     * @param HowToMove which way to move
     * @return whether its possible to move to the given state
     */
    private boolean checkNewState(MazeState s, int x , int y, HashSet<String> visited,String HowToMove){
        MazeState tmp = new MazeState();
        if (s.currPosX + x >= 0 && s.currPosY + y >= 0
                && s.currPosX + x < searchableMaze.getRow() && s.currPosY + y < searchableMaze.getCol() &&
                searchableMaze.getStatus(s.currPosX + x, s.currPosY + y) == 0 ) {
            tmp.copyMazeState(s);
            tmp.updateState(s.currPosX + x, s.currPosY + y);
            if(HowToMove=="normal" || HowToMove == "diagonal")
                if(!visited.contains(tmp.toString()))
                    return true;
            if(HowToMove=="normalToDiagonal")
                if(!tmp.getArrived())
                    return true;
            return false;
        }
        else
            return false;
    }


    /**
     *
     * @param checkOption array of all available states
     * @param s the current state
     * @param x number of cells to jump in rows
     * @param y number of cells to jump in colls
     * @param howToMove which way to move
     */
    private void addNewOption(ArrayList<MazeState>checkOption,MazeState s, int x, int y,String howToMove){
        MazeState newState = new MazeState();
        newState.copyMazeState(s);
        newState.updateState(newState.currPosX+x, newState.currPosY+y);
        //sets the cost of diagonal move to 2 and normal to 3
        if(howToMove == "diagonal")
            newState.costToMove = 15+s.getCost();
        else
            newState.costToMove = 10+s.getCost();
        checkOption.add(newState);

    }

    @Override
    /**
     * returns all the states that are available to proceed
     */
    public ArrayList<MazeState> getAllPossibleStates(MazeState s, HashSet<String> visited) {
        ArrayList<MazeState>checkOption = new ArrayList<>();
        for(int x = -1 ; x < 2 ; x++){
            for(int y = -1 ; y < 2 ; y++){
                if(x!=y && (x==0 || y==0)) {
                    if (checkNewState(s,x,y,visited,"normal")) {
                        addNewOption(checkOption,s,x,y,"normal");
                    }
                }
                // diagonal move
                else{
                    if(x!=0 && y!=0){
                        if(checkNewState(s,0,y,visited,"normalToDiagonal") || checkNewState(s,x,0,visited,"normalToDiagonal")) {
                            if(checkNewState(s,x,y,visited,"diagonal")) {
                                addNewOption(checkOption,s,x,y,"diagonal");
                            }
                        }
                    }
                }
            }
        }
        return checkOption;
    }

    //////// Getters ///////

    /**
     * @return Start Position of the maze
     */
    @Override
    public MazeState getStartState() {
        return startPosition;
    }

    /**
     * @return Goal Position of the maze
     */
    @Override
    public MazeState getGoalState() {
        return endPosition;
    }

    @Override
    public int getCol() {
        return searchableMaze.getCol();
    }

    @Override
    public int getRow() {
        return searchableMaze.getRow();
    }

    //returns the value of a given state
    public int getVisit(int row,int col){
        return searchableMaze.getStatus(row,col);

    }

    // sets the states in the given value
    public void setVisit(int row,int col,int value){
        searchableMaze.setNewStatus(row,col,value);
    }

    // print the maze
    public void print(){
        searchableMaze.print();
    }


}