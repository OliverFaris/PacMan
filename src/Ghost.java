import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Ghost {
    // Movement and position
    protected int dx, dy;
    protected int x, y;
    protected int tileRow, tileCol;
    protected int targetRow, targetCol;
    protected int dir;

    protected ArrayList<Tile> directions;
    protected int frameCounter;
    protected int frightenedFrames;
    protected static boolean didGhostCollide;
    protected boolean isFrightened;

    // Phases
    protected static int phase;
    protected int lastPhase;
    protected final int SCATTER = 0;
    protected final int CHASE = 1;
    protected final int FRIGHTENED = 2;
    protected final int RIGHT = 2;
    protected final int LEFT = 1;
    protected final int UP = 3;
    protected final int DOWN = 0;

    // Speed
    protected int speed;
    protected int bufferPixels;

    protected Image[] frightenedSprites;

    // Screen
    protected GameViewer screen;

    public Ghost(GameViewer screen) {
        this.tileCol = 14;
        this.tileRow = 11;
        this.dy = 0;
        this.dx = speed = 11;
        this.bufferPixels = speed;
        this.dir = RIGHT;
        this.didGhostCollide = false;
        this.isFrightened = false;

        this.directions = new ArrayList<>();
        this.phase = SCATTER;
        this.lastPhase = -1;
        this.frameCounter = 0;
        this.frightenedFrames = 0;

        // 14 * 32
        this.x = 448;
        // 11 * 32 + 23
        this.y = 375;

        this.screen = screen;

        this.frightenedSprites = new Image[] {new ImageIcon("Resources/0frightened.png").getImage(), new ImageIcon("Resources/1frightened.png").getImage()};
    }

    // Resets ghost motion after pac man loses a life
    public void reset() {
        tileCol = 14;
        tileRow = 11;
        dy = 0;
        dx = speed = 11;
        bufferPixels = speed;
        dir = RIGHT;
        didGhostCollide = false;
        phase = SCATTER;
        lastPhase = -1;
        frameCounter = 0;
        frightenedFrames = 0;
        x = 448;
        y = 375;
    }

    public void findRowCol() {
        this.tileRow = (y-23 +16)/32;
        this.tileCol = (x+16)/32;
    }

    public void move(Tile[][] maze, Player player) {
        findRowCol();
        canTurn(maze);
        // If pac man ate a power pellet make ghosts turn frightened
        if (player.isSuperPacman()) {
            if (frightenedFrames == 0) {
                lastPhase = phase;
                phase = FRIGHTENED;
                dx *= -1;
                dy *= -1;
                speed = 5;
                bufferPixels = 5;
                isFrightened = true;
            }
            frightend();
            frightenedFrames++;
            // Once 8 seconds have passed the ghosts are normal again
            if (frightenedFrames > 192) {
                player.setSuperPacman(false);
                phase = lastPhase;
            }
        }
        // Handles normal movement
        else {
            findTarget(player);
            chase(maze);
            frameCounter++;
            speed = 10;
            bufferPixels = 10;
            isFrightened = false;
            frightenedFrames = 0;
            // The ghosts chase for 20 seconds with 7 second scatter periods in between
            // The scatter periods only occur 4 times before the ghost chase pac man forever
            if (phase == SCATTER && (frameCounter == 168 || frameCounter == 816 || frameCounter == 1464 || frameCounter == 2112)) {
                phase = CHASE;
                dx *= -1;
                dy *= -1;
            }
            else if (phase == CHASE && (frameCounter == 648 || frameCounter == 1296 || frameCounter == 1944)) {
                phase = SCATTER;
                dx *= -1;
                dy *= -1;
            }
        }
        checkPortal(maze);
        this.x += dx;
        this.y += dy;
        if (checkPlayerCollision(player)) {
            didGhostCollide = true;
        }
    }

    public boolean checkPlayerCollision(Player player) {
        return player.getTileRow() == tileRow && player.getTileCol() == tileCol && !player.isSuperPacman();
    }

    public abstract void findTarget(Player player);

    // Corrects position and velocity based on the direction given
    public void chooseDir(char direction) {
        switch (direction) {
            case 'r':
                y = tileRow * 32 + 23;
                dx = speed;
                dy = 0;
                dir = RIGHT;
                break;
            case 'l':
                y = tileRow * 32 + 23;
                dx = -speed;
                dy = 0;
                dir = LEFT;
                break;
            case 'u':
                x = tileCol * 32;
                dx = 0;
                dy = -speed;
                dir = UP;
                break;
            case 'd':
                x = tileCol * 32;
                dx = 0;
                dy = speed;
                dir = DOWN;
                break;
        }
    }

    // Decides if the ghost can turn
    public void canTurn(Tile[][] maze) {
        // Up
        if (dy <= 0 && !maze[tileRow - 1][tileCol].getIsWall() && x % 32 < bufferPixels) {
            directions.add(new Tile(tileRow -1, tileCol, 'u'));
        }
        // Left
        if (dx <= 0 && !maze[tileRow][tileCol - 1].getIsWall() && (y - 23) % 32 < bufferPixels) {
            directions.add(new Tile(tileRow, tileCol -1, 'l'));
        }
        // Down
        if (dy >= 0 && !maze[tileRow + 1][tileCol].getIsWall() && x % 32 < bufferPixels) {
            directions.add(new Tile(tileRow +1, tileCol, 'd'));
        }
        // Right
        if (dx >= 0 && !maze[tileRow][tileCol + 1].getIsWall() && (y - 23) % 32 < bufferPixels) {
            directions.add(new Tile(tileRow, tileCol +1, 'r'));
        }
    }

    // Determines which tile will minimize the distance to their target tile
    public void chase(Tile[][] maze) {
        double smallest = Integer.MAX_VALUE;
        double distance =0;
        Tile optimalTile = null;
        for (int i=0; i < directions.size(); i++) {
            distance = Math.sqrt(Math.pow(targetCol - directions.get(i).getCol(), 2) + Math.pow(targetRow - directions.get(i).getRow(), 2));
            if (distance < smallest) {
                smallest = distance;
                optimalTile = directions.get(i);
            }
        }
        directions.clear();
        if (optimalTile != null) {
            chooseDir(optimalTile.getRelativeDir());
        }
    }

    // Chooses random directions
    public void frightend() {
        if (!directions.isEmpty()) {
            int index = (int) (Math.random() * directions.size());
            chooseDir(directions.get(index).getRelativeDir());
            directions.clear();
        }
    }

    // Checks if a ghost is going through a portal
    public void checkPortal(Tile[][] maze) {
        if (tileCol == 1 && dx < 0 && x % 32 < bufferPixels) {
            // 26*32 -32
            x = 800;
            tileCol = 26;
        }
        else if(tileCol == 26 && dx > 0 && x % 32 < bufferPixels) {
            x = 32;
            tileCol = 1;
        }
    }

    public abstract void drawGhost(Graphics g);
}