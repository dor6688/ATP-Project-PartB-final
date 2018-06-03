package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Measure time millis in generate the maze
     *
     * @param row
     * @param col
     * @return time
     */
    public long measureAlgorithmTimeMillis(int row, int col){
        long startTime;
        long finishTime;
        startTime = System.currentTimeMillis(); // before generating
        generate(row, col);
        finishTime = System.currentTimeMillis();// after generating
        return (finishTime-startTime);
    }

    /**
     * Build the frame construction
     * Mark the Start and Exit position
     *
     * @param myMaze
     */
    protected void fillBoard(Maze myMaze) {

        for(int i=0;i<=myMaze.getRow()-1;i++){
            myMaze.setNewStatus(i,myMaze.getCol()-1,1);
        }
        for(int i=0;i<=myMaze.getCol()-1;i++){
            myMaze.setNewStatus(myMaze.getRow()-1,i,1);
        }
        for(int i=0;i<=myMaze.getRow()-1;i++){
            myMaze.setNewStatus(i,0,1);
        }
        for(int i=0;i<=myMaze.getCol()-1;i++){
            myMaze.setNewStatus(0,i,1);
        }
        // Start S
        if(myMaze.getStartPosition().getRowIndex()==0)
            myMaze.setNewStatus(1,myMaze.getStartPosition().getColumnIndex(),0);
        else
            myMaze.setNewStatus(myMaze.getStartPosition().getRowIndex(),1,0);
        // End E
        if(myMaze.getGoalPosition().getRowIndex()==myMaze.getRow()-1)
            myMaze.setNewStatus(myMaze.getRow()-1,myMaze.getGoalPosition().getColumnIndex(),0);
        else
            myMaze.setNewStatus(myMaze.getGoalPosition().getRowIndex(),myMaze.getCol()-1,0);
    }

    /**
     * check if the size of board is grater than 4
     * unless make default 10
     * @param size
     * @return size or 10
     */
    protected int checkMazeSize(int size){
        if(size < 4)
            return  10;
        return size;
    }
}
