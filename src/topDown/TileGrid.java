package topDown;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import engine.Window;

public class TileGrid implements Serializable{
	
	private static final long serialVersionUID = 9090735084564502668L;
	
	Window w;
	Map<int[], Tile> grid;
	int width, height;
	int tileSize;
	
	public TileGrid(Window w){
		this.w = w;
	}
	
	public void newMap(String f){
		grid = new HashMap<>();
		ArrayList<String> lines = w.FileH.readFromFile(f);
		int wid = Integer.parseInt(lines.get(0).split("/")[0]);
		width = wid;
		int hei = Integer.parseInt(lines.get(0).split("/")[1]);
		height = hei;
		
		//Image Setup
		Map<Integer, String> images = new HashMap<>();
		for(int i = hei+1; i < lines.size(); i++){
			String[] splitStr = lines.get(i).split("=");
			images.put(Integer.parseInt(splitStr[0]), splitStr[0]);
		}
		tileSize = w.getImage(images.get(1)).getIconHeight();
		
		for(int y = 0; y < hei; y++){
			for(int x = 0; x < wid; x++){
				char current = lines.get(y+1).charAt(x);
				Tile t = new Tile();
				t.setImage(w.getImage(images.get(current)));
				t.setGridXY(x, y);
				grid.put(new int[]{x, y}, t);
			}
		}
	}
	
	public Tile getTile(int x, int y){
		return grid.get(new int[]{x, y});
	}
	
	BufferedImage createImage(){
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
}
