package View;

import VIewModel.MyViewModel;
import javafx.fxml.FXML;

public class MyViewController extends ScreenController {
    @FXML private MazeDisplayer MazeDisplayer;
//    private MyViewModel viewModel;

    @FXML
    public void initialize(){
        MazeDisplayer.drawMaze(viewModel.getMaze());
    }







}

