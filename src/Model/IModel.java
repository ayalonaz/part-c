package Model;


import algorithms.mazeGenerators.Maze;
import javafx.scene.input.KeyCode;

import javafx.scene.input.KeyCode;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public interface IModel {


    void generateMaze(int width, int height);
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    void setCharacterPositionRow(int characterPositionRow);
    void setCharacterPositionColumn(int characterPositionColumn);
    void saveMaze(File file);
    void loadMaze(File file );
    void close();
    Maze getMazeDetails();
    int[][] getMaze();
    void updateCharacterLocation(KeyCode direction);
    void getSolution();
    void generateMazeServer(int height, int width);
    void hideSol();
    void startServers();
}
