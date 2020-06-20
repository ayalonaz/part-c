package Model;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface IModel {


    void generateMaze(int width, int height);
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    void close();
    public int[][] getMaze();
    public int getRowChar();
    public int getColChar();
    public void updateCharacterLocation(int direction);
    public void saveMaze(String name);
    public  getSolution()



}
