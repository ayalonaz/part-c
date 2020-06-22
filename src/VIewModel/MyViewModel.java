package VIewModel;

import Model.IModel;
import Model.MyModel;

import algorithms.mazeGenerators.Maze;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    public MyViewModel(IModel model){
        this.model = model;
    }
    //<editor-fold desc="Take care Observable">
    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (o==model){
                    //Notify my observer (View) that I have changed
                    setChanged();
                    notifyObservers();
                }
            }
        });
    }
    public void generateMaze(int width, int height){
        model.generateMazeServer( height, width);
//        model.generateMaze( width, height);
    }
    public void stop() {
        MyModel myModel = (MyModel)model;
        myModel.stopServers();
    }
    public void ExitApp(){
        model.close();
    }
    public void moveCharacter(KeyCode movement){
        model.updateCharacterLocation(movement);
    }
    public int[][] getMaze() {
        return model.getMaze();
    }
    public Maze getMazeDetails(){
        return model.getMazeDetails();
    }
    public int getRowsNumber(){
        return model.getMazeDetails().getRowNumbers();
    }
    public int getColsNumber(){
        return model.getMazeDetails().getColNumbers();
    }
    public int getStartRow(){
        return model.getMazeDetails().getStartPosition().getRowIndex();
    }
    public int getStartCol(){
        return model.getMazeDetails().getStartPosition().getColumnIndex();
    }
    public int getGoalRow(){
        return model.getMazeDetails().getGoalPosition().getRowIndex();
    }
    public int getGoalCol(){
        return model.getMazeDetails().getGoalPosition().getColumnIndex();
    }
    public int getCharacterPositionRow() {
        return model.getCharacterPositionRow();
    }
    public int getCharacterPositionColumn() {
        return model.getCharacterPositionColumn();
    }
    public void saveMaze(File file){
        model.saveMaze(file);
    }
    public void loadMaze(File file){
        model.loadMaze(file);
    }
    public void getSolution(){
        model.getSolution();
    }
    public void hideSol(){model.hideSol();}

}