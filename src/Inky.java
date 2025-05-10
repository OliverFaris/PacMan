import javax.swing.*;
import java.awt.*;

public class Inky extends Ghost{
    private Image[][] inkySprites;
    public Inky(GameViewer screen) {
        super(screen);
        inkySprites = new Image[][] {
                {new ImageIcon("Resources/Inky/d0inky.png").getImage(), new ImageIcon("Resources/Inky/d1inky.png").getImage()},
                {new ImageIcon("Resources/Inky/l0inky.png").getImage(), new ImageIcon("Resources/Inky/l1inky.png").getImage()},
                {new ImageIcon("Resources/Inky/r0inky.png").getImage(), new ImageIcon("Resources/Inky/r1inky.png").getImage()},
                {new ImageIcon("Resources/Inky/u0inky.png").getImage(), new ImageIcon("Resources/Inky/u1inky.png").getImage()}
        };
    }

    @Override
    public void findTarget(Player player) {
        if (phase == CHASE) {
            if (player.getDx() != 0) {
                targetCol = player.getTileCol() - ((player.getDx() / 10) * 4);
                targetRow = player.getTileRow();
            }
            else if (player.getDy() != 0){
                targetCol = player.getTileCol();
                targetRow = player.getTileRow() - ((player.getDy() / 10) * 4);
            }
        }

        else if (phase == SCATTER) {
            targetCol = 27;
            targetRow = 30;
        }
    }

    @Override
    public void drawGhost(Graphics g) {
        if (phase != FRIGHTENED) {
//            g.setColor(Color.RED);
//            g.fillRect(x, y, 32, 32);
            g.drawImage(inkySprites[dir][(frameCounter/8) % 2], x -9, y -9, 50, 50, screen);
        }
        else {
            g.drawImage(frightenedSprites[(frameCounter/8) % 2], x -9, y -9, 50, 50, screen);
        }
    }
}
