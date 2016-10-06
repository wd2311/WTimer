package game;

import javafx.scene.image.Image;

public class Player {

	double xPos;
	double yPos;
	double height = 60;
	double width = 25;;
	Image standing;
	Image jumping;
	boolean onGround;
	WTimer inAir = new WTimer();
	double yPosPreJump;
	
	public Player(){
		onGround = true;
		standing = new Image(getClass().getResourceAsStream("Standing"));
		jumping = new Image(getClass().getResourceAsStream("Jumping"));
	}//Player
	
	public double getInAirTimerTime(){
		return inAir.getTime();
	}//getAirTimerTime
	
	public void startInAirTimer(){
		inAir.start();
	}//startInAirTimer
	
	public void stopInAirTimer(){
		inAir.stop();
	}//stopInAirTimer
	
	public void resetInAirTimer(){
		inAir.reset();
	}//resetInAirTimer
	
	public void setInAirTimerTarget(double targ){
		inAir.setTarget(targ);
	}//setInAirTimerTarget
	
	public double getInAirTimerTarget(){
		return inAir.target;
	}
	
	public void switchOnGround(){
		onGround = !onGround;
	}//swichOnGround
	
	public void setX(double x){
		xPos = x;
	}//setX
	
	public void setY(double y){
		yPos = y;
	}//setY
	
	public void setYPosPreJump(double y){
		yPosPreJump = y;
	}
}//Player