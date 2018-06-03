package algorithms.search;
import java.util.ArrayList;
import java.util.Stack;

/**
 * DepthFirstSearch - DFS
 */
public class DepthFirstSearch extends ASearchingAlgorithm{

    int nodeCounter;
    boolean draw = false;
    MazeState current;
    Stack<MazeState> stack;

    /**
     * Constructor
     */
    public DepthFirstSearch(){
        super();
        nodeCounter = 0;
        stack = new Stack<>();
    }

    /**
     * solve
     * move until you stuck and find another way
     *
     * @param iSearchable
     * @return solution to the maze by dfs way
     */
    @Override
    public Solution solve(ISearchable iSearchable){
        if(iSearchable instanceof ISearchable) {
            //pushing the start to the stack
            stack.push(iSearchable.getStartState());
            while (!stack.isEmpty()) {
                current = stack.pop();
                //marking as visited bt adding to hash array
                visited.add(current.toString());
                //draw in the maze
                if (draw) {
                    //setting as visited by setting the value to 2
                    iSearchable.setVisit(current.currPosX, current.currPosY, 2);
                    iSearchable.print();
                }
                //if we are at the end state setting as visited and done
                if (current.currPosX == iSearchable.getGoalState().currPosX && current.currPosY == iSearchable.getGoalState().currPosY) {
                    addToSolution(iSearchable, current, sol);
                    break;
                }
                //if its not the end yet
                current.setSon(iSearchable.getAllPossibleStates(current, visited));
                ArrayList listTmp = new ArrayList();
                listTmp.addAll(current.getSons());
                //moves to one of the sons randomly
                while (!listTmp.isEmpty()) {
                    int i = (int) (Math.random() * listTmp.size());
                    MazeState node = (MazeState) listTmp.get(i);
                    listTmp.remove(i);
                    node.setParent(current);
                    if (!visited.contains(node.toString())) {
                        stack.push(node);
                        visited.add(current.toString());
                    }
                }
            }
            nodeCounter = visited.size() + 1;
        }
        return sol;
    }
    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return nodeCounter;
    }

}