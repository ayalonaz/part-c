package View;

import VIewModel.MyViewModel;
import algorithms.search.Solution;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import  javafx.scene.control.Button;

import java.awt.*;

public class MyViewController extends ScreenController{
    @FXML private MazeDisplayer mazeDisplay;
    @FXML private BorderPane pane;
    @FXML private GridPane gPane;
    @FXML private VBox vBoxView;
    @FXML private Button solBtn;
    @FXML private Button solHide;

    @FXML
    public void initialize() {
        solBtn.setVisible(true);
        solHide.setVisible(false);
        gPane.setAlignment(Pos.CENTER);
        vBoxView.setAlignment(Pos.CENTER);
        pane.setOnKeyPressed(this::PressOnKey);
        mazeDisplay.setWidth(main.getWidth() - 50);
        mazeDisplay.setHeight(main.getHeight() - 100);
        mazeDisplay.drawMaze(viewModel.getMaze());

        main.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                mazeDisplay.setHeight(main.getHeight() - 100);
//                mazeDisplay.setScaleY(mazeDisplay.getScaleY() * (1 + (newValue.intValue() - oldValue.intValue()) / (float) oldValue.intValue()));
                mazeDisplay.draw();

            }
        });
        main.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                mazeDisplay.setWidth(main.getWidth() - 50);
//                mazeDisplay.setScaleX(mazeDisplay.getScaleX() * (1 + (newValue.intValue() - oldValue.intValue()) / (float) oldValue.intValue()));
                mazeDisplay.draw();
            }
        });
        mazeDisplay.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }
            mazeDisplay.setScaleX(mazeDisplay.getScaleX() * zoomFactor);
            mazeDisplay.setScaleY(mazeDisplay.getScaleY() * zoomFactor);
        });
    }


    public void PressOnKey(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        if(viewModel.getMaze()[viewModel.getGoalRow()][viewModel.getGoalCol()]==10){
            addScreen("win","/win.fxml");
            activate("win");
        }
        mazeDisplay.drawCharacter(viewModel.getCharacterPositionRow(),viewModel.getCharacterPositionColumn());
        keyEvent.consume();
    }



    @FXML
    public void showSolution() {
        solBtn.setVisible(false);
        solHide.setVisible(true);
        viewModel.getSolution();
        mazeDisplay.draw();

    }

    public void hideSol(){
        solBtn.setVisible(true);
        solHide.setVisible(false);
        viewModel.hideSol();
        mazeDisplay.draw();
    }


}

