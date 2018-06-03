package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator{
    Maze myMaze;

    public SimpleMazeGenerator(){
    }

    /**
     * building the maze
     * @param row
     * @param col
     * @return complete maze
     */
    @Override
    public Maze generate(int row, int col) {
        row = checkMazeSize(row); // check if isn't less than 4
        col = checkMazeSize(col); // check if isn't less than 4
        myMaze = new Maze(row, col,false);
        buildSolution(myMaze); // build one solution
        fillBoard(myMaze); // make board
        remove2(myMaze); // remove the solution
        return myMaze;
    }

    /**
     * Put random walls in the maze
     *
     * @param maze
     */
    private void setRandomWalls(Maze maze){
        // maximum walls in the maze
        int maxWalls = maze.getGoalPosition().getRowIndex()*maze.getGoalPosition().getColumnIndex()/2;
        int counter = 0;
        while(counter < maxWalls){
            int randRow = (int) (Math.random()*maze.getRow());
            int randCol = (int) (Math.random()*maze.getCol());
            if(maze.getStatus(randRow,randCol) == 0){
                maze.setNewStatus(randRow,randCol,1);
                counter++;
            }
        }
    }

    /**
     * Check if position is in the board
     *
     * @param maze
     * @param row
     * @param col
     * @return true if is inside the board, else false
     */
    private boolean checkBoard(Maze maze , int row, int col){
        if(row<maze.getRow()-1 && col<maze.getCol()-1&& row!=0&&col!=0)
            return true;
        return false;
    }

    /**
     * Mark each point that selected in the solution
     *
     * @param length
     * @param row
     * @param col
     * @param maze
     * @param rowORcol
     */
    private void printSol(int length, int row, int col, Maze maze, String rowORcol){
        if(checkBoard(maze,row,col))
            maze.setNewStatus(row, col, 2);
        while(length>1){
            if(rowORcol == "right")
                col++;
            if(rowORcol == "down")
                row++;
            if(rowORcol == "left")
                col--;
            if(rowORcol == "up")
                row--;
            if(checkBoard(maze,row,col))
                maze.setNewStatus(row, col, 2);
            length--;
        }
    }

    /**
     * The range to build the path is maximum 3 blocks
     *
     * @param maze
     * @param indexRow
     * @param indexCol
     * @param which
     * @return the range
     */
    private int range(Maze maze, int indexRow,int indexCol,String which){
        int range;
        int rangeR ;
        if(which=="col") {
            range = (maze.getCol() - 2 - indexCol);
            rangeR = Math.max((int)Math.random()*(maze.getCol()-1 - indexCol) , 2);
            if(range>1)
                return Math.min(rangeR,3);
            else{
                if(maze.getCol()-1 - indexRow == 2 ||maze.getRow() - 1 - indexRow == 1)
                    return 2;
            }
        }
        if(which=="row") {
            range = (maze.getRow() - 2 - indexRow);
            rangeR = Math.max((int)Math.random()*(maze.getRow()-1 - indexRow), 2);
            if (range > 1)
                return Math.min(rangeR, 3);
            else {
                if (maze.getCol() - 1 - indexCol == 2 || maze.getCol() - 1 - indexCol == 1 )
                    return 2;
            }
        }
        return 0;
    }

    /**
     * update row or col after adding to the solution path
     *
     * @param maze
     * @param indexRow
     * @param indexCol
     * @param colORrow
     * @param rightORdown
     * @param rightORdownINT
     * @return the update row or the update col
     */
    private int updateRowColAfterAddToSol(Maze maze,int indexRow,int indexCol,String colORrow, String rightORdown, int rightORdownINT){
        int length = range(maze,indexRow,indexCol,colORrow);
        printSol(length,indexRow,indexCol,maze,rightORdown);
        if(rightORdownINT==1){
            indexCol = indexCol+length-1;
            if(indexCol>maze.getCol()-2)
                indexCol=maze.getCol()-2;
            return indexCol;
        }
        else{
            indexRow = indexRow+length-1;
            if(indexRow>maze.getRow()-2)
                indexRow=maze.getRow()-2;
            return indexRow;
        }
    }

    /**
     * Build a solution to ensure at least one maze solution
     *
     * @param maze
     */
    private void buildSolution(Maze maze){
        int indexRow = 1, indexCol =1;
        int rightORdown ;
        while(indexRow!=maze.getRow()-2 || indexCol!=maze.getCol()-2) {
            rightORdown = (int)(Math.random()*2);
            //right
            if (rightORdown == 1)
                indexCol = updateRowColAfterAddToSol(maze,indexRow,indexCol,"col","right",rightORdown);
            //down
            else
                indexRow = updateRowColAfterAddToSol(maze,indexRow,indexCol,"row","down",rightORdown);
        }
        setRandomWalls(maze);

    }

    /**
     * Remove the mark in the path
     *
     * @param m
     */
    private void remove2(Maze m){
        for (int i=0;i<m.getRow();i++){
            for(int j=0;j<m.getCol();j++){
                if(m.getStatus(i,j)==2)
                    m.setNewStatus(i,j,0);
            }
        }
    }

    /**
     * Example Maze for test
     *
     * @return Maze
     */
    public Maze mazeForTest(){
        myMaze = new Maze(13,8,false);
        myMaze.makeExample();
        fillBoard(myMaze);
        return myMaze;
    }

    /**
     * Example of a maze without solution
     * @return Maze
     */
    public Maze mazeWithNoSol(){
        myMaze = new Maze(10,10,false);
        myMaze.makeMazeWithNoSolution();
        fillBoard(myMaze);
        return myMaze;
    }
}
