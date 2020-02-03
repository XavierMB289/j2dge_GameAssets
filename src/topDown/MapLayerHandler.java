package topDown;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

import engine.Window;

public class MapLayerHandler implements Serializable{
	
	private static final long serialVersionUID = 4613297766781979185L;
	
	Window w;
	TileGrid layer;
	
	int size = 16;
	
	transient BufferedImage img;
	
	public MapLayerHandler(Window w){
		this.w = w;
		layer = new TileGrid(w);
	}
	
	public MapLayerHandler(Window w, int imgSize){
		size = imgSize;
		this.w = w;
		layer = new TileGrid(w);
	}
	
	public MapLayerHandler getMap(String filename){
		if(new File(getClass().getClassLoader().getResource(filename).getFile()).exists()){
			layer.newMap(filename);
			System.err.println("File "+filename+" not found at MapLayerHandler");
			return this;
		}
		return null;
	}
	
	public void paint(Graphics2D g){
		if(img == null){
			img = layer.createImage();
		}
		g.drawImage(img, -(img.getWidth()/2), -(img.getHeight()/2), null);
	}
	
	public void update(){
		Graphics2D g2d = img.createGraphics();
		
		int width = img.getWidth()/size;
		int height = img.getHeight()/size;
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				g2d.drawImage(layer.getTile(x, y).getImage(), x*size, y*size, null);
			}
		}
		
		g2d.dispose();
	}
}
