package twoWTimersWithSprites;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	
	WTimer timer1 = new WTimer();
	WTimer timer2 = new WTimer();
	
	int gridSceneWidth = 600;
	int gridSceneHeight = 600;
	
	int canvasWidth = 550;
	int canvasHeight = 440;
	
	int blockWidth1 = 80;
	int blockHeight1 = 100;
	int blockVelocity1 = 60;
	Block block1 = new Block(blockWidth1, blockHeight1, blockVelocity1);
	
	int blockWidth2 = 60;
	int blockHeight2 = 30;
	int blockVelocity2 = 25;
	Block block2 = new Block(blockWidth2, blockHeight2, blockVelocity2);
	
	GridPane grid;
	Scene gridScene;
	HBox buttonHolder1;
	Button[] buttons1; 
	HBox buttonHolder2;
	Button[] buttons2;
	Canvas canvas;
	GraphicsContext gc;
	AnimationTimer animator;
	
	public static void main(String[] args){
		Application.launch(args);
	}//main
	
	public void start(Stage mainStage){
		grid();
		scene();
		buttons1();
		buttons2();
		canvas();
		stage(mainStage);
	}//start
	
	private void grid(){
		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		//grid.setGridLinesVisible(true);
		//^^ This would show grid lines, useful for understanding and keeping track of gaps and insets.
	}//grid
	
	private void scene(){
		gridScene = new Scene(grid, gridSceneWidth, gridSceneHeight);
	}//scene
	
	private void buttons1(){
		buttonHolder1 = new HBox();
		buttonHolder1.setSpacing(10);
		
		buttons1 = new Button[3];
		for(int i = 0; i < 3; i ++){
			buttons1[i] = new Button();
			buttons1[i].setPrefSize(180, 45);
			buttons1[i].setFont(Font.font("Tahoma", 14));
			buttonHolder1.getChildren().add(buttons1[i]);
		}//for
		
		buttons1[0].setText("Start 1");
		buttons1[1].setText("Stop 1");
		buttons1[2].setText("Reset 1");
		
		buttons1[0].setOnAction(
		new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				block1.startTimer();
		    }//handle
		}//EventHandler class
		);
		buttons1[1].setOnAction(
		new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				block1.stopTimer();
		    }//handle
		}//EventHandler class
		);
		buttons1[2].setOnAction(
		new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
		        block1.resetTimer();
		    }//handle
		}//EventHandler class
		);
		
		grid.add(buttonHolder1, 0, 0);
	}//buttons1
	
	private void buttons2(){
		buttonHolder2 = new HBox();
		buttonHolder2.setSpacing(10);
		
		buttons2 = new Button[3];
		for(int i = 0; i < 3; i ++){
			buttons2[i] = new Button();
			buttons2[i].setPrefSize(180, 45);
			buttons2[i].setFont(Font.font("Tahoma", 14));
			buttonHolder2.getChildren().add(buttons2[i]);
		}//for
		
		buttons2[0].setText("Start 2");
		buttons2[1].setText("Stop 2");
		buttons2[2].setText("Reset 2");
		
		buttons2[0].setOnAction(
		new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				block2.startTimer();
		    }//handle
		}//EventHandler class
		);
		buttons2[1].setOnAction(
		new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				block2.stopTimer();
		    }//handle
		}//EventHandler class
		);
		buttons2[2].setOnAction(
		new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
		        block2.resetTimer();
		    }//handle
		}//EventHandler class
		);
		
		grid.add(buttonHolder2, 0, 1);
	}//buttons2
	
	private void canvas(){
		canvas = new Canvas(canvasWidth, canvasHeight);
		gc = canvas.getGraphicsContext2D();
		gc.setFont(Font.font("Tahoma", 15));
        gc.setFill(Color.AQUAMARINE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        
        animator = new AnimationTimer() {
        	public void handle(long now) {
	        	gc.setFill(Color.AQUAMARINE);
		        gc.fillRect(0, 0, canvasWidth, canvasHeight);//resets background drawing
		        //^^ Reset The Background 
		        
		        gc.setFill(Color.BLACK);
		        gc.fillText("Timer1 Reading (Seconds): " + block1.getBlocksTime()/1000, 30, 30);
		        //^^ Display the Timer Reading
		        
		        gc.setFill(Color.RED);
		        gc.fillRect(block1.velocity * block1.getBlocksTime()/1000, 100, block1.width, block1.height);
		        //^^ Position (pixels) = Velocity (pixels per second) * Time (seconds)
		        
		        gc.setFill(Color.BLACK);
		        gc.fillText("Timer2 Reading (Seconds): " + block2.getBlocksTime()/1000, 300, 30);
		        //^^ Display the Timer Reading
		        
		        gc.setFill(Color.BLUE);
		        gc.fillRect(block2.velocity * block2.getBlocksTime()/1000, 250, block2.width, block2.height);
		        //^^ Position (pixels) = Velocity (pixels per second) * Time (seconds)
            }//handle
        };//AnimationTimer
        animator.start();
        
        grid.add(canvas, 0, 2);
	}//canvas
	
	private void stage(Stage stage){
		stage.setTitle("Timer Example");
		stage.setScene(gridScene);
		stage.show();
	}//stage
}//Main
