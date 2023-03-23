package implementation;
public class BombBox extends Box {

    private int issuper;

    public BombBox(int row, int column, int issuper) {
        super(row, column, "bomb");
        this.issuper=issuper;
    }
    
    public boolean isBomb() {
        
        return true;
    }

    public boolean isSuperBomb() {
        if(issuper==1) {return true;}
        return false;
    }

    public int getIssuper() {
        return issuper;
    }
}