package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch {

    ASearchingAlgorithm best = new BestFirstSearch();
    SimpleMazeGenerator sm = new SimpleMazeGenerator();
    Maze exampleMaze = sm.mazeForTest();
    ISearchable searchable = new SearchableMaze(exampleMaze);



    @Test
    void inputNull() {
        Solution s =best.solve(null);
        assertEquals(0,s.sol.size());

    }

    @Test
    void WrongInput() {
        best.solve(searchable);
        assertNotEquals(0,best.getNumberOfNodesEvaluated());

    }

    @Test
    void getName() {
        assertEquals("BestFirstSearch",best.getName());
    }
}