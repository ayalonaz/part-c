package VIewModel;

import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

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
    //</editor-fold>

    //<editor-fold desc="ViewModel Functionality">
    public void generateMaze(int width, int height){
        model.generateMaze(width, height);
    }

    public void stop() {
        MyModel myModel = (MyModel)model;
        myModel.stopServers();
    }

    public void moveCharacter(KeyCode movement){
        model.moveCharacter(movement);
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public int[][] getMaze() {
        return model.getMaze();
    }

    public int getCharacterPositionRow() {
        //return characterPositionRowIndex;
        return model.getCharacterPositionRow();
    }

    public int getCharacterPositionColumn() {
        //return characterPositionColumnIndex;
        return model.getCharacterPositionColumn();
    }

    public void setSolution(){
        model.setSolution();
    }

    public void hideSolution(){
        model.hideSolution();
    }

    public void saveMaze(String name){model.saveMaze(name);}

    public ObservableList<String> getNames(){
        return model.getNames();
    }
    public void loadMaze(String name){
        model.loadMaze(name);
    }
    //</editor-fold>
}