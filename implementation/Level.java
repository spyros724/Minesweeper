package implementation;
public class Level {
    private BoxArray game;
    private int rows;
    private int columns;
    private int bombs;
    private int time;
    private boolean superbomb;
    private String dir;

    public Level(int rows, int columns, int bombs, int time, boolean superbomb, String dir) {
        game = new BoxArray(rows, columns, bombs, superbomb, dir);
        this.rows=rows;
        this.columns=columns;
        this.bombs=bombs;
        this.time=time;
        this.superbomb=superbomb;
        this.dir=dir;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBombs() {
        return bombs;
    }

    public BoxArray getGame() {
        return game;
    }

    public boolean isSuper() {
        return superbomb;
    }
}