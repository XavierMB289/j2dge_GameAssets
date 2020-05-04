package topDown;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backends.Vector2D;
import engine.Window;

public class MapHandler {
	
	Window w;
	
	TileGrid floor;
	TileGrid walls;
	TileGrid front;
	
	int[] mapSize;
	
	private int scale;
	
	private ArrayList<Rectangle> rects = null;
	
	public MapHandler(Window w, String filepath){
		
		this.w = w;
		
		floor = createGrid(filepath+"_floor.txt");
		walls = createGrid(filepath+"_wall.txt");
		front = createGrid(filepath+"_frontWall.txt");
		
	}
	
	public TileGrid createGrid(String filepath){
		
		TileGrid ret;
		
		ArrayList<String> file = w.FileH.readArrayFromFile(filepath);
		String[] mS = file.get(0).split("/");
		mapSize = new int[]{Integer.parseInt(mS[0]), Integer.parseInt(mS[1])};
		ret = new TileGrid(w, mapSize[0], mapSize[1]);
		
		String[] map = new String[mapSize[1]];
		for(int i = 2; i < mapSize[1]+2; i++){
			map[i-2] = file.get(i);
		}
		
		Map<String, String> ids = new HashMap<>();
		for(int i = mapSize[1]+2; i < file.size(); i++){
			String[] id = file.get(i).split("=");
			ids.put(id[0], id[1]);
		}
		
		String[] temp = file.get(1).split("/");
		scale = Integer.parseInt(temp[1]);
		ret.setScale(scale);
		
		ret.addIDs(ids);
		ret.setupMap(map);
		
		ret.createMap(Integer.parseInt(temp[0]));
		
		return ret;
	}
	
	public boolean onFloor(Vector2D v){
		if(rects == null){
			rects = floor.getBounds();
		}
		
		Point p = new Point((int)v.x, (int)v.y);
		
		for(Rectangle r : rects){
			if(r.contains(p)){
				return true;
			}
		}
		return false;
	}
	
	public void changeBounds(int x, int y){
		rects = floor.changeRects(x, y);
	}
	
	public ArrayList<Rectangle> getRects(){
		return rects;
	}
	
	public int getScale(){
		return scale;
	}
	
	public BufferedImage getFloor(){
		return floor.getMap();
	}
	
	public BufferedImage getWalls(){
		return walls.getMap();
	}
	
	public BufferedImage getFront(){
		return front.getMap();
	}
	
}
