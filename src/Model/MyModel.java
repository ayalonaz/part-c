package Model;

import Client.Client;
import Client.IClientStrategy;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MyModel extends Observable implements IModel {

        private ExecutorService threadPool = Executors.newCachedThreadPool();

    public MyModel() {
        }

        //<editor-fold desc="Servers">
        public void startServers() {
        }

        public void stopServers() {
            threadPool.shutdown();
        }

         private Maze theMaze;
        //<editor-fold desc="Character">
        private int characterPositionRow = 1;
        private int characterPositionColumn = 1;
        private int[][] maze;

//    public int[][] getMaze() {
//        return maze;
//    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    @Override
    public void close() {
        try {
            threadPool.shutdown();
            threadPool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

    private void generateWithServers(int heigh, int width) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        theMaze = mg.generate(width, heigh);
                        maze = theMaze.getTheMaze();
                        //maze.print();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        //Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                        //Print Maze Solution retrieved from the server
                        /*(System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void generateMaze(int width, int height) {
        //Generate maze
        threadPool.execute(() -> {
            try {
                Thread.sleep(1000);
                maze = generateMyMaze(width,height);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setChanged(); //Raise a flag that I have changed
            notifyObservers(maze); //Wave the flag so the observers will notice
        });
    }

}
