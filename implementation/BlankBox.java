package implementation;
public class BlankBox extends Box {
    
    private int neighbourbombs;

    public BlankBox(int neighbourbombs, int row, int column){
        super(row, column, String.valueOf(neighbourbombs));
        this.neighbourbombs=neighbourbombs;
    }

    public boolean isBomb() {
        return false;
    }

    public boolean isSuperBomb() {
        return false;
    }

    public int getNeighbourBombs(){
        return neighbourbombs;
    }
}