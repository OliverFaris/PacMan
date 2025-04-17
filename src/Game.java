import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
    private GameViewer window;
    private int GamePhase;
    private Tile[][] maze;
    private final int MAZE_WIDTH = 28;
    private final int MAZE_HEIGHT = 31;
    private Player player;
    private Ghost[] ghosts;

    public Game() {
        window = new GameViewer(this);
        maze = new Tile[MAZE_HEIGHT][MAZE_WIDTH];
    }

    public Tile[][] getMaze() {
        return maze;
    }

    public void gameLoop() {
        initializeMaze();
        window.repaint();
    }

    public void initializeMaze() {
        try {
            File myObj = new File("Resources/maze.txt");
            Scanner myReader = new Scanner(myObj);

            for (int i=0; i<MAZE_HEIGHT; i++) {
                String line = myReader.nextLine();

                for (int j=0; j<MAZE_WIDTH; j++) {
                    // Create a new MazeCell for each location

                    // Set if it is a wall or the start or end cell
                    if (line.charAt(j) == 'o') {
                        maze[i][j] = new Tile(i, j, false, true, false);
                    }
                    else if ( (i == 3 && (j == 1 || j == 26)) || (i == 23 && (j == 1 || j == 26))) {
                        maze[i][j] = new Tile(i, j, false, true, true);
                    }
                    else
                        maze[i][j] = new Tile(i, j, true, false, false);
                }
            }
            myReader.close();

            for (int i = 0; i < MAZE_HEIGHT; i++) {
                System.out.println("");
                for (int j = 0; j < MAZE_WIDTH; j++) {
                    if (maze[i][j].getIsWall())
                        System.out.print("#");
                    else
                        System.out.print(" ");
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameLoop();
    }
}
