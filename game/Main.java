package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
 * Make it so if you fail, you get restarted to the beginning of the level.
 * Add buttons for new level.
 * Maybe give blocks a random color
 * need certain amount of blocks to beat level
 * add a timer for the "JUMP FAILED", make it show for a little.
 * make move in x direction
 * blocks forever running, score resets if you miss one, high score changes
 */

public class Main extends Application{
	
	int gridSceneWidth = 1280;
	int gridSceneHeight = 720;
	
	int canvasWidth = 1230;
	int canvasHeight = 520;
	
	Image sun = new Image(getClass().getResourceAsStream("Sun"));
	Image clouds = new Image(getClass().getResourceAsStream("Clouds"));
	
	Player person = new Player();
	
	double jumpPower = 1000;
	double timeForJump = 1000;
	
	GridPane grid;
	Scene gridScene;
	Label titleLabel;
	HBox hbox;
	Button[] buttons;
	Canvas canvas;
	GraphicsContext gc;
	AnimationTimer animator;
	
	Level[] levels = new Level[10];
	int currLevel = -1;
	//el problema no es que ellos no venden tallas largos, es que ellos solo ve
	//talking to icy guy in spanish, feel like going somewhere...?
	boolean inLevel = false;
	
	int score = 0;
	boolean toAwardPoint = true;
	
	
	public static void main(String[] args){
		Application.launch(args);
	}//main

	public void start(Stage mainStage){
		for(int i = 0; i < levels.length; i ++){
			levels[i] = new Level(5);
		}
		person.setY(canvasHeight-75);
		person.setX(50);
		grid();
		scene();
		title();
		buttons();
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
		
		gridScene.addEventHandler(KeyEvent.KEY_PRESSED, 
		new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event){
				if(event.getCode() == KeyCode.UP){
					if(person.getInAirTimerTime() == 0){
						person.switchOnGround();
						person.setYPosPreJump(person.yPos);
						person.setInAirTimerTarget(timeForJump);
						person.startInAirTimer();
					}//if he's not already jumping
				}//ifSpace
				if((event.getCode() == KeyCode.C) || (event.getCode() == KeyCode.SPACE)){
					if(!inLevel){
						if(currLevel < levels.length - 1){
							currLevel += 1;
							inLevel = true;
						}
					}
				}
				if(person.onGround){
					if(event.getCode() == KeyCode.DIGIT1){
						jumpPower = 1000;
					}
					if(event.getCode() == KeyCode.DIGIT2){
						jumpPower = 1200;
					}
					if(event.getCode() == KeyCode.DIGIT3){
						jumpPower = 1400;
					}
					if(event.getCode() == KeyCode.DIGIT4){
						jumpPower = 1600;
					}
					if(event.getCode() == KeyCode.DIGIT5){
						jumpPower = 1800;
					}
				}
				
			};
		});
	}//scene
	
	private void title(){
		//titleLabel = new Label("Jumpy Man!");
		titleLabel = new Label("Jonny Sucks!!");
		titleLabel.setFont(Font.font("Tahoma", 20));
		grid.add(titleLabel, 0, 0);
	}//title
	
	private void buttons(){
		hbox = new HBox();
		hbox.setSpacing(10);
		
		buttons = new Button[4];
		for(int i = 0; i < 4; i ++){
			buttons[i] = new Button();
			buttons[i].setPrefSize(200, 50);
			buttons[i].setFont(Font.font("Tahoma", 14));
			buttons[i].setFocusTraversable(false);
			hbox.getChildren().add(buttons[i]);
		}
		buttons[0].setText("Play Next Level.");
		buttons[1].setText("Number Two :^)");
		buttons[2].setText("Three Here! :o");
		buttons[3].setText("Male or Female");
		
		buttons[0].setOnAction(
		new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(!inLevel){
					if(currLevel < levels.length - 1){
						currLevel += 1;
						inLevel = true;
					}
				}
		    }//handle
		}//EventHandler class
		);
		
		grid.add(hbox, 0, 1);
	}//buttons
	
	private void canvas(){
		canvas = new Canvas(canvasWidth, canvasHeight);
		gc = canvas.getGraphicsContext2D();
		gc.setFont(Font.font("Tahoma", 15));
		
        animator = new AnimationTimer() {
        	public void handle(long now) {
		        drawBackground();
		        drawPerson();
		        checkJumping();
		        if(inLevel){
		        	levels[currLevel].blocks[levels[currLevel].currBlockNum].startTimer();  
		        	calculateAndDrawCurrentBlock();
		        	checkForCollision();
		        	checkIfNeedNewBlock();
		        	checkForLevelOver();
		        	//showMeWhatsComing();
		        }
            }//handle
        };//AnimationTimer
        animator.start();
        canvas.focusTraversableProperty();
        canvas.focusedProperty();
        canvas.requestFocus();
        canvas.setFocusTraversable(true);
        grid.add(canvas, 0, 2);
	}//canvas
	
	private void drawBackground(){
		gc.setFill(Color.AQUAMARINE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        gc.setFill(Color.GREEN);
        gc.fillRect(0, canvasHeight-75, canvasWidth, 75);
        gc.drawImage(sun, canvasWidth-100, 0);
        gc.drawImage(clouds, 0, 0);
        gc.drawImage(clouds, 400, 40);
        gc.drawImage(clouds, 700, 20);
        gc.drawImage(clouds, 175, 125);
        gc.drawImage(clouds, 950, 125);
        gc.strokeLine(0, canvasHeight-75, canvasWidth, canvasHeight-75);
        gc.setFill(Color.MISTYROSE);
        gc.fillText("'Up Arrow' to Jump.", 20, canvasHeight - 40);
        gc.fillText("'C' for Next Level.", 20, canvasHeight - 20);
        gc.fillText("'Number Keys 1-5' to Change Jump Power.", 170, canvasHeight - 40);
        gc.setFill(Color.FIREBRICK);
        if(inLevel){
        	gc.fillText("Level: " + (currLevel + 1), 275, 50);
            gc.fillText("Block Number: " + (levels[currLevel].currBlockNum + 1) + " / " + levels[currLevel].amtOfBlocks, 275, 75);
        }else{
        	gc.fillText("Next Level: " + (currLevel + 2), 275, 50);
        }
        gc.fillText("Score: " + score, canvasWidth-200, 50);
	}
	
	private void drawPerson(){
		if(person.onGround){
        	gc.drawImage(person.standing, person.xPos, person.yPos - person.height);
        }else{
        	gc.drawImage(person.jumping, person.xPos, person.yPos - person.height);
        }
	}
	
	private void checkJumping(){
		if(person.getInAirTimerTime() > 0){ 
        	if(person.getInAirTimerTime() >= person.getInAirTimerTarget()){
        		person.setY((person.yPosPreJump - jumpPower*(-Math.pow(person.getInAirTimerTarget()/1000, 2) + person.getInAirTimerTarget()/1000*person.getInAirTimerTarget()/1000)));
        		person.resetInAirTimer();
        		person.switchOnGround();
        		person.setYPosPreJump(0);
        	}else{
        		person.setY((person.yPosPreJump - jumpPower*(-Math.pow(person.getInAirTimerTime()/1000, 2) + person.getInAirTimerTarget()/1000*person.getInAirTimerTime()/1000)));
        	}
        }
	}
	
	private void calculateAndDrawCurrentBlock(){
		gc.setFill(Color.RED);
    	levels[currLevel].blocks[levels[currLevel].currBlockNum].setX(-levels[currLevel].blocks[levels[currLevel].currBlockNum].velocity * (levels[currLevel].blocks[levels[currLevel].currBlockNum].getBlocksTime()/1000) + canvasWidth - levels[currLevel].blocks[levels[currLevel].currBlockNum].width);
    	gc.fillRect(levels[currLevel].blocks[levels[currLevel].currBlockNum].xPos, 
					canvasHeight-75 - levels[currLevel].blocks[levels[currLevel].currBlockNum].height, 
					levels[currLevel].blocks[levels[currLevel].currBlockNum].width, 
					levels[currLevel].blocks[levels[currLevel].currBlockNum].height);
	}
	
	private void checkIfNeedNewBlock(){
		if(levels[currLevel].blocks[levels[currLevel].currBlockNum].xPos < 0 - levels[currLevel].blocks[levels[currLevel].currBlockNum].width){
    		levels[currLevel].incrementBlockNum();
    		if(toAwardPoint){
    			score += 1;
    		}
    		toAwardPoint = true;
    	}
	}
	
	private void checkForCollision(){
		if(levels[currLevel].blocks[levels[currLevel].currBlockNum].xPos < person.width + person.xPos){
    		if(levels[currLevel].blocks[levels[currLevel].currBlockNum].xPos + levels[currLevel].blocks[levels[currLevel].currBlockNum].width > person.xPos){
    			//^^ x direction
    			if(person.yPos + person.height > canvasHeight-75 - levels[currLevel].blocks[levels[currLevel].currBlockNum].height){
    				gc.setFont(Font.font("Tahoma", 30));
    				gc.fillText("BLOCK JUMP FAILED.", 450, 300);
    				gc.setFont(Font.font("Tahoma", 15));
    				toAwardPoint = false;
    			}
    		}
    	}
	}
	
	private void checkForLevelOver(){
		if(levels[currLevel].currBlockNum == levels[currLevel].amtOfBlocks){
    		inLevel = false;
    	}
	}
	
	private void showMeWhatsComing(){
		for(int i = 0; i < levels[currLevel].amtOfBlocks; i ++){
    		gc.setFill(Color.RED);
    		gc.fillRect(i*125 + 20, 20, levels[currLevel].blocks[i].width, levels[currLevel].blocks[i].height);
    	}
	}
	
	private void stage(Stage stage){
		stage.setTitle("Jumpy Man");
		stage.setScene(gridScene);
		stage.show();
	}//stage
}//Main