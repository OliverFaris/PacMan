import java.awt.*;

public class Blinky extends Ghost{
    public Blinky(GameViewer screen) {
        super(screen);
    }

    @Override
    public void findTarget(Player player) {
        targetCol = player.getTileCol();
        targetRow = player.getTileRow();
    }

    @Override
    public void drawGhost(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 32, 32);
    }
}
