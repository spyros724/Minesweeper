package implementation;
public abstract class Box {
    
    private final int row;
    private final int column;
    private boolean revealed;
    private boolean flaged;
    private String screen;
    private String reality;

    public Box(int row, int column, String reality) {
        this.row=row;
        this.column=column;
        revealed=false;
        flaged=false;
        screen="initial";
        this.reality=reality;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isFlaged() {
        return flaged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public String getScreen() {
        return screen;
    }

    public String getReality() {
        return reality;
    }

    public void flag() {
        if(!flaged && !revealed) {
            flaged=true;
            revealed=false;
            screen="flag";
        }
    }

    public void unflag() {
        if(flaged && !revealed) {
            flaged=false;
            revealed=false;
            screen="initial";
        }
    }

    public void reveal() {
        if(!flaged && !revealed) {
            
            flaged=false;
            revealed=true;
            screen=reality;
        }
    }

    public void alwaysreveal() {
        flaged=false;
        revealed=true;
        screen=reality;
    }

    public boolean isZero(){
        return reality.equals("0");
    }

    public abstract boolean isBomb();
    public abstract boolean isSuperBomb();

}