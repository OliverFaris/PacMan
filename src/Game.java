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
    private int gamePhase;
    private Tile[][] maze;
    private final int MAZE_WIDTH = 28;
    private final int MAZE_HEIGHT = 31;
    private Player player;
    private static final int SLEEP_TIME = 41;
    private final int BLINKY = 0;
    private final int PINKY = 1;
    private final int INKY = 2;
    private final int CLYDE = 3;
    // Ghosts

    private Ghost[] ghosts;

    public Game() {
        window = new GameViewer(this);
        maze = new Tile[MAZE_HEIGHT][MAZE_WIDTH];
        this.player = new Player(window);
        this.gamePhase = 0;

        this.ghosts = new Ghost[]{new Blinky(window), new Pinky(window), new Inky(window), new Clyde(window)};

        window.addKeyListener(this);
    }

    public Player getPlayer() {
        return player;
    }

    public Tile[][] getMaze() {
        return maze;
    }

    public Ghost[] getGhosts() {
        return ghosts;
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
        // Player can only use arrow keys when they haven't won yet
        if (gamePhase != 3) {
            // Sets the next direction for pac man because pac man can "pre-move"
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP, KeyEvent.VK_W:
                    player.setNextDirection('u');
                    gamePhase = 1;
                    break;
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                    player.setNextDirection('r');
                    gamePhase = 1;
                    break;
                case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                    player.setNextDirection('d');
                    gamePhase = 1;
                    break;
                case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                    player.setNextDirection('l');
                    gamePhase = 1;
                    break;
            }
            window.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Player wins
        if (player.getPoints() == 2620) {
            gamePhase = 3;
        }
        // Handles ghost collision
        if(Ghost.didGhostCollide) {
            player.minusLife();
            System.out.println(player.getLives());
            gamePhase = 0;
            for (Ghost ghost : ghosts) {
                ghost.reset();
                player.reset();
            }
            window.repaint();
        }
        // Normal movement
        if (gamePhase == 1 && player.getLives() > 0) {
            player.move(maze);
            ghosts[BLINKY].move(maze, player);
            ghosts[PINKY].move(maze, player);
            ghosts[INKY].move(maze, player);
            ghosts[CLYDE].move(maze, player);
            window.repaint();
        }
    }
}
