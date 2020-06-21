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

    private void drawCharacter(int row,int col){
        characterPositionRow=row;
        CharacterPositionColumn=col;
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
            Image backRound=null;
            try {
                wallImage = new Image(new FileInputStream("Resources/images/wallNew.png"));
                characterImage=new Image(new FileInputStream("Resources/images/characterNew.png"));
                backRound = new Image(new FileInputStream("Resources/images/passGround.PNG"));
                win=new Image(new FileInputStream("Resources/images/win.gif"));
                pathImage=new Image(new FileInputStream("Resources/images/passSol.gif"));


                for(int i=0;i<row;i++)
                {
                    for(int j=0;j<col;j++)
                    {
                        h = i * cellHeight;
                        w = j * cellWidth;
                        if(maze[i][j] == 1) // Wall
                        {
                            graphicsContext.drawImage(wallImage,w,h,cellWidth,cellHeight);
                        }
                        else if(maze[i][j]==3){
                            graphicsContext.drawImage(characterImage,w,h,cellWidth,cellHeight);
                        }
                        else if(maze[i][j]==4){
                            graphicsContext.drawImage(pathImage,w,h,cellWidth,cellHeight);
                        }
                        else {
                            graphicsContext.drawImage(backRound,w,h,cellWidth,cellHeight);
                        }

                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("There is no Image player....");
            }


        }
    }

    private double getCol_player() {
        return 0;
    }

    private double getRow_player() {
        return 0;
    }
}