package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            IMazeGenerator generator = new MyMazeGenerator();
            ByteArrayOutputStream mazeByte ;
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            String value = Server.config.getMazeGenerator();
            // MyMazeGenerator init
            if(value.equals("MyMazeGenerator")){
                generator = new MyMazeGenerator();
            }
            // SimpleMazeGenerator init
            if(value.equals("SimpleMazeGenerator")){
                generator = new SimpleMazeGenerator(); 
            }
            int[] maze_size = (int[])fromClient.readObject();// get Maze size
            byte[] mazeInByte = generateMaze(maze_size,generator);// convert maze to byte
            mazeByte =new ByteArrayOutputStream();
            OutputStream out = new MyCompressorOutputStream(mazeByte);
            out.write(mazeInByte);
            toClient.writeObject(mazeByte.toByteArray());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * get array with size of row and column
     * and  generate new maze
     * @param mazeSize
     * @param im
     * @return new maze
     */
    private byte[] generateMaze(int[] mazeSize, IMazeGenerator im)  {
        Maze maze = im.generate(mazeSize[0], mazeSize[1]);
        return maze.toByteArray();
    }
}
