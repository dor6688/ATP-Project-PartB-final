package Server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Server  {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ExecutorService executor;
    public static Configurations config;
    private int threadnum = 2;


    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy)  {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        this.config = new Configurations();
        this.stop = false;
        if(null != config.getNumberThreadInPool())
            this.threadnum = Integer.parseInt(config.getNumberThreadInPool());
        this.executor = Executors.newFixedThreadPool(threadnum);
    }



    private void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    executor.execute(()->{
                        try {
                            serverStrategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                            clientSocket.getInputStream().close();
                            clientSocket.getOutputStream().close();
                            clientSocket.close();
                        } catch (IOException e) {}
                    });
                } catch (SocketTimeoutException e) {
                }
            }
            serverSocket.close();
            executor.shutdown();
        } catch (IOException e) {
        }
    }



    public void start() {
        Thread t = new Thread(()->runServer());
        t.start();
    }

    public void stop(){
        stop = true;
    }



    public static class Configurations {

        private static String generators;
        private static String searchers;
        private static String numberThreadInPool;


        public Configurations(){
            Properties prop = new Properties();
            InputStream input = null;
            try {
                String filename = "config.properties";
                input = Server.class.getClassLoader().getResourceAsStream(filename);
                if(input!=null){
                    //load a properties file from class path, inside static method
                    prop.load(input);

                    //get the property value and print it out
                    generators = prop.getProperty("Maze_Generate");
                    searchers = prop.getProperty("Search_Algorithm");
                    numberThreadInPool = prop.getProperty("threadPool");


                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally{
                if(input!=null){
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        public String getMazeGenerator() {
            return generators;
        }
        public String getSearchAlgorithm() {
            return searchers;
        }
        public String getNumberThreadInPool(){
            return numberThreadInPool;
        }

    }

}
