package topDown;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import engine.Window;

public class MapHandler {
	
	Window w;
	
	public static final int STATUS_UP = 1;
	public static final int STATUS_DOWN = -1;
	
	MapLayerHandler under;
	MapLayerHandler current;
	MapLayerHandler over;
	
	private float transparency = 0f;
	private boolean headingDown = false;
	
	int currentLevel = 0;
	String baseFilename;
	boolean mapChanging = false;
	
	public MapHandler(Window w){
		this.w = w;
	}
	
	public MapHandler(Window w, String name){
		this.w = w;
		setFilename(name);
	}
	
	public void setFilename(String basicName){
		baseFilename = basicName;
	}
	
	public void setupMap(){
		setupMap(16);
	}
	
	public void setupMap(int imgSize){
		under = new MapLayerHandler(w, imgSize).getMap(baseFilename+"_"+(currentLevel-1));
		current = new MapLayerHandler(w, imgSize).getMap(baseFilename+"_"+currentLevel);
		over = new MapLayerHandler(w, imgSize).getMap(baseFilename+"_"+(currentLevel+1));
	}
	
	public void rotateMaps(int status){
		if(status == STATUS_UP){
			currentLevel++;
			under = current;
			current = over;
			over = over.getMap(baseFilename+"_"+(currentLevel+1));
		}else if(status == STATUS_DOWN){
			currentLevel--;
			over = current;
			current = under;
			under = under.getMap(baseFilename+"_"+(currentLevel-1));
		}
	}
	
	public void headingDown(boolean b){
		headingDown = b;
	}
	
	public void setTransparency(float t){
		transparency = t;
	}
	
	public void paint(Graphics2D g){
		
		//Just for sanity's sake
		float opacity = Math.min(Math.max(0, transparency), 1);
		
		if(!headingDown){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			under.paint(g);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-opacity));
		current.paint(g);
		if(headingDown){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			over.paint(g);
		}
	}
	
	public void update(){
		under.update();
		current.update();
		over.update();
	}
	
	//Methods for animations...
	public Tile getTile(int x, int y, String map){
		if(under.layer.getMapName().equals(map)){
			return under.layer.getTile(x, y);
		}
		if(current.layer.getMapName().equals(map)){
			return current.layer.getTile(x, y);
		}
		if(over.layer.getMapName().equals(map)){
			return over.layer.getTile(x, y);
		}
		return null;
	}
	
	public void setTileImage(String img, String map, int x, int y){
		if(under.layer.getMapName().equals(map)){
			under.layer.grid.get(new int[]{x,y}).image = w.ImageH.getImage(img);
			return;
		}
		if(current.layer.getMapName().equals(map)){
			current.layer.grid.get(new int[]{x,y}).image = w.ImageH.getImage(img);
			return;
		}
		if(over.layer.getMapName().equals(map)){
			over.layer.grid.get(new int[]{x,y}).image = w.ImageH.getImage(img);
			return;
		}
	}
	
}
