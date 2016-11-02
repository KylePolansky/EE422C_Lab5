/* CRITTERS GUI <Main.java>
 * EE422C Project 4b submission by
 * Replace <...> with your actual data.
 * <Brian Madina>
 * <bjm3348>
 * <16460>
 * <Kyle Polansky>
 * <KPP446>
 * <16480>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment5;
import assignment5.Critter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
 
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
    private Button btnquit, btnshow, btnstep, btnseed, btnmake, btnstats, btnanimation, btnFpsUp, btnFpsDown;
    private TextField stepsInput, makeInput, makeInput2, statsInput, seedInput, fpsInput;
    private Label stepsLabel, makeLabel, makeLabel2, statsLabel, seedLabel, fpsLabel;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Critters2");
        btnquit = new Button("quit");
        btnshow = new Button("show");
        btnstep = new Button("step");
        btnseed = new Button("seed");
        btnmake = new Button("make");
        btnstats = new Button("stats");
        stepsInput = new TextField();
        makeInput = new TextField();
        statsInput = new TextField();
        seedInput = new TextField();
        makeInput2 = new TextField();
        btnanimation = new Button("Animate");
        fpsInput = new TextField();
        btnFpsDown = new Button("-");
        btnFpsUp = new Button("+");
        stepsLabel = new Label("Number of Time Steps");
        makeLabel = new Label("Critter Name");
        makeLabel2 = new Label("Number of Critters");
        statsLabel = new Label("Critter Name");
        seedLabel = new Label("Seed Number");
        fpsLabel = new Label("Time Steps per Frame");
               
      
        GridPane root = new GridPane();
        root.setHgap(20);
        root.setVgap(20);
        root.add(btnquit,0,0);
        
        root.add(btnshow,0,1);
        
        root.add(stepsLabel,1,2);
        root.add(btnstep,0,3);
        root.add(stepsInput, 1,3);
        
        root.add(seedLabel,1,4);
        root.add(btnseed,0,5);
        root.add(seedInput, 1,5);
        
        root.add(makeLabel,1,6);
        root.add(makeLabel2,2,6);
        root.add(btnmake,0,7);
        root.add(makeInput, 1, 7);
        root.add(makeInput2, 2, 7);
        
        root.add(statsLabel, 1, 8);
        root.add(btnstats,0,9);
        root.add(statsInput, 1, 9);
        
        root.add(fpsLabel, 1, 10);
        root.add(btnanimation,0,11);
        root.add(fpsInput, 1, 11);
        root.add(btnFpsUp, 3, 11);
        root.add(btnFpsDown, 2, 11);
        Scene scene = new Scene(root, 600, 600);
        buttonToCode();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void buttonToCode()
    {
        btnquit.setOnAction(e -> thecode(e));
        btnshow.setOnAction(e -> thecode(e));
        btnstep.setOnAction(e -> thecode(e));
        btnseed.setOnAction(e -> thecode(e));
        btnmake.setOnAction(e -> thecode(e));
        btnstats.setOnAction(e -> thecode(e));
    }
    
    public void thecode(ActionEvent e)
    {
       
        if(e.getSource()==btnquit)
        {
        	System.exit(0);
        }
        else if(e.getSource()==btnshow)
        {
        	Critter.displayWorld();
        }
        else if(e.getSource()==btnstep)
        {	
        	int numSteps;
        	if(stepsInput.getText().equals(""))
        		numSteps = 1;
        	else 
        		numSteps = Integer.parseInt(stepsInput.getText());
            for (int i = 0; i < numSteps; i++) {
                Critter.worldTimeStep();
            }
        }
        else if(e.getSource()==btnseed)
        {
        	int seed;
        	if(seedInput.getText().equals(""))
        		seed = 0;
        	else
        		seed = Integer.parseInt(seedInput.getText());
            Critter.setSeed(seed);
            
        }
        else if(e.getSource()==btnmake)
        {	
        	int numCritters;
        	if(makeInput2.getText().equals(""))
        		numCritters = 1;
        	else
        		numCritters = Integer.parseInt(makeInput2.getText());
        	try{
            for (int i = 0; i < numCritters; i++) {
                Critter.makeCritter(myPackage + "." + makeInput.getText()); //prepend the package name
            }
        	}catch(Exception e1)
        	{
        		System.out.print("You fucked up");
        	}
            
        }
        else if(e.getSource() == btnstats)
        {
        	
        }
        else if(e.getSource() == btnstats)
        {
        	
        }
       
    }

}
