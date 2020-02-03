package topDown;

import engine.Window;

public class MapHandler {
	
	Window w;
	
	public static final int STATUS_UP = 1;
	public static final int STATUS_DOWN = -1;
	
	MapLayerHandler under;
	MapLayerHandler current;
	MapLayerHandler over;
	
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
	
}
