package View;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MazeDisplayer extends Canvas {
    private  int characterPositionRow=0;
    private  int CharacterPositionColumn=0;
    private int [][] maze;

    public void drawMaze(int [][] maze)
    {
        this.maze = maze;
        draw();
    }

    protected void drawCharacter(int row,int col) {
            characterPositionRow = row;
            CharacterPositionColumn = col;
            this.maze[characterPositionRow][CharacterPositionColumn] = 3;
            draw();

}

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void draw()
    {
        Boolean isDrawn=false;
        if( maze!=null)
        {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int row = maze.length;
            int col = maze[0].length;
            double cellHeight = canvasHeight/row;
            double cellWidth = canvasWidth/col;
            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0,0,canvasWidth,canvasHeight);
            graphicsContext.setFill(Color.RED);
            double w,h;
            //Draw Maze
            Image wallImage = null;
            Image characterImage=null;
            Image win=null;
            Image pathImage=null;
            Image backgroundImage=null;
            Image redFlagImage=null;

            try {
                wallImage = new Image(new FileInputStream("Resources/images/wallImage.gif"));
                characterImage = new Image(new FileInputStream("Resources/images/characterImage.gif"));
                win = new Image(new FileInputStream("Resources/images/winImage.gif"));
                pathImage = new Image(new FileInputStream("Resources/images/pathImage.gif"));
                backgroundImage = new Image(new FileInputStream("Resources/images/backgroundImage.gif"));
                redFlagImage = new Image(new FileInputStream("Resources/images/redFlag.gif"));
                //if (!isDrawn){
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            h = i * cellHeight;
                            w = j * cellWidth;
                            if (maze[i][j] == 1) // Wall
                            {
                                graphicsContext.drawImage(wallImage, w, h, cellWidth, cellHeight);
                            } else if (maze[i][j] == 3) {
                                graphicsContext.drawImage(characterImage, w, h, cellWidth, cellHeight);
                            } else if (maze[i][j] == 4) {
                                graphicsContext.drawImage(pathImage, w, h, cellWidth, cellHeight);
                            }else if(maze[i][j]==10){
                                    graphicsContext.drawImage(win, w,h,  cellWidth, cellHeight);
                            } else if (maze[i][j] == 5) {
                                graphicsContext.drawImage(redFlagImage, w, h, cellWidth, cellHeight);
                            }

                            else {
                                graphicsContext.drawImage(backgroundImage, w, h, cellWidth, cellHeight);
                            }

                        }
                    }
              //  isDrawn = true;
            //}
//            else if(isDrawn){
//
//                }

            } catch (FileNotFoundException e) {
                System.out.println("There is no Image player....");
            }


        }
    }


}