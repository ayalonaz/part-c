package View;

import VIewModel.MyViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class MyViewController extends ScreenController {
    @FXML
    private MazeDisplayer mazeDisplay;

    @FXML
    public void initialize() {
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

    @FXML
    public void showSolution() {

    }
}
