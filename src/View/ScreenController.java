package View;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import VIewModel.MyViewModel;

public class ScreenController implements Iview,Observer {
    private HashMap<String, Scene> screenMap = new HashMap<>();

    protected static Stage main;
    protected static MyViewModel viewModel;
    private int rows;
    private int cols;
    private float mousePositionX;
    private float mousePositionY;



    protected void addScreen(String name,String fxmlPath){
        if(screenMap.containsKey(name))
            return;
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/View/" + fxmlPath));
            screenMap.put(name, new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void removeScreen(String name){
        screenMap.remove(name);
    }
    protected void activate(String name){
        if(screenMap.containsKey(name)){
            main.setScene(screenMap.get(name));
        }
    }
    protected void printKeys(){
        System.out.println(this.screenMap.keySet());
    }
    public void loadMaze(){
        File dirOfMazes=new File("Resources/savedMaze");
        if(dirOfMazes.exists()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(dirOfMazes);
            File r = fileChooser.showOpenDialog(main);
            viewModel.loadMaze(r);
            this.rows=viewModel.getRowsNumber();
            this.cols=viewModel.getColsNumber();
            activate("MyView");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Load Game");
            alert.setHeaderText(null);
            alert.setContentText("You not have saved games");
        }


    }
    public void saveMaze(){
        File dirOfMazes=new File("Resources/savedMaze");
        if(!dirOfMazes.exists()){
            dirOfMazes.mkdirs();
        }
        FileChooser FileChooser=new FileChooser();
        FileChooser.setInitialDirectory(dirOfMazes);
        FileChooser.setInitialFileName("MyMaze");
        File r=FileChooser.showSaveDialog(main);
        viewModel.saveMaze(r);
    }
    public void startNewGame(){
        viewModel.generateMaze(viewModel.getRowsNumber(),viewModel.getColsNumber());
        addScreen("MyView","View/MyView.fxml");
        activate("MyView");
    }
    public void showProperties(){
        activate("Properties");
    }
    public void showRules(){
        activate("Rules");
    }
    public void showAbout()
    {
        activate("About");
    }
    public void closeApplication(){
        viewModel.ExitApp();
        Platform.exit();
    }

    public void setStage(Stage stage){
        main=stage;
    }

    public void setViewModel(MyViewModel viewModel){
        ScreenController.viewModel=viewModel;
    }


    @Override
    public void update(Observable o, Object arg) {


    }
}