import java.awt.*;

public class Ghost {
    private int phase;
    private int dx, dy;
    private int x, y;
    private int currentRow, currentCol;
    private int targetRow, targetCol;
    private Image currentSprite;
    private final int SPEED = 10;
    private GameViewer screen;

    public Ghost(GameViewer screen) {
        this.currentCol = 14;
        this.currentRow = 11;
        this.dy = 0;
        this.dx = SPEED;

        // 14 * 32
        this.x = 448;
        // 11 * 32 + 23
        this.y = 375;

        this.screen = screen;
    }

    public void getTarget(){

    }

    public void findNextTile() {

    }

    public void move() {
        this.x += dx;
        this.y += dy;
    }

    public void drawGhost(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 32, 32);
    }
}