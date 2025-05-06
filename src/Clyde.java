import java.awt.*;

public class Clyde extends Ghost{
    public Clyde(GameViewer screen) {
        super(screen);
    }

    @Override
    public void findTarget(Player player) {
        int distanceToPlayer = (int) Math.sqrt(Math.pow(player.getTileCol()- tileCol, 2) + Math.pow(player.getTileRow() - tileRow, 2));
        if (phase == CHASE && distanceToPlayer > 8){
            targetCol = player.getTileCol();
            targetRow = player.getTileRow();
        }

        if (phase == SCATTER || distanceToPlayer <= 8) {
            targetCol = 0;
            targetRow = 30;
        }
    }

    @Override
    public void drawGhost(Graphics g) {
        if (phase != FRIGHTENED) {
            g.setColor(Color.ORANGE);
            g.fillRect(x, y, 32, 32);
        }
        else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, 32, 32);
        }
    }
}
