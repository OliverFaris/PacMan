import java.awt.*;
import java.util.ArrayList;

public abstract class Ghost {
    protected int phase;
    protected int dx, dy;
    protected int x, y;
    protected int tileRow, tileCol;
    protected int targetRow, targetCol;
    protected final int SPEED = 12;
    protected final int BUFFER_PIXELS = 12;
    protected GameViewer screen;
    protected ArrayList<Tile> directions;

    public Ghost(GameViewer screen) {
        this.tileCol = 14;
        this.tileRow = 11;
        this.dy = 0;
        this.dx = SPEED;
        this.directions = new ArrayList<>();

        // 14 * 32
        this.x = 448;
        // 11 * 32 + 23
        this.y = 375;

        this.screen = screen;
    }

    public void findRowCol() {
        this.tileRow = (y-23 +16)/32;
        this.tileCol = (x+16)/32;
    }

    public void move(Tile[][] maze, Player player) {
        findRowCol();
        findTarget(player);
        canTurn(maze);
        chase(maze);
        checkPortal(maze);
        this.x += dx;
        this.y += dy;
        if (checkPlayerCollision(player)) {
            System.out.println("suop");
        }
    }

    public boolean checkPlayerCollision(Player player) {
        return player.getTileRow() == tileRow && player.getTileCol() == tileCol;
    }

    public abstract void findTarget(Player player);

    public void chooseDir(char direction) {
        switch (direction) {
            case 'r':
                y = tileRow * 32 + 23;
                dx = SPEED;
                dy = 0;
                break;
            case 'l':
                y = tileRow * 32 + 23;
                dx = -SPEED;
                dy = 0;
                break;
            case 'u':
                x = tileCol * 32;
                dx = 0;
                dy = -SPEED;
                break;
            case 'd':
                x = tileCol * 32;
                dx = 0;
                dy = SPEED;
                break;
        }
    }

    public void canTurn(Tile[][] maze) {
        // Up
        if (dy <= 0 && !maze[tileRow - 1][tileCol].getIsWall() && x % 32 < BUFFER_PIXELS) {
            directions.add(new Tile(tileRow -1, tileCol, 'u'));
        }
        // Left
        if (dx <= 0 && !maze[tileRow][tileCol - 1].getIsWall() && (y - 23) % 32 < BUFFER_PIXELS) {
            directions.add(new Tile(tileRow, tileCol -1, 'l'));
        }
        // Down
        if (dy >= 0 && !maze[tileRow + 1][tileCol].getIsWall() && x % 32 < BUFFER_PIXELS) {
            directions.add(new Tile(tileRow +1, tileCol, 'd'));
        }
        // Right
        if (dx >= 0 && !maze[tileRow][tileCol + 1].getIsWall() && (y - 23) % 32 < BUFFER_PIXELS) {
            directions.add(new Tile(tileRow, tileCol +1, 'r'));
        }
    }

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

    public void frightend() {
        if (!directions.isEmpty()) {
            int index = (int) (Math.random() * directions.size());
            chooseDir(directions.get(index).getRelativeDir());
            directions.clear();
        }
    }

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

    public abstract void drawGhost(Graphics g);
}