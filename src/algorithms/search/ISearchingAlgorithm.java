package algorithms.search;


public interface ISearchingAlgorithm {
    /**
     * Algorithm that solve the maze
     * @param iSearchable
     * @return
     */
    Solution solve(ISearchable iSearchable);

    /**
     * Get the name of the Algorithm
     * @return The name of the Algorithm
     */
    String getName();

    /**
     * The number of nodes that we check
     * @return number of the nodes that evaluated during
     */
    int getNumberOfNodesEvaluated();


}