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

    public Tile(int row, int col, boolean isWall, boolean isPelletVisible, boolean isPowerPellet, GameViewer screen) {
        this.row = row;
        this.col = col;
        this.isWall = isWall;
        this.isPelletVisible = isPelletVisible;
        this.isPowerPellet = isPowerPellet;
        this.screen = screen;
        this.pelletImage = new ImageIcon("Resources/pellet2.png").getImage();
        this.powerPelletImage = new ImageIcon("Resources/powerPellet2.png").getImage();
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
        if(isPowerPellet && isPelletVisible) {
            g.drawImage(powerPelletImage, col * 32 ,23+ row*32, screen);
//            g.fillOval(5 + col * 32, 5 + 23 + row * 32, 22, 22);
        }
        else if(isPelletVisible) {
            g.drawImage(pelletImage, col * 32 ,23+ row*32, screen);
//            g.fillOval(10 + col * 32, 10 + 23 + row * 32, 12, 12);
        }
    }
}
