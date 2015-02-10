package map;

import java.util.ArrayList;

import block.block;

public class Chunk extends  ArrayList<ArrayList<block[]>>{

	public ArrayList<ArrayList<block[]>> ChunkRL;
	
	//@override
	public Chunk(){
		super();
		ChunkRL = new ArrayList<ArrayList<block[]>>();
		ChunkRL.add(null);
	}
	
	//@override
	public ArrayList<block[]> get(int chunkNum){
		if(chunkNum < 0){
			return ChunkRL.get(-chunkNum);
		}else{
			return super.get(chunkNum);
		}
		
	}
	
	//@override
	public ArrayList<block[]> set(int arg0, ArrayList<block[]> screen){
		if(arg0 < 0){
			return ChunkRL.set(-arg0, screen);
		}else{
			return super.set(arg0, screen);
		}
	}

}