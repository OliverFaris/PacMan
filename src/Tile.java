import javax.swing.*;
import java.awt.*;

public class Tile {
    private boolean isWall;
    private int row;
    private int col;
    private boolean isPelletVisible;
    private boolean isPowerPellet;
    private GameViewer screen;
    private Image pelletImage;
    private Image powerPelletImage;
    private int frameCounter;

    public Tile(int row, int col, boolean isWall, boolean isPelletVisible, boolean isPowerPellet, GameViewer screen) {
        this.row = row;
        this.col = col;
        this.isWall = isWall;
        this.isPelletVisible = isPelletVisible;
        this.isPowerPellet = isPowerPellet;
        this.screen = screen;
        this.pelletImage = new ImageIcon("Resources/pellet2.png").getImage();
        this.powerPelletImage = new ImageIcon("Resources/powerPellet2.png").getImage();
        this.frameCounter = 0;
    }

    public boolean getIsWall() {
        return isWall;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isPowerPellet() {
        return isPowerPellet;
    }

    public boolean isPelletVisible() {
        return isPelletVisible;
    }

    public void setPelletVisible(boolean pelletVisible) {
        isPelletVisible = pelletVisible;
    }

    public void draw(Graphics g) {
        frameCounter++;
        if(isPowerPellet && isPelletVisible) {
            if ((frameCounter/6)%2 ==0)
                g.drawImage(powerPelletImage, col * 32 ,23+ row*32, screen);
        }
        else if(isPelletVisible) {
            g.drawImage(pelletImage, col * 32 ,23+ row*32, screen);
        }
    }
}
