package implementation;
import java.lang.Math;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents the field of boxes that are the 
 * central part of the Minesweeper game.
 */
public class BoxArray {
    private Box[][] boxarray;
    private int rows;
    private int columns;
    private BombBox[] bombarray;
    private int bombs;
    private boolean superbomb;
    private String dir;

    /**
     * Creates a field of boxes with the following characteristics.
     * @param rows The number of rows that the field has.
     * @param columns The number of columns that the field has.
     * @param bombs The number of hidden bombs that the field has.
     * @param superbomb Defines wether there is a superbomb or not. 
     * @param dir The address of the working directory.
     */

    public BoxArray(int rows, int columns, int bombs, boolean superbomb, String dir){
        this.bombs=bombs;
        this.rows=rows;
        this.columns=columns;
        this.superbomb=superbomb;
        this.dir=dir;
        boxarray= new Box[rows][columns];
        bombarray= new BombBox[bombs];
        createboxarray();
        storebombs(dir);
    }

    /**
     * Places the bombs in the field randomly, so that the player
     * does not know their positions in the first place.
     */

    public void createboxarray() {
        int iterations=0;
        while(iterations != bombs) {
            int randrow = (int) (Math.ceil(Math.random()*(rows-1))); 
            int randcolumn = (int) (Math.ceil(Math.random()*(columns-1)));
            if (!(boxarray[randrow][randcolumn] instanceof BombBox)) {
                if(superbomb && iterations==0) {
                    boxarray[randrow][randcolumn] = new BombBox(randrow, randcolumn, 1);
                    bombarray[iterations] = (BombBox) getBox(randrow, randcolumn);
                    iterations++;
                }
                else {
                    boxarray[randrow][randcolumn] = new BombBox(randrow, randcolumn, 0);
                    bombarray[iterations] = (BombBox) getBox(randrow, randcolumn);
                    iterations++;
                }
            }
        }
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++) {
                if (!(boxarray[r][c] instanceof BombBox)) {
                    int i = NeighbourBombs(r, c);
                    boxarray[r][c] = new BlankBox(i, r, c);
                }
            }
        }
    }

    /**
     * Calculates for a box in a specific location 
     * how many of its neighbouring boxes contain bombs.
     * @param r The row in which the box, whose neighbour-bombs
     *           we want to calculate, is located.
     * @param c The column in which the box, whose neighbour-bombs
     *           we want to calculate, is located.
     * @return The number of bombs that are next to this box.
     */

    public int NeighbourBombs(int r, int c) {
        int count = 0;
        int[][] indexes = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] pair : indexes) {
            if ((0<=r+pair[0]) && (r+pair[0]<=rows-1) && (0<=c+pair[1]) && (c+pair[1]<=columns-1)){
                if ((boxarray[r+pair[0]][c+pair[1]]!=null) && (boxarray[r+pair[0]][c+pair[1]].isBomb()))
                    count++;
            }
        }
        return count;
    }

    /**
     * Gets a specific box out of the field.
     * @param row The row of the field in which
     *            the box is located.
     * @param column The column of the field in which
     *               the box is located.
     * @return The box from the specific row and column
     *          of the field.
     */
    
    public Box getBox(int row, int column) {
        return boxarray[row][column];
    }

    /**
     * Gets the vertical size of the field.
     * @return The number of rows that make up the field.
     */


    public int getRows() {
        return rows;
    }

    /**
     * Gets the horizontal size of the field.
     * @return The number of columns that make up the field.
     */

    public int getColumns() {
        return columns;
    }

    /**
     * Reveals the content of all the boxes
     * that are located in the field.
     */

    public void revealarray() {
        for (Box[] r : boxarray) {
            for (Box b : r){
                b.alwaysreveal();
            }
        }
    }

    /**
     * Stores information about every bomb
     * of the field in the mines.txt file which
     * is located in the medialab folder. 
     * Each bomb is given a row in the .txt file,
     * the first number describing each bomb is its 
     * row in the field, the second is its column
     * and the third describes if the bomb is a superbomb.
     * @param dir The address of the directory that contains 
     *            the medialab folder.
     */

    public void storebombs(String dir) {
        try {
            File myObj = new File(dir+"\\medialab\\mines.txt");
            if (myObj.createNewFile()) {
                try {
                    FileWriter myWriter = new FileWriter(dir+"\\medialab\\mines.txt");
                    for(BombBox b : bombarray) {
                        myWriter.write(String.valueOf(b.getRow()) + "," + String.valueOf(b.getColumn()) + "," + String.valueOf(b.getIssuper()) + "\n");
                    }
                    myWriter.close();
                  } catch (IOException e) {
                    System.out.println("Maybe you should delete revious mines.txt.");
                  }
            } 
            else {
              System.out.println("Maybe you should delete revious mines.txt..");
            }
          } catch (IOException e) {
            System.out.println("Maybe you should delete revious mines.txt..");
    }
}

  

}


