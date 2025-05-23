import javax.swing.*;
import java.awt.*;

public class Player {
    private int lives;
    private int points;
    private int dx, dy;
    private int x, y;
    private int tileRow, tileCol;
    private char nextDirection;
    private Image[] currentSprites;
    private final int SPEED = 11;
    private final int BUFFER_PIXELS = 11;
    private GameViewer screen;
    private int frameCounter;
    private boolean isSuperPacman;

    private Image[] rPacmans, uPacmans, lPacmans, dPacmans;

    public Player(GameViewer screen) {
        this.lives = 3;
        this.points = 0;
        this.dx = 0;
        this.dy = 0;
        this.tileRow = 23;
        this.tileCol = 14;
        this.nextDirection = 'x';
        // 14 x 32 - 16
        this.x = 432;
        // 23 x 32 + 23
        this.y = 759;
        this.screen = screen;
        this.frameCounter = 0;
        this.isSuperPacman = false;

        this.rPacmans = new Image[] {new ImageIcon("Resources/Pacmans/rOpenPacman.png").getImage(), new ImageIcon("Resources/Pacmans/rPacman.png").getImage()};
        this.uPacmans = new Image[] {new ImageIcon("Resources/Pacmans/uOpenPacman.png").getImage(), new ImageIcon("Resources/Pacmans/uPacman.png").getImage()};
        this.lPacmans = new Image[] {new ImageIcon("Resources/Pacmans/lOpenPacman.png").getImage(), new ImageIcon("Resources/Pacmans/lPacman.png").getImage()};
        this.dPacmans = new Image[] {new ImageIcon("Resources/Pacmans/dOpenPacman.png").getImage(), new ImageIcon("Resources/Pacmans/dPacman.png").getImage()};
        this.currentSprites = new Image[] {null, null, new ImageIcon("Resources/Pacmans/fullPacman.png").getImage()};
    }

    // Resets the players position when life is lost
    public void reset() {
        dx = 0;
        dy = 0;
        tileRow = 23;
        tileCol = 14;
        nextDirection = 'x';
        x = 432;
        y = 759;
        isSuperPacman = false;
    }

    // Moves pac man
    public void move(Tile[][] maze) {
        findRowCol();
        checkTurn(maze);
        checkWallCollision(maze);
        checkPortal(maze);
        eatPellet(maze);
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
            // Align to grid and stop movement
            x = tileCol *32;
            dx = 0;
        }
        // Going up or down, is there a wall in front of pacman?
        else if ((dy < 0 && maze[tileRow -1][tileCol].getIsWall() || dy > 0 && maze[tileRow +1][tileCol].getIsWall()) && (y -23) % 32 <BUFFER_PIXELS) {
            y = tileRow *32 +23;
            dy = 0;
        }
    }

    public void checkTurn(Tile[][] maze) {
        // You can only turn when aligned
        // Or you don't need to be aligned when doing a 180
        if(x % 32 < BUFFER_PIXELS && (y-23) % 32 < BUFFER_PIXELS || (nextDirection == 'r' || nextDirection == 'l') || (nextDirection == 'u' || nextDirection == 'd')) {
            if (nextDirection == 'r' && !maze[tileRow][tileCol + 1].getIsWall()) {
                y = tileRow *32 +23;
                dx = SPEED;
                dy = 0;
                replaceImage(rPacmans);
            } else if (nextDirection == 'l' && !maze[tileRow][tileCol - 1].getIsWall()) {
                y = tileRow *32 +23;
                dx = -SPEED;
                dy = 0;
                replaceImage(lPacmans);
            } else if (nextDirection == 'u' && !maze[tileRow - 1][tileCol].getIsWall()) {
                x = tileCol *32;
                dx = 0;
                dy = -SPEED;
                replaceImage(uPacmans);
            } else if (nextDirection == 'd' && !maze[tileRow + 1][tileCol].getIsWall()) {
                x = tileCol *32;
                dx = 0;
                dy = SPEED;
                replaceImage(dPacmans);
            }
        }
    }
    // Checks if pac man is using the portal
    public void checkPortal(Tile[][] maze) {
        if (tileCol == 1 && dx < 0 && x % 32 < BUFFER_PIXELS) {
            // 26*32 -32
            x = 800;
            tileCol = 26;
        }
        else if(tileCol == 26 && dx > 0 && x % 32 < BUFFER_PIXELS) {
            x = 32;
            tileCol = 1;
        }
    }

    public void eatPellet(Tile[][] maze) {
        Tile currentTile = maze[tileRow][tileCol];
        if (currentTile.isPelletVisible()) {
            // Player gets 50 points for a power pellet and only 10 points for a normal pellet
            if(currentTile.isPowerPellet()) {
                points += 40;
                isSuperPacman = true;
            }
            points+= 10;
            // Make pellet invisible
            currentTile.setPelletVisible(false);
        }
    }

    public boolean isSuperPacman() {
        return isSuperPacman;
    }

    public void setSuperPacman(boolean superPacman) {
        isSuperPacman = superPacman;
    }

    public void replaceImage(Image[] arrReplaced) {
        for (int i = 0; i < currentSprites.length -1; i++) {
            currentSprites[i] = arrReplaced[i];
        }
    }
    // Draws pac man based on the direction he is facing
    public void drawPacman(Graphics g) {
        frameCounter++;
        Image currentImage = currentSprites[(frameCounter) % 3];
        if (dx == 0 && dy == 0)
            currentImage = currentSprites[2];
        g.drawImage(currentImage, x -8, y -8, screen);
    }

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }

    public int getTileRow() {
        return tileRow;
    }

    public int getTileCol() {
        return tileCol;
    }

    public void setNextDirection(char nextDirection) {
        this.nextDirection = nextDirection;
    }

    public int getPoints() {
        return points;
    }

    public int getLives() {
        return lives;
    }

    public void minusLife() {
        this.lives--;
    }
}
