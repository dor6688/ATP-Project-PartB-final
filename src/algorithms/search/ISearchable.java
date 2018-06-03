package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;

public interface ISearchable {
    /**
     *
     * @return the start position
     */
    MazeState getStartState();

    /**
     *
     * @return the end position
     */
    MazeState getGoalState();

    /**
     *
     * @param s the current state
     * @param visited a list of all visited nodes
     * @return list of all availabe states to move to
     */
    ArrayList<MazeState> getAllPossibleStates(MazeState s, HashSet<String> visited);

    void setVisit(int row,int col,int value);

    int getVisit(int row,int col);

    // print maze
    void print();

    // get row's maze
    int getRow();

    // get col's maze
    int getCol();


}