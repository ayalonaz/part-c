package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

public class winController extends ScreenController {

    @FXML public HBox ImageHolder;
    @FXML private ImageView win_image;

    @FXML
    public void initialize() {
        //Image i = new Image(new File("/images/winImage.gif").toURI().toString());
        //win_image.setImage(i);
        win_image.setImage(new Image("/images/newWin.jpg"));

        ImageHolder.setAlignment(Pos.CENTER);
        win_image.setFitWidth(500);
        win_image.setFitHeight(500);

    }
    public void setStage(Stage stage) {
        super.setStage(stage);
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (main.getHeight() < 1000) {
                    win_image.setFitHeight(main.getHeight() / 2);
                }
            }
        });
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (main.getHeight() < 1000) {
                    win_image.setFitWidth(main.getWidth() / 2);
                }
            }
        });
    }
}
