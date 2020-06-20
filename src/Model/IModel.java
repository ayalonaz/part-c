package Model;


import javafx.scene.input.KeyCode;

import javafx.scene.input.KeyCode;

import java.nio.file.Path;
import java.nio.file.Paths;


public interface IModel {


    void generateMaze(int width, int height);
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    void setCharacterPositionRow(int characterPositionRow);
    void setCharacterPositionColumn(int characterPositionColumn);
    void saveMaze(String name);
    void loadMaze(String name );
    void close();

    int[][] getMaze();
    void updateCharacterLocation(KeyCode direction);
    void getSolution();






}
