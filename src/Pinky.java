import javax.swing.*;
import java.awt.*;

public class Pinky extends Ghost{
    private Image[][] pinkySprites;
    public Pinky(GameViewer screen) {
        super(screen);
        pinkySprites = new Image[][] {
                {new ImageIcon("Resources/Pinky/d0pinky.png").getImage(), new ImageIcon("Resources/Pinky/d1pinky.png").getImage()},
                {new ImageIcon("Resources/Pinky/l0pinky.png").getImage(), new ImageIcon("Resources/Pinky/l1pinky.png").getImage()},
                {new ImageIcon("Resources/Pinky/r0pinky.png").getImage(), new ImageIcon("Resources/Pinky/r1pinky.png").getImage()},
                {new ImageIcon("Resources/Pinky/u0pinky.png").getImage(), new ImageIcon("Resources/Pinky/u1pinky.png").getImage()}
        };
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
        if (phase != FRIGHTENED) {
//            g.setColor(Color.BLUE);
//        g.fillRect(targetCol * 32, targetRow * 32 + 23, 32, 32);
//            g.setColor(Color.PINK);
//            g.fillRect(x, y, 32, 32);
            g.drawImage(pinkySprites[dir][(frameCounter/8) % 2], x -9, y -9, 50, 50, screen);
        }
        else {
            g.drawImage(frightenedSprites[(frameCounter/8) % 2], x -9, y -9, 50, 50, screen);
        }
    }
}
