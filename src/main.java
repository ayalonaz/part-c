
import Model.IModel;
import Model.MyModel;
import VIewModel.MyViewModel;
import View.*;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/propertiesPage.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Minion Maze Game");
        primaryStage.setScene(new Scene(root,900,900));
        primaryStage.show();

        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        ScreenController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        //ViewModel -> Model
//        MyModel model = new MyModel();
//        model.startServers();
//        MyViewModel viewModel = new MyViewModel(model);
//        model.addObserver(viewModel);

        //Loading Main Windows
//        primaryStage.setTitle("THE Minion MAZE GAME");
//        primaryStage.setWidth(400);
//        primaryStage.setHeight(600);
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        Parent root = fxmlLoader.load(getClass().getResource("View/propertiesPage.fxml").openStream());
//        Scene scene = new Scene(root, 400, 600);
//
//        IModel model = new MyModel();
//        MyViewModel viewModel = new MyViewModel(model);
//        ScreenController view =fxmlLoader.getController();
//        view.setViewModel(viewModel);
//        viewModel.addObserver(view);
//       // scene.getStylesheets().add(getClass().getResource("View/MyView.css").toExternalForm());
//        primaryStage.minWidthProperty().bind(scene.heightProperty());
//        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
//        primaryStage.setScene(scene);
//
//        //View -> ViewModel
////        MyViewController view = fxmlLoader.getController();
////        view.initialize(viewModel,primaryStage,scene);
////        viewModel.addObserver(view);
//        //--------------
//        //setStageCloseEvent(primaryStage,model);
//        //setStageCloseEvent(primaryStage, model);
//        //
//        //Show the Main Window
//        primaryStage.show();
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

