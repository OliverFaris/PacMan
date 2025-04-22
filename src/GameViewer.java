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
        g.drawImage(mazeImage, 0, 23,this);
        for (int i = 0; i < game.getMaze().length; i++) {
            for (int j = 0; j < game.getMaze()[i].length; j++) {
                g.setColor(Color.BLACK);
                g.drawRect(game.getMaze()[i][j].getCol() * 32, TITLE_BAR_HEIGHT + game.getMaze()[i][j].getRow() * 32, 32, 32);
                game.getMaze()[i][j].draw(g);
            }
        }
        game.getPlayer().draw(g);
//        g.setColor(Color.RED);
//        g.fillRect(game.getPlayer().getTileCol() *32, game.getPlayer().getTileRow() *32 +23, 32, 32);
//        g.setColor(Color.YELLOW);
//        g.fillRect(game.getPlayer().getX(), game.getPlayer().getY(), 32,32);
    }
}
