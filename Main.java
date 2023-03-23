import implementation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths; 
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.net.URL; 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;  
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javafx.scene.text.*;
import javafx.scene.text.Font.*;
import javafx.application.Platform;

public class Main extends Application { 

    public static int boardsize;
    public static int moves;
    public static int gamebombs;
    public static int flaged;
    public static int gametime;
    public static int countdown;
    public static boolean playing;
    public static boolean defeat;
    public static boolean win;
    public static boolean globalsuperbomb;
    public static GridPane board = new GridPane();
    public static Label countdownLabel = new Label();
    public static Label bombsLabel = new Label(); 
    public static Label flagedLabel = new Label();
    public static Label startLabel = new Label();  
    public static Level mylevel;
    public static BoxArray mygame;
    public static int endtime;
    public static FinishedGame round1;
    public static FinishedGame round2;
    public static FinishedGame round3;
    public static FinishedGame round4;
    public static FinishedGame round5;
    public static FinishedGame currentround;
    public static int rounds;
    public static boolean loaded;
    public static boolean success2=false;
    public static int tempbombs;
    public static int templevel;
    public static int tempsuperbomb;
    public static int temptime;
    public static String dir = System.getProperty("user.dir");
    
    
    public static void main(String[] args) {  
    launch(args);  
        }  
  
    @Override  
    public void start(Stage primaryStage) throws Exception {  
  //
        rounds=0;
        loaded=false;
        defeat=false;
        win=false;
        moves=0;
        gametime=0;
        flaged=0;
        playing=false;
    	primaryStage.setTitle("MediaLab Minesweeper");
        BorderPane root1 = new BorderPane();
        BorderPane root2 = new BorderPane(); 
        VBox layout= new VBox(10);
        layout.getChildren().addAll( countdownLabel, bombsLabel, flagedLabel, startLabel);
        root1.setCenter(layout);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(root1,root2);
        //
        Scene scene = new Scene(vBox, 700,700);  
        MenuBar menubar = new MenuBar();  
        Menu ApplicationMenu = new Menu("Application");  
        MenuItem applicationmenu1=new MenuItem("Create");  
        applicationmenu1.setOnAction(e -> displayPopupCreate());
        MenuItem applicationmenu2=new MenuItem("Load");  
        applicationmenu2.setOnAction(e -> {
            countdown=-3;
            playing=false;
            try {         
                File f= new File(dir+"\\medialab\\mines.txt"); 
                if(f.delete()) {}  
                else {}  
            }  
            catch(Exception p)  {p.printStackTrace();}  
            displayPopupLoad();
            if(success2) {
                flaged=0;
                moves=0;
                while(gametime==0) {}
                loaded=true;
                playing=false;
                defeat=false;
                win=false;
                createfield();
            }
            
        });
        MenuItem applicationmenu3=new MenuItem("Start");
        applicationmenu3.setOnAction(e -> {
            if(loaded && !playing) {
                startLabel.setText("");
                timer();
                playing=true;
                startLabel.setText("If you want to start a new game load first the description.");
            } 

        });
        MenuItem applicationmenu4=new MenuItem("Exit");
        applicationmenu4.setOnAction(e -> primaryStage.close());
        Menu DetailsMenu=new Menu("Details");  
        MenuItem DetailsMenu1=new MenuItem("Rounds"); 
        DetailsMenu1.setOnAction(e -> {
            displayPopupRounds();
        }); 
        MenuItem DetailsMenu2=new MenuItem("Solution"); 
        DetailsMenu2.setOnAction(e -> {
            if(playing) {
                playing=false;
                defeat=true;
                win=false;
                countdown=-1;
                countdownLabel.setText("Such a pitty you gave up :(");
                revealbombs();
                updateboard();
                updaterounds();
            }

        });   
        DetailsMenu.getItems().addAll(DetailsMenu1,DetailsMenu2);   
        root2.setCenter(board); 

        ApplicationMenu.getItems().addAll(applicationmenu1,applicationmenu2,applicationmenu3,applicationmenu4);  
        menubar.getMenus().addAll(ApplicationMenu,DetailsMenu); 
        root1.setTop(menubar); 
        primaryStage.setScene(scene);  
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();  
          
    }
    
    public static void displayPopupRounds() {
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        Button button1= new Button("Close");
        button1.setOnAction(e -> popupwindow.close());
        VBox layout= new VBox(3);
        ScrollPane r = new ScrollPane();
        Label l1= new Label();
        Label l1bombs= new Label();
        Label l1moves= new Label();
        Label l1time= new Label();
        Label l1winner= new Label();
        Label l2= new Label();
        Label l2bombs= new Label();
        Label l2moves= new Label();
        Label l2time= new Label();
        Label l2winner= new Label();
        Label l3= new Label();
        Label l3bombs= new Label();
        Label l3moves= new Label();
        Label l3time= new Label();
        Label l3winner= new Label();
        Label l4= new Label();
        Label l4bombs= new Label();
        Label l4moves= new Label();
        Label l4time= new Label();
        Label l4winner= new Label();
        Label l5= new Label();
        Label l5bombs= new Label();
        Label l5moves= new Label();
        Label l5time= new Label();
        Label l5winner= new Label();
        if(rounds==0) {
            l1.setText("You have not completed any rounds yet!");
                      
        }
        if(rounds>=1) {
            l1.setText("\nRound 1\n");
            l1bombs.setText("Bombs: " + String.valueOf(round1.getBombs()));
            l1moves.setText("Moves: " + String.valueOf(round1.getMoves()));
            l1time.setText("Seconds given: " + String.valueOf(round1.getTime()));
            l1winner.setText("Winner: " + round1.getWinner());
                        
        }
        if(rounds>=2) {
            l2.setText("\nRound 2\n");
            l2bombs.setText("Bombs: " + String.valueOf(round2.getBombs()));
            l2moves.setText("Moves: " + String.valueOf(round2.getMoves()));
            l2time.setText("Seconds given: " + String.valueOf(round2.getTime()));
            l2winner.setText("Winner: " + round2.getWinner());
            
        }
        if(rounds>=3) {
            l3.setText("\nRound 3\n");
            l3bombs.setText("Bombs: " + String.valueOf(round3.getBombs()));
            l3moves.setText("Moves: " + String.valueOf(round3.getMoves()));
            l3time.setText("Seconds given: " + String.valueOf(round3.getTime()));
            l3winner.setText("Winner: " + round3.getWinner());
             
        }
        if(rounds>=4) {
            l4.setText("\nRound 4\n");
            l4bombs.setText("Bombs: " + String.valueOf(round4.getBombs()));
            l4moves.setText("Moves: " + String.valueOf(round4.getMoves()));
            l4time.setText("Seconds given: " + String.valueOf(round4.getTime()));
            l4winner.setText("Winner: " + round4.getWinner());
            
        }
        if(rounds>=5) {
            l5.setText("\nRound 5\n");
            l5bombs.setText("Bombs: " + String.valueOf(round5.getBombs()));
            l5moves.setText("Moves: " + String.valueOf(round5.getMoves()));
            l5time.setText("Duration: " + String.valueOf(round5.getTime()));
            l5winner.setText("Winner: " + round5.getWinner());
            
        }
        layout.getChildren().addAll(l1, l1bombs, l1moves, l1time, l1winner,
                                    l2, l2bombs, l2moves, l2time, l2winner,
                                    l3, l3bombs, l3moves, l3time, l3winner,
                                    l4, l4bombs, l4moves, l4time, l4winner,
                                    l5, l5bombs, l5moves, l5time, l5winner, button1);
        r.setContent(layout);
        r.setFitToWidth(true);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(r, 700, 700);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();      
    }


    public static void displayPopupLoad() {
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Load description file");
        Label l1 = new Label("Press enter to load the game description.");
        TextField b = new TextField("SCENARIO-ID.txt");
        TilePane r = new TilePane();
        Label l2 = new Label();
        Label l3 = new Label();
        Label l4 = new Label();
        Label l5 = new Label();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            boolean success1=false;
                String filename=b.getText();
                try {
                    l3.setText("");
                    readfile(filename, l3); 
                    l4.setText("");
                    success1=true;
                } catch (InvalidDescriptionException excep) {
                    l4.setText("Invalid file structure!");
                }
                /* 
    	      File myObj = new File(filename);
    	      Scanner myReader = new Scanner(myObj);
    	        int level = Integer.parseInt(myReader.nextLine());
    	        int bombs = Integer.parseInt(myReader.nextLine());
    	        int time = Integer.parseInt(myReader.nextLine());
                int superbomb = Integer.parseInt(myReader.nextLine());
                myReader.close();
                */
                success2=false;
                if(success1) {
                    try {
                        l4.setText("");
                        loadvalues(templevel, tempbombs, temptime, tempsuperbomb);
                        l3.setText("Description loaded.");
                        success2=true;
                      }
                      catch (InvalidValueException  ex) {
                        l3.setText("Invalid values!");
                        l4.setText("Load a valid description!");
                      } 
                }
              if(success2) {
                countdownLabel.setText("Remaining seconds: " + String.valueOf(gametime));
                bombsLabel.setText("There are " + String.valueOf(gamebombs) + " bombs in this game.");
                flagedLabel.setText("Bombs flaged: " + String.valueOf(flaged));
                startLabel.setText("You have to press Start in order to play.");
              }
              /* 
    	    } catch (FileNotFoundException m) {
                l3.setText("File not found.");
                l2.setText("");
    	        l4.setText("");
                }
                */
            }
        };
        b.setOnAction(event);
        Button button1= new Button("Close");
        button1.setOnAction(e -> popupwindow.close());
    VBox layout= new VBox(10);
    layout.getChildren().addAll( b, l1, r, l2, l3, l4, button1);
    layout.setAlignment(Pos.CENTER);
    Scene scene1= new Scene(layout, 300, 250);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();      
}


public static void displayPopupCreate()
{
Stage popupwindow=new Stage();
      
popupwindow.initModality(Modality.APPLICATION_MODAL);
popupwindow.setTitle("Create description file");

Label l1 = new Label("Enter the name of the file you want to be created.");
TextField b1 = new TextField("SCENARIO-ID.txt");
Label l2 = new Label("Enter the level, 1 or 2.");
TextField b2 = new TextField("1");
Label l3 = new Label("Enter the number of bombs.");
TextField b3 = new TextField("9");
Label l4 = new Label("Enter 1 to create a superbomb, otherwise enter 0.");
TextField b4 = new TextField("0");
Label l5 = new Label("Enter time (in seconds).");
TextField b5 = new TextField("120");
Button submit = new Button("Submit");  
Label l6 = new Label();
    submit.setOnAction(e->{
        try {
            File myObj = new File(b1.getText());
            if (myObj.createNewFile()) {
                try {
                    FileWriter myWriter = new FileWriter(dir+"\\medialab\\"+b1.getText());
                    myWriter.write(String.valueOf(b2.getText()) + "\n" + String.valueOf(b3.getText()) + "\n" + String.valueOf(b5.getText()) + "\n" + String.valueOf(b4.getText()));
                    myWriter.close();
                    l6.setText("The file has been created.");
                  } catch (IOException t) {
                    l6.setText("An error occured.");
                  }
            } 
            else {
                l6.setText("There is already a file with this name, please name the file again.");
            }
          } catch (IOException k) {
            l6.setText("An error occured.");
        }
    });  
Button button1= new Button("Close");
     
button1.setOnAction(d -> popupwindow.close());

VBox layout= new VBox(10);
           
layout.getChildren().addAll(l1, b1, l2, b2, l3, b3, l4, b4, l5, b5, submit, l6, button1);
      
layout.setAlignment(Pos.CENTER);
      
Scene scene1= new Scene(layout, 500, 450);
      
popupwindow.setScene(scene1);
      
popupwindow.showAndWait();
       
}

public static void createfield() {
            board.getChildren().clear();
            int size = boardsize;
            Level level = new Level(size, size, gamebombs, gametime, globalsuperbomb, dir); 
            mylevel=level;
            mygame=mylevel.getGame();
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    Button square = new Button();
                    Box box = mygame.getBox(row,col);
                    square.setPrefSize(40, 40);
                    square.setOnMousePressed(mouseEvent -> {
                        // code to handle a click on this square goes here
                        if (mouseEvent.isPrimaryButtonDown() && playing){
                            reveal(box, square);
                            
                            
                        }else if (mouseEvent.isSecondaryButtonDown() && playing){
                            flaghandler(box);
                            updateboard();
                        }
                        
                        if(!playing) {
                        }
                    });
                    board.add(square, col, row);
                }
            }
}

public static void reveal(Box box, Button square){
        if (!box.isFlaged() && !box.isRevealed()){
            box.reveal();
            if (box.isBomb() && !box.isSuperBomb()) {
                
                defeat=true;
                playing=false;
                win=false;
                square.setStyle("-fx-background-color: red;");
                updateboard();
                revealboard();
                updateboard();
                updaterounds();
                countdown=-1;
                countdownLabel.setText("You lost :(");


            }
            if(box.isSuperBomb()) {
                if(moves<=4) {
                    moves++;
                    square.setStyle("-fx-background-color: blue;");
                    revealrow(box.getRow());
                    revealcolumn(box.getColumn());
                    updateboard();
                }
                if(moves>4) {
                    defeat=true;
                    playing=false;
                    win=false;
                    square.setStyle("-fx-background-color: red;");
                    updateboard();
                    revealboard();
                    updateboard();
                    updaterounds();
                    countdown=-1;
                    countdownLabel.setText("You lost :(");
                }
            }
            if(!box.isBomb() && !box.isSuperBomb()) { 
                moves++;
                ArrayList<Box> revealedBoxes = new ArrayList<>();
                revealedBoxes.add(box);
                revealZeroes(box, revealedBoxes);
                updateboard();
                if(checkwin()){
                    defeat=false;
                    playing=false;
                    win=true;
                    countdown=-2;
                    countdownLabel.setText("You won :)");
                    updaterounds();
                    revealboard();
                    updateboard();
                }
            }
            
        }

}

public static void revealcolumn(int c) {
    for(int i=0; i<boardsize; i++) {
        if(mygame.getBox(i, c).isFlaged()) {
            flaghandler(mygame.getBox(i, c));
            mygame.getBox(i, c).reveal();
        }
        if(!mygame.getBox(i, c).isFlaged()) {
            mygame.getBox(i, c).reveal();
        } 
    }
}

public static void revealrow(int r) {
    for(int i=0; i<boardsize; i++) {
        if(mygame.getBox(r, i).isFlaged()) {
            flaghandler(mygame.getBox(r, i));
            mygame.getBox(r, i).reveal();
        }
        if(!mygame.getBox(r, i).isFlaged()) {
            mygame.getBox(r, i).reveal();
        } 
    }
}

public static void revealZeroes(Box box, ArrayList<Box> revealed) {
    int[][] indexes = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    for (int[] pair : indexes) {
        if (0<=box.getRow()+pair[0] && box.getRow()+pair[0]<=boardsize-1 && 0<=box.getColumn()+pair[1] && box.getColumn()+pair[1]<=boardsize - 1) {
            Box helpbox = mygame.getBox(box.getRow()+pair[0], box.getColumn()+pair[1]);
            if (!revealed.contains(helpbox) && helpbox.isZero()){
                helpbox.reveal();
                revealed.add(helpbox);
                revealZeroes(helpbox, revealed);
            }
        }
    }
    for (Box r : revealed) {
        if (!r.equals(box)) {
            for (int[] pair : indexes) {
                if (0<=r.getRow()+pair[0] && r.getRow()+pair[0]<=boardsize-1 && 0<=r.getColumn()+pair[1] && r.getColumn()+pair[1]<=boardsize - 1){
                    Box helpbox = mygame.getBox(r.getRow()+pair[0], r.getColumn()+pair[1]);
                    if (!helpbox.isBomb() && !helpbox.isSuperBomb()){
                        helpbox.reveal();
                    }
                }
            }
        }
    }
} 

public static void flaghandler(Box box) {
    if (box.isFlaged()) {
        box.unflag();
        flaged--;
        flagedLabel.setText("Bombs flaged: " + String.valueOf(flaged));
    }else if (!box.isFlaged()){
        if (flaged < gamebombs && !box.isRevealed()) {
            box.flag();
            flaged++;
            flagedLabel.setText("Bombs flaged: " + String.valueOf(flaged));
        } 
    }
}

public static void updateboard(){
    Image flag = new Image("flag.png");
    for (int row = 0; row < boardsize; row++) {
        for (int column = 0; column < boardsize; column++) {
            Box box = mygame.getBox(row, column);
            Button button = (Button) board.getChildren().get(column+(row*boardsize));
            if (box.isRevealed()) {
                if (box instanceof BlankBox) {
                    if(mygame.NeighbourBombs(row, column)!=0) button.setText(Integer.toString(mygame.NeighbourBombs(row, column)));
                    button.setStyle("-fx-background-color: grey;");
                }
                if(box.isBomb() || box.isSuperBomb()) {
                    Font font = Font.font("Courier New", FontWeight.BOLD, 18);
                    button.setFont(font);
                    button.setText("X");
                }
            }
            if (box.isFlaged()) {
                button.setGraphic(new ImageView(flag));
            }else{
                button.setGraphic(null);
            }

        }
    }

}

public static void revealboard() {
    mygame.revealarray();
}

public static void timer() {

    Thread thread = new Thread(() -> {
            countdown = gametime;
        while (countdown > 0 ) {
            final int count = countdown;
            javafx.application.Platform.runLater(() -> {
                    countdownLabel.setText("Remaining seconds: " + Integer.toString(count));
                    endtime=countdown;
                
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countdown--;
            endtime=countdown;
        }
        Platform.runLater(() -> {
            if(countdown<=-1) {}
            if(countdown>-1) {
                revealboard();
                updateboard();
                countdownLabel.setText("You ran out of time!");
                playing=false;
                defeat=true;
                win=false;
                updaterounds();
            }
        });
    });
    if(!playing) thread.start();
    }


public static void revealbombs() {
    for (int row = 0; row < boardsize; row++) {
        for (int column = 0; column < boardsize; column++) {
            Box box = mygame.getBox(row, column);
            if(box.isBomb() || box.isSuperBomb()) {
                box.reveal();
            }
        }
    }
}

public static void updaterounds() {
    rounds++;
    if(win) currentround=new FinishedGame(gamebombs, moves, gametime, "user");
    if(defeat) currentround=new FinishedGame(gamebombs, moves, gametime, "computer");
    if(rounds==1) {
        round1=currentround;
    }
    if(rounds==2) {
        round2=round1;
        round1=currentround;
    }
    if(rounds==3) {
        round3=round2;
        round2=round1;
        round1=currentround;
    }
    if(rounds==4) {
        round4=round3;
        round3=round2;
        round2=round1;
        round1=currentround;
    }
    if(rounds>=5) {
        round5=round4;
        round4=round3;
        round3=round2;
        round2=round1;
        round1=currentround;
    }
    loaded=false;
    startLabel.setText("You have to load a description in order to play.");
}

public static boolean checkwin() {
    boolean help=true;
    for (int row = 0; row < boardsize; row++) {
        for (int column = 0; column < boardsize; column++) {
            Box box = mygame.getBox(row, column);
            if(!box.isBomb() && !box.isSuperBomb()) {
                if(!box.isRevealed()) help=false;
            }
        }
    }
    return help;
}

public static void loadvalues(int level, int bombs, int time, int superbomb) throws InvalidValueException {

    if((level==1 && bombs>=9 && bombs<=11 && time>=120 && time<=180 && superbomb==0) ||
     (level==2 && bombs>=35 && bombs<=45 && time>=240 && time<=360 && (superbomb==0 || superbomb==1)) ) {
        if(level==1) {
            boardsize=9;
            gamebombs=bombs;
            globalsuperbomb=false;
            gametime=time;
          }
          if(level==2 && superbomb==0) {
            boardsize=16;
            gamebombs=bombs;
            globalsuperbomb=false;
            gametime=time;
          }
          if(level==2 && superbomb==1) {
            boardsize=16;
            gamebombs=bombs;
            globalsuperbomb=true;
            gametime=time;
          }
    }
    else {
        throw new InvalidValueException("Invalid values!");
    }
}

public static long countLine(String fileName, Label l3) {
        long lines=0;
        try {
        Path path = Paths.get(dir+"\\medialab\\"+fileName);
            lines = Files.lines(path).count();
        } catch (IOException e) {
            l3.setText("File not found.");
        }
    return lines;
}

public static void readfile(String fileName, Label l3) throws InvalidDescriptionException {
        try{
            
            if(countLine(fileName, l3)==4) {
                File myObj = new File(dir+"\\medialab\\"+fileName);
                Scanner myReader = new Scanner(myObj);
                        templevel = Integer.parseInt(myReader.nextLine());
                        tempbombs = Integer.parseInt(myReader.nextLine());
                        temptime = Integer.parseInt(myReader.nextLine());
                        tempsuperbomb = Integer.parseInt(myReader.nextLine());
                        myReader.close();
            }
            else {
                throw new InvalidDescriptionException("Invalid Description, unexpected number of rows in the file!");
            }
        } catch (FileNotFoundException m) {
            l3.setText("File not found.");
            }
    
    

}

} 