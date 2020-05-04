package topDown;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import backends.Vector2D;

public class Tile {
	
	protected ImageIcon image;
	
	protected Vector2D coords;
	
	protected Rectangle rect = null;
	
	public Tile(ImageIcon i){
		image = i;
		coords = new Vector2D();
	}
	
	public Tile setImage(ImageIcon i){
		image = i;
		return this;
	}
	
	public Image getImage(){
		return image.getImage();
	}
	
	public void setCoords(int x, int y){
		coords.set(x, y);
	}
	
	public Rectangle changeRect(int x, int y){
		rect.setLocation((int)coords.x+x, (int)coords.y+y);
		return getRect();
	}
	
	public int width(){
		return image.getIconWidth();
	}
	
	public int height(){
		return image.getIconHeight();
	}
	
	public Rectangle getRect(){
		if(rect == null){
			rect = new Rectangle((int)coords.x, (int)coords.y-height()/2, width(), height());
		}
		return rect;
	}
	
}
