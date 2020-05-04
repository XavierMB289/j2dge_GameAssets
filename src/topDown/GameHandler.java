package topDown;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import backends.Vector2D;
import engine.Window;

public class GameHandler {
	
	Window w;
	
	public Player player;
	
	String[] levels;
	int currentLevel = 0;
	
	MapHandler mh;
	
	public GameHandler(Window w, String[] levels){
		this.w = w;
		this.levels = levels;
		newMap();
	}
	
	public void newMap(){
		mh = new MapHandler(w, levels[currentLevel]);
	}
	
	public void nextMap(){
		currentLevel++;
		newMap();
	}
	
	public void prevMap(){
		currentLevel--;
		newMap();
	}
	
	public int getScale(){
		return mh.getScale();
	}
	
	public boolean inBounds(Vector2D v){
		return mh.onFloor(v);
	}
	
	public void changeBounds(int x, int y){
		mh.changeBounds(x, y);
	}
	
	public void paintRects(Graphics2D g){
		if(mh.getRects()!= null){
			g.setStroke(new BasicStroke(3));
			g.setColor(Color.red);
			for(Rectangle r : mh.getRects()){
				g.drawRect(r.x, r.y, r.width, r.height);
			}
		}
	}
	
	public BufferedImage getFloor(){
		return mh.getFloor();
	}
	
	public BufferedImage getWalls(){
		return mh.getWalls();
	}
	
	public BufferedImage getFront(){
		return mh.getFront();
	}
	
}
