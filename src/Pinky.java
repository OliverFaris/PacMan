import java.awt.*;

public class Pinky extends Ghost{
    public Pinky(GameViewer screen) {
        super(screen);
    }

    @Override
    public void findTarget(Player player) {
        if (phase == CHASE) {
            if (player.getDx() != 0) {
                targetCol = ((player.getDx() / 10) * 4) + player.getTileCol();
                targetRow = player.getTileRow();
            }
            else if (player.getDy() != 0){
                targetCol = player.getTileCol();
                targetRow = ((player.getDy() / 10) * 4) + player.getTileRow();
            }
        }

        else if (phase == SCATTER) {
            targetCol = 0;
            targetRow = 0;
        }
    }

    @Override
    public void drawGhost(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(targetCol * 32, targetRow * 32 + 23, 32, 32);

        g.setColor(Color.PINK);
        g.fillRect(x, y, 32, 32);
    }
}
