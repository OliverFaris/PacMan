import javax.swing.*;
import java.awt.*;

public class Blinky extends Ghost{
    private Image[][] blinkySprites;
    public Blinky(GameViewer screen) {
        super(screen);

        blinkySprites = new Image[][] {
                {new ImageIcon("Resources/Blinky/d0blinky.png").getImage(), new ImageIcon("Resources/Blinky/d1blinky.png").getImage()},
                {new ImageIcon("Resources/Blinky/l0blinky.png").getImage(), new ImageIcon("Resources/Blinky/l1blinky.png").getImage()},
                {new ImageIcon("Resources/Blinky/r0blinky.png").getImage(), new ImageIcon("Resources/Blinky/r1blinky.png").getImage()},
                {new ImageIcon("Resources/Blinky/u0blinky.png").getImage(), new ImageIcon("Resources/Blinky/u1blinky.png").getImage()}
        };
    }

    @Override
    public void findTarget(Player player) {
        if (phase == CHASE) {
            targetCol = player.getTileCol();
            targetRow = player.getTileRow();
        }
        else if (phase == SCATTER) {
            targetCol = 27;
            targetRow = 0;
        }
    }

    @Override
    public void drawGhost(Graphics g) {
        if (phase != FRIGHTENED) {
//            g.setColor(Color.RED);
//            g.fillRect(x, y, 32, 32);
            g.drawImage(blinkySprites[dir][(frameCounter/10) % 2], x -8, y -8, screen);
        }
        else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, 32, 32);
        }
    }
}
