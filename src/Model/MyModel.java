package Model;

import Client.Client;
import Client.IClientStrategy;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.*;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private int goal_id = 3;

    public MyModel() {
        maze = null;
        characterPositionRow = 0;
        characterPositionColumn = 0;
    }
    public void startServers() {
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
    private void generateWithServers(int heigh, int width) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5000, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        my_maze = mg.generate(width, heigh);
                        maze = my_maze.getTheMaze();
                        toServer.writeObject(maze);
                        toServer.flush();
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
    private void getSolutionFromServer(int heigh, int width) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5000, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        my_maze = mg.generate(width, heigh);
                        maze = my_maze.getTheMaze();
                        toServer.writeObject(maze);
                        toServer.flush();
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
        threadPool.execute(() -> {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze local_maze = mazeGenerator.generate(width, height);
            my_maze = local_maze;
            characterPositionRow = my_maze.getStartPosition().getRowIndex();
            characterPositionColumn = my_maze.getStartPosition().getColumnIndex();
            setChanged(); //Raise a flag that I have changed
            notifyObservers(maze); //Wave the flag so the observers will notice
        });
    }
    public void setCharacterPositionRow(int characterPositionRow) {
        this.characterPositionRow = characterPositionRow;
    }
    public void setCharacterPositionColumn(int characterPositionColumn) {
        this.characterPositionColumn = characterPositionColumn;
    }
    public void updateCharacterLocation(KeyCode key) {
        int char_col=characterPositionColumn;
        int char_row=characterPositionRow;
        switch (key) {
            case NUMPAD8: //Up
                if(legalMove(char_row-1,char_col))
                    setCharacterPositionRow(char_row-1);
                break;
            case NUMPAD2: //Down
                if(legalMove(char_row+1,char_col))
                    setCharacterPositionRow(char_row+1);
                break;
            case NUMPAD4: //Left
                if(legalMove(char_row,char_col-1))
                    setCharacterPositionColumn(char_col-1);
                break;
            case NUMPAD6: //Right
                if(legalMove(char_row,char_col+1))
                    setCharacterPositionColumn(char_col+1);
                break;
            case NUMPAD9: //up & right
                if(legalMove(char_row-1,char_col+1)) {
                    setCharacterPositionColumn(char_col + 1);
                    setCharacterPositionRow(char_row - 1);
                }
                break;
            case NUMPAD7: //up & left
                if(legalMove(char_row-1,char_col-1)) {
                    setCharacterPositionColumn(char_col - 1);
                    setCharacterPositionRow(char_row - 1);
                }
                break;
            case NUMPAD3: //down & right
                if(legalMove(char_row+1,char_col+1)) {
                    setCharacterPositionColumn(char_col + 1);
                    setCharacterPositionRow(char_row + 1);
                }
                break;
            case NUMPAD1: //down & left
                if(legalMove(char_row+1,char_col-1)) {
                    setCharacterPositionColumn(char_col - 1);
                    setCharacterPositionRow(char_row + 1);
                }
                break;
        }
        setChanged();
        notifyObservers();
    }
    private boolean legalMove(int row, int column) {
        if (row < 0 || row > my_maze.getRowNumbers() || column < 0 || column > my_maze.getColNumbers()) {
            return false;

        } else {
            return true;
        }
    }

    @Override
    public void saveMaze(String name)  {
        try{
            Path path= Paths.get("Recources/"+name+".txt");
            if(Files.exists(path)) {
                Alert al = new Alert(Alert.AlertType.INFORMATION, "the maze already exsit in folder");
            }
            else{
                FileWriter myWriter= new FileWriter(path.toString());
                int cols= my_maze.getColNumbers();
                int rows=my_maze.getRowNumbers();
                int char_col=this.characterPositionColumn;
                int char_row=this.characterPositionRow;
                myWriter.append(char_col+",");
                myWriter.append(char_row+",");
                myWriter.append((char) cols+",");
                myWriter.append((char) rows+",");
                for(int i=0;i<rows;i++) {
                    for (int j = 0; j < cols; j++) {
                        myWriter.write(maze[i][j]+",");
                    }
                }
                myWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMaze(String name ){
        try{
            FileReader fileReader=new FileReader("Recources/"+name+".txt");
            BufferedReader inStream = new BufferedReader(fileReader);
            String s=inStream.readLine();
            String[] Data_from_file=s.split(",");
            int char_col=Integer.parseInt(Data_from_file[0]);
            int char_row=Integer.parseInt(Data_from_file[1]);
            int col=Integer.parseInt(Data_from_file[2]);
            int row=Integer.parseInt(Data_from_file[3]);
            int[][] maze_from_file=new int[row][col];
            int k=4;
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    maze_from_file[i][j]=Integer.parseInt(Data_from_file[k]);
                    k++;
                }
            }
            fileReader.close();
            this.maze=maze_from_file;
            this.my_maze=new Maze(row,col);
            my_maze.setTheMaze(maze_from_file);
            this.characterPositionColumn=char_col;
            this.characterPositionRow=char_row;
            setChanged();
            notifyObservers(my_maze);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void getSolution() {

    }
}




