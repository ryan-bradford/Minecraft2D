package save;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import player.player;
import main.main;
import block.block;
import thread.task;

public class saveTask extends task {
	PrintWriter out;

	@Override
	public void runTask() {
		try {
			FileWriter fr = new FileWriter(main.fileName);
			BufferedWriter br = new BufferedWriter(fr);
			out = new PrintWriter(br);
			out.write("Head");
			out.println(" ");
			out.write(" " + main.map.currentScreen);
			out.println(" ");
			savePlayer(main.getPlayer());
			for (int i = 0; i < main.map.chunk.size(); i++) { //ltr
				saveChunk(main.map.chunk.get(i), i, true);
			}
			for (int i = 0; i < main.map.chunk.ChunkRL.size(); i++) { //rtl
				saveChunk(main.map.chunk.get(-i), i, false);
			}
			saveInventory();
			saveInventoryBar();
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Boolean returnRunnable() {
		return true;
	}

	@Override
	public int getWait() {
		return 1000;
	}

	@Override
	public int[] getData() {
		return new int[] { 0 };
	}

	@Override
	public int getCPULoad() {
		return 0; // 0 is no load, 3 is maximum load
	}

	public void savePlayer(player play) {
		out.write("Player");
		out.println(" ");
		out.write(Integer.toString(play.getBounds().x));
		out.println(" ");
		out.write(Integer.toString(play.getBounds().y));
		out.println(" ");

	}

	public void saveChunk(ArrayList<block[]> chunk, int chunkNum, boolean leftToRight) {
		if(leftToRight){
			out.write("Chunk");
		}else{
			out.write("-Chunk");
		}
		out.println(" ");
		for (int i = 0; i < chunk.size(); i++) {
			for (int x = 0; x < chunk.get(i).length; x++) {
				try {
				out.write(Integer.toString(chunk.get(i)[x].getBounds().x));
				out.println(" ");
				out.write(Integer.toString(chunk.get(i)[x].getBounds().y));
				out.println(" ");
				out.write(Integer.toString(chunk.get(i)[x].id));
				out.println(" ");
				out.write(Integer.toString(chunk.get(i)[x].health));
				out.println(" ");
				} catch(NullPointerException ex) {
					
				}
			}
		}
		out.write("End");
		out.println(" ");	
	}
	
	public void saveInventory() {
		out.write("Inventory");
		out.println(" ");
		for(int i = 0; i < main.map.inventory.inventoryButtons.length; i++) {
			for(int x = 0; x < main.map.inventory.inventoryButtons[i].length; x++) {
				out.write(Integer.toString(main.map.inventory.inventoryButtons[i][x].amount));
				out.println(" ");
				out.write(Integer.toString(main.map.inventory.inventoryButtons[i][x].blockID));
				out.println(" ");
			}
		}
		out.write("End");
		out.println(" ");
	}
	
	public void saveInventoryBar() {
		out.write("Inventory Bar");
		out.println(" ");
		for(int i = 0; i < main.map.inventoryBar.inventoryBarButtons.length; i++) {
				out.write(Integer.toString(main.map.inventoryBar.inventoryBarButtons[i].amount));
				out.println(" ");
				out.write(Integer.toString(main.map.inventoryBar.inventoryBarButtons[i].blockID));
				out.println(" ");
		}
		out.write("End");
		out.println(" ");
	}
}
