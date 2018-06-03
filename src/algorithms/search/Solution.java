package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {

    ArrayList<AState> sol;

    public Solution(){
        sol = new ArrayList<>();
    }

    public ArrayList<AState> getSolutionPath(){
        return sol;
    }

    /**
     * add state to the solution
     * @param current
     */
    public void addToSolution(AState current){
        sol.add(current);
    }

    @Override
    public String toString() {
        return sol.size()+"";
    }
}
