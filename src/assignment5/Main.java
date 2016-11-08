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
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
    public static Canvas worldCanvas;
    public static TextArea textSpace;
    private Button btnquit, btnstep, btnseed, btnmake, btnstats, btnanimation, btnanimationD, btnFpsUp, btnFpsDown;
    private TextField stepsInput, makeInput, makeInput2, statsInput, seedInput, fpsInput;
    private Label stepsLabel, makeLabel, makeLabel2, statsLabel, seedLabel, fpsLabel;
    private boolean isAnimating = false;
    private Stage worldStage;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Critters2");
        btnquit = new Button("quit");
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
        btnanimationD = new Button("Stop Animate");
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
        root.add(btnanimationD,0,12);
        btnanimationD.setDisable(true);
        root.add(fpsInput, 1, 11);
        root.add(btnFpsUp, 3, 11);
        root.add(btnFpsDown, 2, 11);
        Scene scene = new Scene(root, 600, 600);
        buttonToCode();
		scene.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        //create the world map
        worldStage = new Stage();
        worldStage.setTitle("Critter World");
        worldCanvas = new Canvas(1000,1000);
        Group worldRoot = new Group();
        worldRoot.getChildren().add(worldCanvas);
        Scene s = new Scene(worldRoot, 1000, 1000, Color.BLACK);
        worldStage.setScene(s);
        Critter.displayWorld();
        worldStage.show();

        textSpace = new TextArea();
        textSpace.setDisable(true);
        PrintStream ps = new PrintStream((new CustomOutputStream()));
        System.setOut(ps);

        Stage textStage = new Stage();
        textStage.setTitle("Console");
        Group textRoot = new Group();
        textRoot.getChildren().add(textSpace);
        Scene s1 = new Scene(textRoot);
        textStage.setScene(s1);
        textStage.show();
    }

    public class CustomOutputStream extends OutputStream {

        @Override
        public void write(int b) {
            textSpace.appendText(String.valueOf((char)b));
        }
    }
    
    public void buttonToCode()
    {
        btnquit.setOnAction(e -> thecode(e));
        btnstep.setOnAction(e -> thecode(e));
        btnseed.setOnAction(e -> thecode(e));
        btnmake.setOnAction(e -> thecode(e));
        btnstats.setOnAction(e -> thecode(e));
        btnanimation.setOnAction(e -> thecode(e));
        btnanimationD.setOnAction(e -> thecode(e));
        btnFpsDown.setOnAction(e -> fpsDownButtonHandler(e));
        btnFpsUp.setOnAction(e -> fpsUpButtonHandler(e));

    }

    private void fpsDownButtonHandler(ActionEvent e) {
        int fps = 1;
        try {
            fps = Integer.parseInt(fpsInput.getText());
            fps--;
        }
        catch (Exception ex) {
        }
        if (fps < 0) {
            fps = 0;
        }
        fpsInput.setText(Integer.toString(fps));
    }

    private void fpsUpButtonHandler(ActionEvent e) {
        int fps = 1;
        try {
            fps = Integer.parseInt(fpsInput.getText());
            fps++;
        }
        catch (Exception ex) {
        }
        if (fps < 0) {
            fps = 0;
        }
        fpsInput.setText(Integer.toString(fps));
    }


    
    public void thecode(ActionEvent e)
    {
       
        if(e.getSource()==btnquit)
        {
        	System.exit(0);
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
        else if(e.getSource()==btnmake) {
            int numCritters;
            if (makeInput2.getText().equals(""))
                numCritters = 1;
            else
                numCritters = Integer.parseInt(makeInput2.getText());
            try {
                for (int i = 0; i < numCritters; i++) {
                    Critter.makeCritter(myPackage + "." + makeInput.getText()); //prepend the package name
                }
            } catch (Exception e1) {
                System.out.println("Invalid Critter Name");
            }
        }
        else if(e.getSource() == btnstats)
        {
            String classname = myPackage + "." + statsInput.getText();

            List<Critter> critters = null;
            try {
                critters = Critter.getInstances(classname);
                Class.forName(classname).getMethod("runStats", java.util.List.class).invoke(null,critters);
            } catch (Exception e1) {
                System.out.println("Invalid Critter Name");
            }
        }
        else if(e.getSource() == btnanimation)
        {
            disableButtons();
            isAnimating = true;
            animateExecutor = Executors.newScheduledThreadPool(1);
            animateExecutor.scheduleAtFixedRate(animateRunnable, 0, 1, TimeUnit.SECONDS);
            return;
        }
        else if(e.getSource() == btnanimationD)
        {
            isAnimating = false;
            animateExecutor.shutdown();
            enableButtons();
            return;
        }

        Critter.displayWorld();
    }

    private ScheduledExecutorService animateExecutor;

    private Runnable animateRunnable = new Runnable() {
        public void run() {
            int fps = 1;

            try {
                fps = Integer.parseInt(fpsInput.getText());
            } catch (Exception ex) {
                fpsInput.setText(Integer.toString(fps));
            }
            if (fps < 0) {
                fps = 0;
            }

            for (int i = 0; i < fps; i++) {
                Critter.worldTimeStep();
            }
            Critter.displayWorld();
        }
    };


    private void disableButtons() {
        btnseed.setDisable(true);
        btnmake.setDisable(true);
        btnstats.setDisable(true);
        btnstep.setDisable(true);
        btnanimation.setDisable(true);
        btnanimationD.setDisable(false);
    }

    private void enableButtons() {
        btnseed.setDisable(false);
        btnmake.setDisable(false);
        btnstats.setDisable(false);
        btnstep.setDisable(false);
        btnanimation.setDisable(false);
        btnanimationD.setDisable(true);
    }

}
