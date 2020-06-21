package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class propertiesPageController extends ScreenController{

    private int rows;
    private int cols;
    @FXML private TextField userRows;
    @FXML private TextField userCols;
    @FXML private ImageView imageView;
    @FXML private GridPane UserAddDetails;
    @FXML private HBox ControlHold;
    @FXML private HBox ImageHolder;

    public void initialize(){
        imageView.setImage(new Image("/images/propertiesPage.png"));
//        UserAddDetails.setPadding(new Insets(5,5,5,5));

        ImageHolder.setAlignment(Pos.CENTER);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        ControlHold.setAlignment(Pos.CENTER);
    }

    public void setStage(Stage stage) {
        super.setStage(stage);
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (main.getHeight() < 1000) {
                    imageView.setFitHeight(main.getHeight() / 2);
                }
            }
        });
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (main.getHeight() < 1000) {
                    imageView.setFitWidth(main.getWidth() / 2);
                }
            }
        });
    }

    @FXML
    public void generteMaze(){
        try{
            this.rows= Integer.parseInt(userRows.getText());
            this.cols=Integer.parseInt(userCols.getText());
        }
        catch (NumberFormatException e){

        }
        this.rows= Integer.parseInt(userRows.getText());
        this.cols=Integer.parseInt(userCols.getText());
        viewModel.generateMaze(rows,cols);
        addScreen("MyView","/MyView.fxml");
        activate("MyView");
    }



}
