package View;

import VIewModel.MyViewModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class propertiesPageController extends ScreenController{

    private int rows;
    private int cols;
    @FXML private TextField userRows;
    @FXML private TextField userCols;
    @FXML
    public void getRowsFromUser(){
        this.rows= Integer.parseInt(userRows.getText());
    }
    public void getColsFromUser(){
        this.cols=Integer.parseInt(userCols.getText());
    }

    public void generteMaze(){

        viewModel.generateMaze(rows,cols);
        activate("gameSecne");
    }
}
