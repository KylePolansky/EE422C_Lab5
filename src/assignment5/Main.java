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
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    Button btnquit, btnshow, btnstep, btnseed, btnmake, btnstats;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Critters2");
        btnquit = new Button("quit");
        btnshow = new Button("show");
        btnstep = new Button("step");
        btnseed = new Button("seed");
        btnmake = new Button("make");
        btnstats = new Button("stats")
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
