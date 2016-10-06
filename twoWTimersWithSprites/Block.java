package twoWTimersWithSprites;

public class Block {

	WTimer blockTimer = new WTimer();
	int width;
	int height;
	int velocity;
	
	public Block(int w, int h, int vel){
		width = w;
		height = h;
		velocity = vel;
	}//Block
	
	public double getBlocksTime(){
		return blockTimer.getTime();
	}
	
	public void startTimer(){
		blockTimer.start();
	}//startTimer
	
	public void stopTimer(){
		blockTimer.stop();
	}//startTimer
	
	public void resetTimer(){
		blockTimer.reset();
	}
	
}//Block

