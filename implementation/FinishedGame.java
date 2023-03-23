package implementation;
public class FinishedGame {
    private final int bombs;
    private final int moves;
    private final int time;
    private final String winner;

    public FinishedGame(int bombs, int moves, int time, String winner) {
        this.bombs=bombs;
        this.moves=moves;
        this.time=time;
        this.winner=winner;
    }

    public int getBombs() {
        return bombs;
    }

    public int getMoves() {
        return moves;
    }

    public int getTime() {
        return time;
    }

    public String getWinner() {
        return winner;
    }
}