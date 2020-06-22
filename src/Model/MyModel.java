package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import Server.ServerStrategyGenerateMaze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MyModel extends Observable implements IModel {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private Maze my_maze;
    private int characterPositionRow;
    private int characterPositionColumn;
    private int[][] maze;
    private Solution sol;
    private int previousPlace;

    public MyModel() {
        maze = null;
        characterPositionRow = 0;
        characterPositionColumn = 0;
    }

        public void startServers() {
            Server mazeGeneratingServer = new Server(5400, 10000, new ServerStrategyGenerateMaze());
            Server solveSearchProblemServer = new Server(5401, 10000, new ServerStrategySolveSearchProblem());
            solveSearchProblemServer.start();
            mazeGeneratingServer.start();
    }
    public void stopServers() {
        threadPool.shutdown();
    }

    public int[][] getMaze() {
        return maze;
    }

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

    public void generateMazeServer(int height, int width) {

        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        //outToServer.write("generateMaze".getBytes());

                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        int[] array_of_sizes = new int[2];
                        array_of_sizes[0] = width;
                        array_of_sizes[1] = height;
                        toServer.writeObject("generateMaze");
                        toServer.flush();
                        toServer.writeObject(array_of_sizes);
                        toServer.flush();
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        byte[] maze_array = (byte[]) fromServer.readObject();
                        my_maze = new Maze(maze_array);
                        maze = my_maze.getTheMaze();
                        int row_goal = my_maze.getGoalPosition().getRowIndex();
                        int column_goal = my_maze.getGoalPosition().getColumnIndex();
                        maze[row_goal][column_goal] = 5;
                        characterPositionRow = my_maze.getStartPosition().getRowIndex();
                        characterPositionColumn = my_maze.getStartPosition().getColumnIndex();
                        maze[characterPositionRow][characterPositionColumn] = 3;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void getSolution() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {

                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
//                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
//                        outToServer.write("hello".getBytes());
                        toServer.writeObject("solveMaze");
                        toServer.flush();
                        Maze newMaze = new Maze(my_maze.toByteArray());
                        int[][] maze = newMaze.getTheMaze();
                        maze[my_maze.getGoalPosition().getRowIndex()][my_maze.getGoalPosition().getColumnIndex()] = 0;
                        maze[characterPositionRow][characterPositionColumn] = 0;
                        toServer.writeObject(newMaze.toByteArray());
                        toServer.flush();

                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        sol = (Solution) fromServer.readObject();
                        ArrayList<AState> path = sol.getSolutionPath();
                        for (AState aState : path) {
                            MazeState state = (MazeState) aState;
                            int row = state.getRow();
                            int col = state.getColumn();
                            my_maze.getTheMaze()[row][col] = 4;
                        }
                        my_maze.getTheMaze()[my_maze.getGoalPosition().getRowIndex()][my_maze.getGoalPosition().getColumnIndex()] = 5;
                        my_maze.getTheMaze()[characterPositionRow][characterPositionColumn] = 3;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void generateMaze(int width, int height) {

        MyMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze local_maze = mazeGenerator.generate(width, height);
        my_maze = local_maze;
        this.maze = this.my_maze.getTheMaze();
        characterPositionRow = my_maze.getStartPosition().getRowIndex();
        characterPositionColumn = my_maze.getStartPosition().getColumnIndex();
        this.maze[characterPositionRow][characterPositionColumn] = 3;
        int goal_row = my_maze.getGoalPosition().getRowIndex();
        int goal_col = my_maze.getGoalPosition().getColumnIndex();
        this.maze[goal_row][goal_col] = 5;
        setChanged(); //Raise a flag that I have changed
        notifyObservers(maze); //Wave the flag so the observers will notice

    }

    public void getSolution2() {
        SearchableMaze searchableMazemaze = new SearchableMaze(my_maze);
        maze[my_maze.getGoalPosition().getRowIndex()][my_maze.getGoalPosition().getColumnIndex()] = 0;
        maze[characterPositionRow][characterPositionColumn] = 0;
        //ASearchingAlgorithm aSearchingAlgorithm= new ASearchingAlgorithm();
        BreadthFirstSearch best = new BreadthFirstSearch();
        Solution sol = best.solve(searchableMazemaze);
        ArrayList<AState> path = sol.getSolutionPath();
        for (AState aState : path) {
            MazeState state = (MazeState) aState;
            int row = state.getRow();
            int col = state.getColumn();
            my_maze.getTheMaze()[row][col] = 4;
        }
        maze[my_maze.getGoalPosition().getRowIndex()][my_maze.getGoalPosition().getColumnIndex()] = 5;
        maze[characterPositionRow][characterPositionColumn] = 3;
    }


    public void setCharacterPositionRow(int characterPositionRow) {

        this.characterPositionRow = characterPositionRow;

    }

    public void setCharacterPositionColumn(int characterPositionColumn) {
        this.characterPositionColumn = characterPositionColumn;
    }

    public void updateCharacterLocation(KeyCode key) {

        int char_col = characterPositionColumn;
        int char_row = characterPositionRow;
        switch (key) {

            case UP: //Up
                if (legalMove(char_row - 1, char_col)) {

                    if (maze[char_row - 1][char_col] == 5) {
                        maze[my_maze.getGoalPosition().getRowIndex()][my_maze.getGoalPosition().getColumnIndex()] = 10;

                    }
                    else {
                        maze[characterPositionRow][characterPositionColumn] = previousPlace;
                        previousPlace = maze[char_row - 1][char_col];
                        setCharacterPositionRow(char_row - 1);
                    }
                }
                break;
            case DOWN: //Down
                if (legalMove(char_row + 1, char_col)) {
                    //int tmp=maze[char_row + 1][char_col];
                    if (maze[char_row + 1][char_col] == 5) {
                        maze[my_maze.getGoalPosition().getRowIndex()][my_maze.getGoalPosition().getColumnIndex()] = 10;

                    }
                    else {
                        maze[characterPositionRow][characterPositionColumn] = previousPlace;
                        previousPlace = maze[char_row + 1][char_col];
                        setCharacterPositionRow(char_row + 1);
                    }
                }
                break;
            case LEFT: //Left
                if (legalMove(char_row, char_col - 1)) {
                    //int tmp=maze[char_row ][char_col-1];
                    if (maze[char_row][char_col - 1] == 5) {
                        maze[my_maze.getGoalPosition().getRowIndex()][my_maze.getGoalPosition().getColumnIndex()] = 10;
                    }
                    else {
                        maze[characterPositionRow][characterPositionColumn] = previousPlace;
                        previousPlace = maze[char_row ][char_col-1];
                        setCharacterPositionColumn(char_col - 1);
                    }
                }
                break;
            case RIGHT: //Right
                if (legalMove(char_row, char_col + 1)) {
                    //int tmp=maze[char_row ][char_col+1];
                    if (maze[char_row][char_col + 1] == 5) {
                        maze[my_maze.getGoalPosition().getRowIndex()][my_maze.getGoalPosition().getColumnIndex()] = 10;
//
                    }
                    else {
                        maze[characterPositionRow][characterPositionColumn] = previousPlace;
                        previousPlace = maze[char_row ][char_col+1];
                        setCharacterPositionColumn(char_col + 1);
                    }
                }
                break;
            case NUMPAD8: //Up
                if (legalMove(char_row - 1, char_col)) {
                    maze[characterPositionRow][characterPositionColumn] = 0;
                    setCharacterPositionRow(char_row - 1);
                }
                break;
            case NUMPAD2: //Down
                if (legalMove(char_row + 1, char_col)) {
                    maze[characterPositionRow][characterPositionColumn] = 0;
                    setCharacterPositionRow(char_row + 1);
                }
                break;
            case NUMPAD4: //Left
                if (legalMove(char_row, char_col - 1)) {
                    maze[characterPositionRow][characterPositionColumn] = 0;
                    setCharacterPositionColumn(char_col - 1);
                }
                break;
            case NUMPAD6: //Right
                if (legalMove(char_row, char_col + 1)) {
                    maze[characterPositionRow][characterPositionColumn] = 0;
                    setCharacterPositionColumn(char_col + 1);
                }
                break;
            case NUMPAD9: //up & right
                if (legalMove(char_row - 1, char_col + 1)) {
                    maze[characterPositionRow][characterPositionColumn] = 0;
                    setCharacterPositionColumn(char_col + 1);
                    setCharacterPositionRow(char_row - 1);
                }
                break;
            case NUMPAD7: //up & left
                if (legalMove(char_row - 1, char_col - 1)) {
                    maze[characterPositionRow][characterPositionColumn] = 0;
                    setCharacterPositionColumn(char_col - 1);
                    setCharacterPositionRow(char_row - 1);
                }
                break;
            case NUMPAD3: //down & right
                if (legalMove(char_row + 1, char_col + 1)) {
                    maze[characterPositionRow][characterPositionColumn] = 0;
                    setCharacterPositionColumn(char_col + 1);
                    setCharacterPositionRow(char_row + 1);
                }
                break;
            case NUMPAD1: //down & left
                if (legalMove(char_row + 1, char_col - 1)) {
                    maze[characterPositionRow][characterPositionColumn] = 0;
                    setCharacterPositionColumn(char_col - 1);
                    setCharacterPositionRow(char_row + 1);
                }
                break;
//            case UP: //Up
//                if(legalMove(char_row-1,char_col))
//                    setCharacterPositionRow(char_row-1);
//                break;
//            case DOWN: //Down
//                if(legalMove(char_row+1,char_col))
//                    setCharacterPositionRow(char_row+1);
//                break;
//            case LEFT: //Left
//                if(legalMove(char_row,char_col-1))
//                    setCharacterPositionColumn(char_col-1);
//                break;
//            case RIGHT: //Right
//                if(legalMove(char_row,char_col+1))
//                    setCharacterPositionColumn(char_col+1);
//                break;

        }
        setChanged();
        notifyObservers();
    }

    public void hideSol() {
        for (int i = 0; i < my_maze.getRowNumbers(); i++) {
            for (int j = 0; j < my_maze.getColNumbers(); j++) {
                if (maze[i][j] == 4) {
                    maze[i][j] = 0;
                }
            }
        }
        setChanged();
        notifyObservers(maze);
    }


    private boolean legalMove(int row, int column) {
        if (row < 0 || row >= my_maze.getRowNumbers() || column < 0 || column >= my_maze.getColNumbers()) {

            return false;

        } else {
            if (this.maze[row][column] != 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveMaze(File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            int char_col = this.characterPositionColumn;
            int char_row = this.characterPositionRow;
            MyCompressorOutputStream comp = new MyCompressorOutputStream(out);
            comp.write(my_maze.toByteArray());
            out.close();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append("," + char_row + "," + char_col);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMaze(File file) {
        try {
            InputStream in = new FileInputStream(file);
            MyDecompressorInputStream deCom = new MyDecompressorInputStream(in);
            byte[] mazeByte = new byte[(int) file.length() - 4];
            deCom.read(mazeByte);
            FileReader fileReader = new FileReader(file);
            BufferedReader inStream = new BufferedReader(fileReader);
            String s = inStream.readLine();
            String[] Data_from_file = s.split(",");
            int char_col = Integer.parseInt(Data_from_file[2]);
            int char_row = Integer.parseInt(Data_from_file[1]);
            this.my_maze = new Maze(mazeByte);
            int goal_row = my_maze.getGoalPosition().getRowIndex();
            int goal_col = my_maze.getGoalPosition().getColumnIndex();
            maze = this.my_maze.getTheMaze();
            maze[char_row][char_col] = 3;
            this.characterPositionColumn = char_col;
            this.characterPositionRow = char_row;
            maze[goal_row][goal_col] = 5;

            in.close();
            fileReader.close();
            setChanged();
            notifyObservers(my_maze);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Maze getMazeDetails() {
        return this.my_maze;
    }
}




