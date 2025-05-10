import javax.swing.*;
import java.awt.*;

public class Clyde extends Ghost{
    private Image[][] clydeSprites;
    public Clyde(GameViewer screen) {
        super(screen);

        clydeSprites = new Image[][] {
                {new ImageIcon("Resources/Clyde/d0clyde.png").getImage(), new ImageIcon("Resources/Clyde/d1clyde.png").getImage()},
                {new ImageIcon("Resources/Clyde/l0clyde.png").getImage(), new ImageIcon("Resources/Clyde/l1clyde.png").getImage()},
                {new ImageIcon("Resources/Clyde/r0clyde.png").getImage(), new ImageIcon("Resources/Clyde/r1clyde.png").getImage()},
                {new ImageIcon("Resources/Clyde/u0clyde.png").getImage(), new ImageIcon("Resources/Clyde/u1clyde.png").getImage()}
        };
    }

    @Override
    // Target is right on pac man until it is 8 tiles away
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
    // Animates and draws ghost based on what phase they are in
    public void drawGhost(Graphics g) {
        if (phase != FRIGHTENED) {
            g.drawImage(clydeSprites[dir][(frameCounter/8) % 2], x -9, y -9, 50, 50, screen);
        }
        else {
            g.drawImage(frightenedSprites[(frameCounter/8) % 2], x -9, y -9, 50, 50, screen);
        }
    }
}
