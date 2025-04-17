public class Tile {
    private boolean isWall;
    private int row;
    private int col;
    private boolean isPelletVisible;
    private boolean isPowerPellet;

    public Tile(int row, int col, boolean isWall, boolean isPelletVisible, boolean isPowerPellet) {
        this.row = row;
        this.col = col;
        this.isWall = isWall;
        this.isPelletVisible = isPelletVisible;
        this.isPowerPellet = isPowerPellet;
    }

    public boolean getIsWall() {
        return isWall;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }
}
