package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Maze implements Serializable {

    private Position posStart;
    private Position posEnd;
    int [][] myMaze;
    private boolean design = false; // change here to see maze
    byte [] mazeByte ;
    ArrayList mazeByteTmp;


    /**
     * Constructor
     * Set Start Position
     * Set End Position
     *
     * @param row
     * @param col
     * @param randomStart
     */
    public Maze(int row,int col,boolean randomStart){
        myMaze = new int [row][col];
        posStart = setRandomStartPoint(row,col,randomStart);
        posEnd = new Position(row-2,col-1);
    }

    public Maze(){
        myMaze = new int[10][10];
        posStart = new Position(0,1);
        posEnd = new Position(9,8);

    }

    public Maze(byte[] mazeBytes){
        int frameGrater255 = mazeBytes[0];
        int rowM = 0;
        int colM=0;
        int rowStart=0;
        int colStart=0;
        int rowEnd=0;
        int colEnd=0;
        String level = "";
        if(frameGrater255==0){
            rowM = 0xFF & mazeBytes[1];
            colM = 0xFF & mazeBytes[2];
            rowStart = 0xFF & mazeBytes[3];
            colStart = 0xFF & mazeBytes[4];
            rowEnd = 0xFF & mazeBytes[5];
            colEnd = 0xFF & mazeBytes[6];
            level = "low";
        }
        if (frameGrater255 == 1){
            rowM = 0xFF & mazeBytes[1];
            colM = 256 * (0xFF &mazeBytes[2])+(0xFF &mazeBytes[3]);
            rowStart = 0xFF & mazeBytes[4];
            colStart = 256*(0xFF & mazeBytes[5])+(0xFF &mazeBytes[6]);
            rowEnd = 0xFF & mazeBytes[7];
            colEnd = 256*(0xFF & mazeBytes[8])+(0xFF & mazeBytes[9]);
            level = "med";
        }
        if (frameGrater255 == 2){
            rowM = 256*(0xFF & mazeBytes[1])+(0xFF &mazeBytes[2]);
            colM = 0xFF &mazeBytes[3];
            rowStart = 256*(0xFF & mazeBytes[4])+(0xFF &mazeBytes[5]);
            colStart = 0xFF &mazeBytes[6];
            rowEnd = 256*(0xFF & mazeBytes[7])+(0xFF & mazeBytes[8]);
            colEnd = 0xFF & mazeBytes[9];
            level = "med";
        }
        if (frameGrater255 == 3){
            rowM = 256*(0xFF & mazeBytes[1])+(0xFF &mazeBytes[2]);
            colM = 256 * (0xFF &mazeBytes[3])+(0xFF &mazeBytes[4]);
            rowStart = 256*(0xFF & mazeBytes[5])+(0xFF &mazeBytes[6]);
            colStart = 256*(0xFF & mazeBytes[7])+(0xFF &mazeBytes[8]);
            rowEnd = 256*(0xFF & mazeBytes[9])+(0xFF & mazeBytes[10]);
            colEnd = 256*(0xFF & mazeBytes[11])+(0xFF &mazeBytes[12]);
            level = "large";
        }

        myMaze = new int [rowM][colM];
        posStart = new Position(rowStart,colStart);
        posEnd = new Position(rowEnd,colEnd);
        buildNewMaze(mazeBytes,level);
        myMaze[posEnd.row][posEnd.col] = 0;

    }

    private void buildNewMaze(byte [] oldMaze,String level) {
        int k=-1;
        if (level == "low")
            k = 7;
        if (level == "med")
            k = 10;
        if (level == "large")
            k = 13;
        for (int i=1;i<myMaze.length-1;i++){
            for (int j=1;j<myMaze[0].length-1;j++){
                myMaze[i][j] = 0xFF & oldMaze[k++];
            }
        }
        fillBoard();
    }

    private void fillBoard(){
        for(int i=0;i<=myMaze.length-1;i++){
            myMaze[i][myMaze[0].length-1] = 1;
        }
        for(int i=0;i<=myMaze[0].length-1;i++){
            myMaze[myMaze.length-1][i] = 1;
        }
        for(int i=0;i<=myMaze.length-1;i++){
            myMaze[i][0] = 1;
        }
        for(int i=0;i<=myMaze[0].length-1;i++){
            myMaze[0][i] = 1;
        }
    }






    /**
     * Create the starting point of the maze
     * if randomStart = true
     *      make it in random
     * else
     *      default (1,0)
     *
     * @param row
     * @param col
     * @param randomStart
     * @return start position
     */
    private Position setRandomStartPoint(int row,int col,boolean randomStart){
        Position start = new Position(1,0);
        if(randomStart){
            int x,y;
            int xOry = (int) (Math.random()*2);
            if(xOry==0){
                y= (int) (Math.random()*col);
                while(y==0 || y==col-1)
                    y= (int) (Math.random()*col);
                x=0;
            }
            else{
                x= (int) (Math.random()*row);
                while(x==0 || x==row-1)
                    x= (int) (Math.random()*row);
                y=0;
            }
            start=new Position(x,y);
        }
        return start;
    }

    /**
     * Setters
     * Set new End position
     *
     * @param rowNew
     * @param colNew
     */
    public void setPosEnd(int rowNew, int colNew){
        posEnd = new Position(rowNew,colNew);
    }

    /**
     * Getters
     * get the value in the current row and col
     *
     * @param rowS
     * @param colS
     * @return maze[rowS][colS]
     */
    public int getStatus(int rowS,int colS){
        if(rowS>=0 && colS>=0 && rowS<this.getRow() && colS< this.getCol())
            return myMaze[rowS][colS];
        return -1;
    }

    /**
     * Setters
     * set new value in current position
     * @param rowS
     * @param colS
     * @param value
     */
    public void setNewStatus(int rowS,int colS, int value){
        if(rowS>=0 && colS>=0 && rowS<this.getRow() && colS< this.getCol())
            myMaze[rowS][colS] = value;
    }

    /**
     * Getters
     * @return start position
     */
    public Position getStartPosition(){
        return posStart;
    }

    /**
     * Getters
     * @return goal position
     */
    public Position getGoalPosition(){
        return posEnd;
    }

    /**
     * Getters
     * @return Maze column
     */
    public int getCol(){
        return myMaze[0].length;
    }

    /**
     * Getters
     * @return Maze row
     */
    public int getRow(){
        return myMaze.length;
    }

    /**
     * Print the maze
     */
    public void print(){
        System.out.println();
        for (int i=0;i<myMaze.length;i++){
            for(int j=0;j<myMaze[0].length;j++){
                if(i == posStart.getRowIndex() && j == posStart.getColumnIndex()) {
                    if(design)
                        System.out.print("\u25A5"); // change here
                        //System.out.print("S");
                    else
                        System.out.print("[S]");
                    continue;
                }
                if(i == posEnd.getRowIndex() && j == posEnd.getColumnIndex()) {
                    if(design)
                        System.out.print("\u25A5"); // change here
                        //System.out.print("E");
                    else
                        System.out.print("[E]");
                    continue;
                }
                if(myMaze[i][j] == 2){
                    if(design)
                        System.out.print("\uD83D\uDD37");
                    else
                        System.out.print("[2}");
                    continue;
                }
                if(myMaze[i][j] == 4){
                    if(design)
                        System.out.print("\uD83D\uDD34");
                    else
                        System.out.print("[4]");
                    continue;
                }
                else {
                    //if it is a wall
                    if (myMaze[i][j] == 1) {
                        if(design)
                            System.out.print("\u2B1B");
                        else
                            System.out.print("[1]");
                    } else {
                        if(design)
                            System.out.print("\u2B1C");
                        else
                            System.out.print("[0]");
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * Make example maze with 2 different solution
     */
    public void makeExample(){
        for(int i=2;i<11;i++)
            myMaze[i][2]=1;
        for(int y=3;y<6;y++)
            myMaze[2][y]=1;
        for(int y=3;y<6;y++)
            myMaze[6][y]=1;
        for(int y=3;y<6;y++)
            myMaze[10][y]=1;
        for(int y=4;y<7;y++)
            myMaze[4][y]=1;
        for(int y=4;y<7;y++)
            myMaze[8][y]=1;
    }

    /**
     * maze without solution
     */
    public void makeMazeWithNoSolution(){
        for (int i=0; i<10; i++)
            myMaze[5][i] = 1 ;
    }


    /**
     * represent all the data of the maze:
     *   start Position
     *   end Position
     *   maze row and column
     *   the walls and the path
     *
     * @return the zipped maze in byte array
     */
    public byte[] toByteArray(){
        byte RCless255 = (byte) 0;
        mazeByteTmp = new ArrayList();
        boolean shortRow = true;
        boolean shortCol = true;
        if (myMaze.length > 255) {
            shortRow = false;
            RCless255 += (byte)2;

        }
        if (myMaze[0].length > 255) {
            shortCol = false;
            RCless255 += (byte)1;
        }
        mazeByteTmp.add(RCless255);

        byte [] mazeRow = compressMaze(myMaze.length,shortRow);
        byte [] mazeCol = compressMaze(myMaze[0].length,shortCol);
        byte [] mazeStartRow = compressMaze(posStart.row,shortRow);
        byte [] mazeStartCol= compressMaze(posStart.col,shortCol);
        byte [] mazeEndRow = compressMaze(posEnd.row,shortRow);
        byte [] mazeEndCol = compressMaze(posEnd.col,shortCol);
        //print();
        byte [] mazeWalls = convertMazeToArray(myMaze);
        insertToOneArray(mazeRow);
        insertToOneArray(mazeCol);
        insertToOneArray(mazeStartRow);
        insertToOneArray(mazeStartCol);
        insertToOneArray(mazeEndRow);
        insertToOneArray(mazeEndCol);
        insertToOneArray(mazeWalls);
        mazeByte = insertToFinallByteArray(mazeByteTmp);
        return mazeByte;
    }

    private byte[] convertMazeToArray(int[][] myMaze) {
        List insideMaze = new ArrayList();
        for (int i=1;i<myMaze.length-1;i++){
            for (int j=1;j<myMaze[0].length-1;j++){
                insideMaze.add((byte)myMaze[i][j]);
            }
        }
        byte[]tmp = new byte[insideMaze.size()];
        for (int i=0;i<insideMaze.size();i++) {
            tmp[i] = (byte)insideMaze.get(i);
        }
        return tmp;
    }

    private byte[] insertToFinallByteArray(ArrayList mazeByteTmpTo) {
        mazeByte = new byte[mazeByteTmpTo.size()];
        for (int i=0;i<mazeByteTmpTo.size();i++){
            mazeByte[i] = (byte)mazeByteTmpTo.get(i);
        }
        return mazeByte;
    }


    private void insertToOneArray(byte [] tmp){
        for(int i=0;i<tmp.length;i++){
            mazeByteTmp.add(tmp[i]);
        }
    }

    private byte[] compressWallsAndPath(int[][] myMaze) {
        int counter0=0;
        int counter1=0;
        boolean changed = false;
        ArrayList<Integer> contents = new<Integer> ArrayList();
        for(int i=1; i<myMaze.length-1; i++){
            for (int j=1; j<myMaze[0].length-1; j++){
                if (myMaze[i][j]==0){
                    if(counter1>0) {
                        changed = true;
                        contents.add(counter1);
                    }
                    if(changed){
                        counter0 = 0;
                        counter1 = 0;
                    }
                    changed = false;
                    counter0++;
                }
                else{
                    if(counter0>0) {
                        changed = true;
                        contents.add(counter0);
                    }
                    if(changed){
                        counter1=0;
                        counter0 = 0;
                    }
                    changed = false;
                    counter1++;
                }
            }
        }

        byte[] compByte = convertFromIntToByte(contents);
        return  compByte;
    }

    private byte[] convertFromIntToByte(List listInt){
        byte [] compByte;
        boolean flag = false;
        if(myMaze[0].length < 255){
            compByte = new byte[listInt.size()];
            flag=true;
        }
        else{
            compByte = new byte[listInt.size()*2];
        }
        if(flag){
            for(int i=0;i<listInt.size();i++){
                int integerTmp = (int)listInt.get(i);
                compByte[i] = (byte)integerTmp;
            }
        }
        else{
            int j=0;
            for(int i=0;i<listInt.size()-1;i+=2){
                byte[]tmp=compressMaze((int)listInt.get(j++),flag);
                compByte[i] = tmp[0];
                compByte[i+1] = tmp[1];

            }
        }
        return compByte;
    }



    private byte[] compressMaze(int numToByte,boolean smallMaze){
        byte[] compress;
        if (smallMaze){
            compress = new byte[1];
            compress[0] = (byte) (numToByte % 256); // the remainder of a division
        } else {
            compress = new byte[2];
            compress[0] = (byte) (numToByte / 256); // the multiplication times
            compress[1] = (byte) (numToByte % 256); // the remainder of a division
        }
        return compress;
    }
}
