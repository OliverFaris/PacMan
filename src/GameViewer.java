import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameViewer extends JFrame {
    private Game game;
    private final int TITLE_BAR_HEIGHT = 23;
    private Image mazeImage;

    // 29 x 32
    private final int WINDOW_WIDTH = 896;
    // 31 x 32 + 23
    private final int WINDOW_HEIGHT = 1015;


    public GameViewer(Game game) {
        this.game = game;
        this.mazeImage = new ImageIcon("Resources/maze.png").getImage();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Pac Man");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        createBufferStrategy(2);
    }

    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;
        Graphics g2 = null;
        try {
            g2 = bf.getDrawGraphics();
            myPaint(g2);
        }
        finally {
            g2.dispose();
        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }


    public void myPaint(Graphics g) {
        // Draw maze and pellets
        g.drawImage(mazeImage, 0, 23,this);
        for (int i = 0; i < game.getMaze().length; i++) {
            for (int j = 0; j < game.getMaze()[i].length; j++) {
                game.getMaze()[i][j].draw(g);
            }
        }
        // Draw ghosts
        for (Ghost ghost : game.getGhosts()) {
            ghost.drawGhost(g);
        }
        // Draw player
        game.getPlayer().drawPacman(g);
    }
}
