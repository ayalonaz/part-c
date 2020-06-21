package View;

import VIewModel.MyViewModel;
import javafx.fxml.FXML;

public class MyViewController {
    private MazeDisplayer mazeDisplay;
    private MyViewModel viewModel;

    @FXML
    public void initialize(){
        mazeDisplay.drawMaze(viewModel.getMaze());
    }

}
