package algorithms.mazeGenerators;

import java.util.ArrayList;

import static java.lang.Math.random;

public class MyMazeGenerator extends AMazeGenerator {

    Position startPoint;
    ArrayList<Position> frontier;


    /**
     * Constructor
     */
    public MyMazeGenerator(){
        frontier = new ArrayList<>();
    }

    /**
     * building walls all over the maze
     * @param myMaze
     */
    private void fillAllBoardInWalls(Maze myMaze){
        for(int i = 0;i<myMaze.getRow();i++){
            for(int j=0;j<myMaze.getCol();j++){
                myMaze.setNewStatus(i,j,1);
            }
        }
    }

    /**
     * make the first point
     *
     * @param myMaze
     * @return
     */

    private Position createFirstPoint(Maze myMaze){
        if(myMaze.getStartPosition().getRowIndex()==0)
            return new Position(1,myMaze.getStartPosition().getColumnIndex(),null);
        else
            return new Position(myMaze.getStartPosition().getRowIndex(),1,null);
    }

    /**
     * Adding the first move
     *
     * @param myMaze
     * @return
     */

    private ArrayList<Position> addPointToFront(Maze myMaze){
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0)
                    continue;
                try {
                    if (myMaze.getStatus(startPoint.getRowIndex() + x, startPoint.getColumnIndex() + y) == 0)
                        continue;
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                }
                // add eligible points to frontier
                frontier.add(new Position(startPoint.getRowIndex() + x, startPoint.getColumnIndex() + y, startPoint));
            }
        }
        return frontier;
    }

    /**
     * building the maze
     * @param row
     * @param col
     * @return complete maze
     */
    @Override
    public Maze generate(int row, int col) {
        row = checkMazeSize(row);
        col = checkMazeSize(col);
        Maze myMaze = new Maze(row,col,true);
        //fill all in walls
        fillAllBoardInWalls(myMaze);
        // set the start position
        startPoint = createFirstPoint(myMaze);
        // iterate through direct neighbors of node
        myMaze.setNewStatus(startPoint.getRowIndex(),startPoint.getColumnIndex(),0);
        frontier = addPointToFront(myMaze);

        while (!frontier.isEmpty()) {
            // pick current node at random
            Position current = frontier.remove((int) (random() * frontier.size()));
            Position op = current.opposite();

            try {
                // if both node and its opposite are walls
                if(myMaze.getStatus(current.getRowIndex(),current.getColumnIndex())==1){
                    if(myMaze.getStatus(op.getRowIndex(),op.getColumnIndex())==1){
                        // open path between the nodes
                        openPath(myMaze,current,op,row,col);
                    }
                }
            } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
            }
            // if algorithm has resolved, mark end node
            if (frontier.isEmpty()){
                SetEndPosition(myMaze);
            }
        }
        //fillBoard(myMaze);
        return myMaze;
    }

    /**
     * open path between the nodes
     *
     * @param myMaze
     * @param current
     * @param op
     * @param row
     * @param col
     */

    private void openPath(Maze myMaze, Position current, Position op, int row, int col){
        boolean flag = true;
        if( current.getRowIndex() != row &&  current.getColumnIndex() != col) {
            if (op.getRowIndex() != row && op.getColumnIndex() != col) {

                if((op.getRowIndex() != row-1 && op.getColumnIndex() != col-1) && (op.getRowIndex() != 0 && op.getColumnIndex() != 0))
                    myMaze.setNewStatus(op.getRowIndex(), op.getColumnIndex(), 0);
                else{
                    flag = false;
                }
                myMaze.setNewStatus(current.getRowIndex(), current.getColumnIndex(), 0);
                //myMaze.setNewStatus(op.getRowIndex(), op.getColumnIndex(), 0);
                // iterate through direct neighbors of node, same as earlier
                if(flag)
                    makeNeighbors(myMaze,op,row,col);
            }
        }
    }

    /**
     * adding Neigbors of the current point
     *
     * @param myMaze
     * @param op
     * @param row
     * @param col
     */

    private void makeNeighbors(Maze myMaze, Position op, int row, int col){
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0)
                    continue;
                try {
                    if (myMaze.getStatus(op.getRowIndex() + x, op.getColumnIndex() + y) == 0) continue;
                } catch (Exception e) {
                    continue;
                }
                if (op.row != row - 1 && op.col != col - 1)
                    frontier.add(new Position(op.row + x, op.col + y, op));
            }
        }
    }

    /**
     * Set end Position of the maze
     * @param myMaze
     */
    private void SetEndPosition(Maze myMaze){
        int j = myMaze.getCol()-2;

        while ((j >= myMaze.getCol() / 2)) {
            if (myMaze.getStatus(myMaze.getRow() - 2, j) == 0) {
                myMaze.setPosEnd(myMaze.getRow() - 1, j);
                myMaze.setNewStatus(myMaze.getRow()-1,j,0);
                break;
            }
            j--;
        }
    }
}