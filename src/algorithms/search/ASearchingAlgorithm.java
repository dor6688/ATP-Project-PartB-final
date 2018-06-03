package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    private  boolean showPath = false;

    protected HashSet<String> visited;
    protected Solution sol;

    /**
     * Constructor
     */
    public ASearchingAlgorithm(){
        visited = new HashSet<>();
        sol = new Solution();
    }

    /**
     *
     * @param iSearchable
     * @return
     */
    public abstract Solution solve(ISearchable iSearchable);

    /**
     * @return the name of the Algorithm
     */
    public abstract String getName();

    /**
     * @return Number Of Nodes Evaluated
     */
    public abstract int getNumberOfNodesEvaluated();

    /**
     * adding the right states to the solution starting from tmp
     * @param iSearchable the method of searching the solution
     * @param tmp the current state
     * @param s the solution of the maze
     */
    protected void addToSolution(ISearchable iSearchable, MazeState tmp, Solution s){
        ArrayList<MazeState> sol = reversePath(iSearchable,tmp);
        s.addToSolution(iSearchable.getStartState());
        for (MazeState m : sol) {
            s.addToSolution(m);
        }
    }

    /**
     *
     * @param iSearchable the method of searching the solution
     * @param tmp the current state
     * @return list of all the states in the solution starting from the end until reaches the start state
     */
    protected ArrayList<MazeState> reversePath(ISearchable iSearchable, MazeState tmp) {
        int i=0;
        ArrayList<MazeState> sol = new ArrayList<>();
        while(tmp.currPosX != iSearchable.getStartState().currPosX || tmp.currPosY != iSearchable.getStartState().currPosY){
            sol.add(i,tmp);
            if(showPath) {
                iSearchable.setVisit(tmp.currPosX, tmp.currPosY, 4);
            }
            tmp = (MazeState)tmp.getParents();
        }
        if(showPath) {
            iSearchable.print();
        }
        clear(iSearchable);
        return sol;
    }
    //if cell has been visited
    private void clear(ISearchable iSearchable){
        for(int i=0;i<iSearchable.getRow();i++){
            for(int j=0;j<iSearchable.getCol();j++){
                if(iSearchable.getVisit(i,j) == 4 ||iSearchable.getVisit(i,j) == 2)
                    iSearchable.setVisit(i,j,0);
            }
        }
    }

}