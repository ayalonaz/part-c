package View;

import VIewModel.MyViewModel;
import javafx.fxml.FXML;

public class MyViewController extends ScreenController{
    @FXML private MazeDisplayer mazeDisplay;

    @FXML
    public void initialize(){
        mazeDisplay.drawMaze(viewModel.getMaze());
    }

    @FXML
    public void showSolution(){

    }
}
