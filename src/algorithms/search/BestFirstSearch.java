package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch(){
        super();
        queue = new PriorityQueue<MazeState>(((o1, o2) -> o1.costToMove-o2.costToMove));
    }

    /**
     * solve extend BFS
     * with priority queue
     *
     * @param iSearchable
     * @return
     */
    @Override
    public Solution solve(ISearchable iSearchable) {
        return super.solve(iSearchable);
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }

}