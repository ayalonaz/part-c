package Model;


import javafx.scene.input.KeyCode;

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
