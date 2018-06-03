package algorithms.search;
import java.util.*;


/**
 * BreadthFirstSearch - BFS
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected int nodeCounter;
    protected Queue<MazeState> queue;
    MazeState currentStat;
    private boolean draw = false;


    /**
     * Constructor
     */
    public BreadthFirstSearch(){
        super();
        nodeCounter = 0;
        queue = new LinkedList<>();

    }

    /**
     * solve
     * add to Queue every position
     * until State isn't the end move to all the neighbors
     *
     *
     * @param iSearchable
     * @return Solution - The path to the exit
     */
    @Override
    public Solution solve(ISearchable iSearchable) {
        if(iSearchable instanceof ISearchable) {
            queue.add(iSearchable.getStartState());// add the Start Position
            while (!queue.isEmpty()) {
                currentStat = queue.poll();
                currentStat.setArrived(); // mark current stat
                //draw in the maze
                if(draw) {
                    iSearchable.setVisit(currentStat.currPosX, currentStat.currPosY, 2);
                    iSearchable.print();
                }
                // check if you arrived to the End
                if (currentStat.currPosX == iSearchable.getGoalState().currPosX && currentStat.currPosY == iSearchable.getGoalState().currPosY) {
                    addToSolution(iSearchable, currentStat, sol); //insert to Solution sol the correct path from Start to End
                    break;
                }
                currentStat.setSon(iSearchable.getAllPossibleStates(currentStat, visited));
                ArrayList allTheOptions = new ArrayList();
                allTheOptions.addAll(currentStat.getSons()); // Get all the Neighbors
                // over all the son's
                while (!allTheOptions.isEmpty()) {
                    int i=0;
                    MazeState MoveCurrent = (MazeState) allTheOptions.get(i);
                    allTheOptions.remove(i); // delete him from the list
                    MoveCurrent.setParent(currentStat); // update the Parents
                    if (!visited.contains(MoveCurrent.toString())) {
                        queue.add(MoveCurrent);
                        visited.add(MoveCurrent.toString()); // insert position to visited - close
                    }
                    i++;
                }
            }
            nodeCounter = visited.size() + 1; // get Number Of Nodes Evaluated
        }
        return sol;
    }

    /**
     * The name of the algorithm
     * @return String BreadthFirstSearch
     */
    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    /**
     * Number Of Nodes Evaluated
     * @return int - nodeCounter
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return nodeCounter;
    }
}