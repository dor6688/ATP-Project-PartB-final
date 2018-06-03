package algorithms.mazeGenerators;

public interface IMazeGenerator {

    /**
     * Build the the maze by the row and the col
     * @param row
     * @param col
     * @return Maze
     */
    Maze generate(int row,int col);

    /**
     * Calculate the time that took to the algorithm to build the maze
     * @param row
     * @param col
     * @return long - the time
     */
    long measureAlgorithmTimeMillis(int row, int col);


}
