package topDown;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import engine.Window;

public class TileGrid {
	
	Window w;
	
	Tile[][] tilemap;
	
	Map<String, ImageIcon> ids;
	int[][] playable = null;
	
	private double scale;
	
	private transient BufferedImage map;
	
	private ArrayList<Rectangle> bounds = null;
	
	public TileGrid(Window w, int wid, int hei){
		this.w = w;
		tilemap = new Tile[hei][wid];
		ids = new HashMap<>();
	}
	
	public void setScale(int scale){
		this.scale = scale;
	}
	
	public void addIDs(Map<String, String> stringIDs){
		for(Map.Entry<String, String> entry : stringIDs.entrySet()){
			ids.put(entry.getKey(), w.getImage(entry.getValue()));
		}
	}
	
	public void setupMap(String[] map){
		if(ids.isEmpty()){
			return;
		}
		for(int y = 0; y < tilemap.length; y++){
			String[] temp = map[y].split("");
			for(int x = 0; x < tilemap[y].length; x++){
				if(temp[x].equals("0")){
					tilemap[y][x] = null;
				}else{
					tilemap[y][x] = new Tile(w.ImageH.resizeImage(w, ids.get(temp[x]), scale));
				}
			}
		}
		ids = null;
	}
	
	public void createMap(int tileSize){
		map = new BufferedImage((int)(tilemap[0].length*tileSize*scale), (int)(tilemap.length*tileSize*scale), BufferedImage.TYPE_INT_ARGB);
		Graphics g = map.createGraphics();
		
		for(int y = 0; y < tilemap.length; y++){
			for(int x = 0; x < tilemap[y].length; x++){
				if(tilemap[y][x] != null){
					tilemap[y][x].setCoords((int)((-tilemap[0].length/2 + x) * (tileSize*scale))+w.HALF_W, (int)((-tilemap.length/2 + y) * (tileSize*scale))+w.HALF_H);
					g.drawImage(tilemap[y][x].getImage(), (int)(x*tileSize*scale), (int)(y*tileSize*scale), null);
				}
			}
		}
		
		g.dispose();
		
		map = w.ImageH.toBuffImage(new ImageIcon(map));
	}
	
	public ArrayList<Rectangle> getBounds(){
		if(bounds == null){
			bounds = new ArrayList<>();
			for(int y = 0; y < tilemap.length; y++){
				for(int x = 0; x < tilemap[y].length; x++){
					if(tilemap[y][x] != null){
						bounds.add(tilemap[y][x].getRect());
					}
				}
			}
		}
		return bounds;
	}
	
	public ArrayList<Rectangle> changeRects(int x, int y){
		bounds = new ArrayList<>();
		for(int fy = 0; fy < tilemap.length; fy++){
			for(int fx = 0; fx < tilemap[fy].length; fx++){
				if(tilemap[fy][fx] != null){
					bounds.add(tilemap[fy][fx].changeRect(x, y));
				}
			}
		}
		return bounds;
	}
	
	public BufferedImage getMap(){
		return map;
	}
	
}
