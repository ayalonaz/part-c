
import View.AboutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.text.View;
import java.io.File;
import java.util.Optional;


public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        //ViewModel -> Model
//        MyModel model = new MyModel();
//        model.startServers();
//        MyViewModel viewModel = new MyViewModel(model);
//        model.addObserver(viewModel);

        //Loading Main Windows
        primaryStage.setTitle("THE BARNEY MAZE GAME");
        primaryStage.setWidth(400);
        primaryStage.setHeight(600);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/About.fxml").openStream());
        Scene scene = new Scene(root, 400, 600);
       // scene.getStylesheets().add(getClass().getResource("View/MyView.css").toExternalForm());
        primaryStage.minWidthProperty().bind(scene.heightProperty());
        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
        primaryStage.setScene(scene);

        //View -> ViewModel
//        MyViewController view = fxmlLoader.getController();
//        view.initialize(viewModel,primaryStage,scene);
//        viewModel.addObserver(view);
        //--------------
        //setStageCloseEvent(primaryStage,model);
        //setStageCloseEvent(primaryStage, model);
        //
        //Show the Main Window
        primaryStage.show();
    }







    private void setStageCloseEvent(Stage primaryStage, View model) {
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                // Close the program properly
                // model.close();
            } else {
                // ... user chose CANCEL or closed the dialog
                event.consume();
            }
        });
    }

}

