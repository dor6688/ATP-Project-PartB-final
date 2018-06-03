package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;


public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        ISearchingAlgorithm searcher = new BreadthFirstSearch();
        Solution sol ;
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            Maze maze = (Maze)fromClient.readObject(); // the maze that the server gets from the client
            String mazeByteInString = convertFromMazeToStringInByte(maze); // convert from maze to byte in string
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            int hashCodeMaze = mazeByteInString.hashCode();
            // check if hashCodeMaze file exist
            if(new File(tempDirectoryPath+""+hashCodeMaze+".txt").isFile()){
                sol = takeSolutionFromFile(tempDirectoryPath, hashCodeMaze);
            }
            // file doesn't exist
            else {
                // get from Configuration the search algorithm
                String value =Server.config.getSearchAlgorithm();
                if(value.equals("BFS"))
                    searcher = new BreadthFirstSearch();
                if(value.equals("DFS"))
                    searcher = new DepthFirstSearch();
                if(value.equals("BEST"))
                    searcher = new BestFirstSearch();
                sol = makeNewFileWithSolution(tempDirectoryPath, hashCodeMaze, searcher, maze);
            }
            toClient.writeObject(sol);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * create new file with a hash code name
     *  which contains the solution maze
     * @param tempDirectoryPath
     * @param hashCodeMaze
     * @param searcher
     * @param maze
     * @return Solution
     */
    private Solution makeNewFileWithSolution(String tempDirectoryPath, int hashCodeMaze, ISearchingAlgorithm searcher, Maze maze) {

        Solution sol = null;
        try {
            File file = new File(tempDirectoryPath + "" + hashCodeMaze + ".txt");
            FileOutputStream fos = new FileOutputStream(file);
            ISearchable mazeToSolve = new SearchableMaze(maze);
            sol = searcher.solve(mazeToSolve); // solve maze
            String solInString = sol.getSolutionPath().toString();
            fos.write(solInString.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sol;
    }

    /**
     * find the correct file with hashCodeMaze file name
     * read the answer from it
     * convert every string to AState
     * insert all AState to the Solution
     * @param tempDirectoryPath
     * @param hashCodeMaze
     * @return Solution
     */
    private Solution takeSolutionFromFile(String tempDirectoryPath, int hashCodeMaze) {
        BufferedReader br ;
        FileReader fr;
        Solution sol = new Solution();
        try {
            fr = new FileReader(tempDirectoryPath + "" + hashCodeMaze + ".txt");
            br = new BufferedReader(fr);
            String sCurrentLine = br.readLine(); // string that we read from the file
            String[] solutionState = sCurrentLine.split(", ");
            MazeState state = new MazeState();
            for (int i = 0; i < solutionState.length; i++) {
                if (i == 0) { // the first word
                    solutionState[i] = solutionState[i].substring(1);
                }
                if (i == solutionState.length - 1) { // the last word
                    solutionState[i] = solutionState[i].substring(0, solutionState[i].length() - 1);
                }
                String[] size = solutionState[i].split(","); // get the row and the column
                state.updateState(Integer.parseInt(size[0].substring(1)), Integer.parseInt(size[1].substring(0, size[1].length() - 1)));
                MazeState mazeS = new MazeState();
                mazeS.copyMazeState(state);
                sol.addToSolution(mazeS); // insert to solution
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return sol;

    }

    /**
     * convert maze toByteArray
     * and present the byte array as string
     * @param maze
     * @return string array with maze bytes
     */
    private String convertFromMazeToStringInByte(Maze maze) {
        byte[] mazeInByte = maze.toByteArray(); // maze in byte array
        String mazeString = convertByteToStr(mazeInByte);
        return mazeString;
    }

    /**
     * convert byte array to string array
     * @param mazeByte
     * @return string array
     */
    public String convertByteToStr(byte[]mazeByte){
        String str = "";
        for(int i=0;i<mazeByte.length;i++){
            str += mazeByte[i];
        }
        return str;
    }
}
