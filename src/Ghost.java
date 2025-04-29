import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;

public class Ghost {
    private int phase;
    private int dx, dy;
    private int x, y;
    private int tileRow, tileCol;
    private int targetRow, targetCol;
    private final int SPEED = 10;
    private final int BUFFER_PIXELS = 10;
    private GameViewer screen;
    private ArrayList<Character> directions;

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

    public void move() {
        this.x += dx;
        this.y += dy;
    }

    public void findTarget(Player player) {
        targetCol = player.getTileCol();
        targetRow = player.getTileRow();
    }

    public void moveToTarget(Tile[][] maze) {
        ArrayList<Double> potentialDirection = new ArrayList<>();
        if (maze[tileRow][tileCol +1].getIsWall())
            potentialDirection.add(Math.sqrt(Math.pow(targetCol - (tileCol+1), 2) + Math.pow(targetRow - tileRow, 2)));
        if (maze[tileRow][tileCol -1].getIsWall())
            potentialDirection.add(Math.sqrt(Math.pow(targetCol - (tileCol-1), 2) + Math.pow(targetRow - tileRow, 2)));
        if (maze[tileRow -1][tileCol].getIsWall())
            potentialDirection.add(Math.sqrt(Math.pow(targetCol - tileCol, 2) + Math.pow(targetRow - (tileRow -1), 2)));
        if (maze[tileRow +1][tileCol].getIsWall())
            potentialDirection.add(Math.sqrt(Math.pow(targetCol - tileCol, 2) + Math.pow(targetRow - (tileRow +1), 2)));
        double smallest = Integer.MAX_VALUE;
        for (Double dir : potentialDirection) {
            if (smallest < dir)
                smallest = dir;
        }

    }

    public void canTurn(Tile[][] maze) {
        if (dx >= 0 && !maze[tileRow][tileCol + 1].getIsWall() && (y - 23) % 32 < BUFFER_PIXELS) {
            directions.add('r');
        }
        if (dx <= 0 && !maze[tileRow][tileCol - 1].getIsWall() && (y - 23) % 32 < BUFFER_PIXELS) {
            directions.add('l');
        }
        if (dy <= 0 && !maze[tileRow - 1][tileCol].getIsWall() && x % 32 < BUFFER_PIXELS) {
            directions.add('u');
        }
        if (dy >= 0 && !maze[tileRow + 1][tileCol].getIsWall() && x % 32 < BUFFER_PIXELS) {
            directions.add('d');
        }
    }

    public void frightend() {
        if (!directions.isEmpty()) {
            int index = (int) (Math.random() * directions.size());
            switch (directions.get(index)) {
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

    public void drawGhost(Graphics g) {
//        g.setColor(Color.GREEN);
//        g.fillRect(tileCol * 32, tileRow *32 +23, 32,32);
        g.setColor(Color.RED);
        g.fillRect(x, y, 32, 32);
    }
}