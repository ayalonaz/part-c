package View;

import VIewModel.MyViewModel;
import javafx.fxml.FXML;

public class MyViewController {
    @FXML private MazeDisplayer mazeDisplay;

    @FXML
    public void initialize(){
        mazeDisplay.drawMaze(ScreenController.viewModel.getMaze());
    }

}
