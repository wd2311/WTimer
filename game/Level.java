package game;

import java.util.Random;

public class Level {

	Block[] blocks;
	Random r;
	int currBlockNum = 0;
	int amtOfBlocks = 0;
	
	public Level(int amtOfBlocks){
		this.amtOfBlocks = amtOfBlocks;
		r = new Random();
		blocks = new Block[this.amtOfBlocks];
		for(int i = 0; i < this.amtOfBlocks; i ++){
			blocks[i] = new Block(r.nextInt(100) + 25, r.nextInt(100) + 25, r.nextInt(800) + 400);
		}//
	}//Level
	
	public void incrementBlockNum(){
		currBlockNum += 1;
	}//incrementBlockNum
	
}//Level
