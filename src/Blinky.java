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
    // Target is right on pacman
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
        // Animates and draws ghost based on what phase they are in
        if (phase != FRIGHTENED) {
            g.drawImage(blinkySprites[dir][(frameCounter/8) % 2], x -9, y -9, 50, 50, screen);
        }
        else {
            g.drawImage(frightenedSprites[(frameCounter/8) % 2], x -9, y -9, 50, 50, screen);
        }
    }
}
