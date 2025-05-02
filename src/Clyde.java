import java.awt.*;

public class Clyde extends Ghost{
    public Clyde(GameViewer screen) {
        super(screen);
    }

    @Override
    public void findTarget(Player player) {
        targetCol = player.getTileCol();
        targetRow = player.getTileRow();
    }

    @Override
    public void drawGhost(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, 32, 32);
    }
}
