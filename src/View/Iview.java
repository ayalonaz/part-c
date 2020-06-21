package View;

import VIewModel.MyViewModel;

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
