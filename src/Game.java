import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game implements KeyListener, ActionListener {
    private GameViewer window;
    private int GamePhase;
    private Tile[][] maze;
    private final int MAZE_WIDTH = 28;
    private final int MAZE_HEIGHT = 31;
    private Player player;
    private Ghost testGhost;
    private static final int SLEEP_TIME = 41;

    public Game() {
        window = new GameViewer(this);
        maze = new Tile[MAZE_HEIGHT][MAZE_WIDTH];
        this.player = new Player(window);
        this.testGhost = new Ghost(window);

        window.addKeyListener(this);
    }

    public Player getPlayer() {
        return player;
    }

    public Tile[][] getMaze() {
        return maze;
    }

    public Ghost getTestGhost() {
        return testGhost;
    }

    public void gameLoop() {
        initializeMaze();
        window.repaint();
    }

    public void initializeMaze() {
        try {
            File myObj = new File("Resources/maze.txt");
            Scanner myReader = new Scanner(myObj);

            for (int i = 0; i < MAZE_HEIGHT; i++) {
                String line = myReader.nextLine();
                for (int j = 0; j < MAZE_WIDTH; j++) {

                    // Add power pellets
                    if ((i == 3 && (j == 1 || j == 26)) || (i == 23 && (j == 1 || j == 26))) {
                        maze[i][j] = new Tile(i, j, false, true, true, window);
                    }
                    // Add normal pellets
                    else if (line.charAt(j) == 'o') {
                        maze[i][j] = new Tile(i, j, false, true, false, window);
                        // Remove pellets from certain areas
                        if (((j >= 7 && j < 21) && (i >= 9 && i < 20)) || ((j <= 5 || j >= 22) && i == 14))
                            maze[i][j].setPelletVisible(false);
                    }
                    // Everything else is a wall
                    else
                        maze[i][j] = new Tile(i, j, true, false, false, window);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameLoop();
        Timer clock = new Timer(SLEEP_TIME, game);
        clock.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.setNextDirection('u');
                break;
            case KeyEvent.VK_RIGHT:
                player.setNextDirection('r');
                break;
            case KeyEvent.VK_DOWN:
                player.setNextDirection('d');
                break;
            case KeyEvent.VK_LEFT:
                player.setNextDirection('l');
                break;
        }
        window.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.findRowCol();
        player.checkTurn(maze);
        player.checkWallCollision(maze);
        player.checkPortal(maze);
        player.eatPellet(maze);
        player.move();
        testGhost.move();
        window.repaint();
    }
}
