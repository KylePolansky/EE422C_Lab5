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
    private Button btnquit, btnshow, btnstep, btnseed, btnmake, btnstats;
    private TextField stepsInput;
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
               
      
        GridPane root = new GridPane();
        root.setHgap(20);
        root.setVgap(20);
        root.add(btnquit,0,0);
        root.add(btnshow,0,1);
        root.add(btnstep,0,2);
        root.add(stepsInput, 1,2);
        root.add(btnseed,0,3);
        root.add(btnmake,0,4);
        root.add(btnstats,0,5);
        Scene scene = new Scene(root, 500, 500);
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
        /*else if(e.getSource()==btnstep)
        {
        	int numSteps = Integer.parseInt(stepsInput.getText());;
            if ( == 2) {
                numSteps = Integer.parseInt(inputSplit[1]);
            }
            for (int i = 0; i < numSteps; i++) {
                Critter.worldTimeStep();
            }
        }*/
       
    }

}
