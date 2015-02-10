package map;
//package map;


public class Biome {
	//@author JayBT
	
	public int worldSeed;
	public int biomeSeed;
	public int chunkNum; //if biomes end up being saved to save space this will become broken, avoid
	public int startChunkNum;
	public int biomeLength;
	public int maxBiomeLength = 16;
	public int minBiomeHeight = 3;
	public int maxBiomeHeight = 3; //calculated from tope of screen, i. e., 3 with a height of 40 means max height is 37
	public byte surfaceGen; //Varied Random, Rolling Hills, Short Hills, etc. Used to calculate the surface. 
	public byte environmentGen; //Grassland, Desert, Forest, etc. Also will be to calculate temperature and mob types.
	
	
	Biome(int gSeed, int gCurrentScreen, String[] args){
		worldSeed = gSeed;
		chunkNum = gCurrentScreen;
		
		//Uses the "sprinkle method" (patent pending) to find which biome this chunk is part of
		//generates a random number 1-10 based on the world seed and current screen per chunk;
		//thus, '0' is sprinkled throughout each chunk, at random locations. 
		//in addition, each chunk contains a mandatory sprinkle at chunk 0 and every 16 chunks
		//when it finds the sprinkle, it generates the biome seed
		//the biome seed is thus the same from one sprinkle all the way unto the next
		int mandatorySprinkle = worldSeed;
		mandatorySprinkle ^= (mandatorySprinkle << 21);
		mandatorySprinkle ^= (mandatorySprinkle >>> 35);
		mandatorySprinkle ^= (mandatorySprinkle << 4);
		mandatorySprinkle = Math.abs(mandatorySprinkle %16);
		for(int i = chunkNum; true; i--){
			//Is this chunk the one with the sprinkle?
			int sprinkleNum = worldSeed + i;
			sprinkleNum ^= (sprinkleNum << 21);
			sprinkleNum ^= (sprinkleNum >>> 35);
			sprinkleNum ^= (sprinkleNum << 4);
			sprinkleNum = Math.abs(sprinkleNum %8);
			//System.out.println("i = "+i+" sprinkleNum = "+sprinkleNum+" ms= "+mandatorySprinkle+" i+ms%16= "+((i+mandatorySprinkle) % maxBiomeLength));
			if(sprinkleNum == 0 || i == 0 || (i+mandatorySprinkle) % maxBiomeLength == 0){ //found the sprinkle!
				//generates biomeSeed
				//System.out.println("worldSeed = "+worldSeed+" sprinkle at chunknum "+i+" msprinkle = "+mandatorySprinkle+" sprinkle = "+sprinkleNum);
				biomeSeed = worldSeed + i;
				biomeSeed ^= (biomeSeed << 13);
				biomeSeed ^= (biomeSeed >>> 7);
				biomeSeed ^= (biomeSeed << 17);
				startChunkNum = i;
				break;
			}
			
		}
		//generates biome information
		environmentGen = (byte) biomeSeed;
		environmentGen ^= (environmentGen << 21);
		environmentGen ^= (environmentGen >>> 35);
		environmentGen ^= (environmentGen << 4);
		
		
	}

	public int genSurface(int x, int currentScreen, int prevSurface) {
		// TODO Auto-generated method stub
		int surface = (int) ((int) worldSeed * (currentScreen + 1) * Math.sqrt((worldSeed * (x + 1)) % 240)) % 100;
		// System.out.println("column " x " percentage of " surface " mapwidth " mapWidth);
		//System.out.println("x "+x+"surface "+surface);
		if (surface < 2) { // unlikely scenario where floor is raised two blocks
			surface = -2;
		} else if (surface < 20) { // floor is up 1 block
			surface = -1;
		} else if (surface < 80) { // floor is dirtRows
			surface = 0;
		} else if (surface < 98) { // floor is down 1
			surface = 1;
		} else { // (surface < 100) floor is down 2
			surface = 2;
		}
		return surface;
	}
	
}