package View;

import VIewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public interface Iview {
     void setViewModel(MyViewModel viewModel);
     void loadMaze();
     void saveMaze();
     void startNewGame();
     void showProperties();
     void showRules();
     void showAbout();
     void closeApplication();

}
