import javax.swing.*;
import java.awt.*;

public class Player {
    private int lives;
    private int points;
    private int dx, dy;
    private int x, y;
    private int tileRow, tileCol;
    private char nextDirection;
    private Image currentSprite;
    private final int SPEED = 10;
    private final int BUFFER_PIXELS = 10;
    private GameViewer screen;
    private int frameCounter;

    private Image rPacman, uPacman, lPacman, dPacman;

    public Player(GameViewer screen) {
        this.lives = 3;
        this.points = 0;
        this.dx = 0;
        this.dy = 0;
        this.tileRow = 23;
        this.tileCol = 14;
        this.nextDirection = 'x';
        this.currentSprite = null;
        // 14 x 32
        this.x = 448;
        // 23 x 32 + 23
        this.y = 759;
        this.screen = screen;
        this.frameCounter = 0;

        this.rPacman = new ImageIcon("Resources/rPacman.png").getImage();
        this.uPacman = new ImageIcon("Resources/uPacman.png").getImage();
        this.lPacman = new ImageIcon("Resources/lPacman.png").getImage();
        this.dPacman = new ImageIcon("Resources/dPacman.png").getImage();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void findRowCol() {
        this.tileRow = (y-23 +16)/32;
        this.tileCol = (x+16)/32;
    }

    public void checkWallCollision(Tile[][] maze) {
        // Going right or left, is there a wall in front of pacman?
        if ((dx > 0 && maze[tileRow][tileCol +1].getIsWall() || dx < 0 && maze[tileRow][tileCol -1].getIsWall()) && x % 32 <BUFFER_PIXELS) {
            if (dx > 0)
                currentSprite = rPacman;
            else
                currentSprite = lPacman;
            // Align to grid and stop movement
            x = tileCol *32;
            dx = 0;
        }
        // Going up or down, is there a wall in front of pacman?
        else if ((dy < 0 && maze[tileRow -1][tileCol].getIsWall() || dy > 0 && maze[tileRow +1][tileCol].getIsWall()) && (y -23) % 32 <BUFFER_PIXELS) {
            // Align to grid and stop movement
            if (dy > 0)
                currentSprite = dPacman;
            else
                currentSprite = uPacman;
            y = tileRow *32 +23;
            dy = 0;
        }
    }

    public void checkTurn(Tile[][] maze) {
        // You can only turn when aligned
        // Or you don't need to be aligned when doing a 180
        if(x % 32 < BUFFER_PIXELS && (y-23) % 32 < BUFFER_PIXELS || (dx != 0 && nextDirection == 'r' || nextDirection == 'l') || (dy != 0 && nextDirection == 'u' || nextDirection == 'd')) {
            if (nextDirection == 'r' && !maze[tileRow][tileCol + 1].getIsWall()) {
                y = tileRow *32 +23;
                dx = SPEED;
                dy = 0;
                currentSprite = rPacman;
            } else if (nextDirection == 'l' && !maze[tileRow][tileCol - 1].getIsWall()) {
                y = tileRow *32 +23;
                dx = -SPEED;
                dy = 0;
                currentSprite = lPacman;
            } else if (nextDirection == 'u' && !maze[tileRow - 1][tileCol].getIsWall()) {
                x = tileCol *32;
                dx = 0;
                dy = -SPEED;
                currentSprite = uPacman;
            } else if (nextDirection == 'd' && !maze[tileRow + 1][tileCol].getIsWall()) {
                x = tileCol *32;
                dx = 0;
                dy = SPEED;
                currentSprite = dPacman;
            }
        }
    }

    public void checkPortal(Tile[][] maze) {
        if (tileCol == 1 && dx < 0 && x % 32 < BUFFER_PIXELS) {
            // 26*32
            x = 832;
            tileCol = 27;
        }
        else if(tileCol == 26 && dx > 0 && x % 32 < BUFFER_PIXELS) {
            x = 0;
            tileCol = 1;
        }
    }

    public void eatPellet(Tile[][] maze) {
        Tile currentTile = maze[tileRow][tileCol];
        if (currentTile.isPelletVisible()) {
            // Player gets 50 points for a power pellet and only 10 points for a normal pellet
            if(currentTile.isPowerPellet())
                points+= 40;
            points+= 10;
            // Make pellet invisible
            currentTile.setPelletVisible(false);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(currentSprite, x -8, y -8, screen);
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTileRow() {
        return tileRow;
    }

    public int getTileCol() {
        return tileCol;
    }

    public char getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(char nextDirection) {
        this.nextDirection = nextDirection;
    }

    public int getPoints() {
        return points;
    }
}
