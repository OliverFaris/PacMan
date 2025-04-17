import javax.swing.*;
import java.awt.*;

public class GameViewer extends JFrame {
    private Game game;
    private final int TITLE_BAR_HEIGHT = 23;
    private Image pacMan;
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
    }

    public void paint(Graphics g) {
        g.drawImage(mazeImage, 0, 23,this);
        for (int i = 0; i < game.getMaze().length; i++) {
            for (int j = 0; j < game.getMaze()[i].length; j++) {
                g.setColor(Color.BLACK);
                g.drawRect(game.getMaze()[i][j].getCol() * 32, TITLE_BAR_HEIGHT + game.getMaze()[i][j].getRow() * 32, 32, 32);
                if(game.getMaze()[i][j].isPowerPellet()) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(5 + game.getMaze()[i][j].getCol() * 32, 5 + TITLE_BAR_HEIGHT + game.getMaze()[i][j].getRow() * 32, 22, 22);
                }
                else if(game.getMaze()[i][j].isPelletVisible()) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(10 + game.getMaze()[i][j].getCol() * 32, 10 + TITLE_BAR_HEIGHT + game.getMaze()[i][j].getRow() * 32, 12, 12);
                }
            }
        }
    }
}
