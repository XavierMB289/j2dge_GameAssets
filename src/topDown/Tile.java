package topDown;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Tile {
	
	protected ImageIcon image;
	protected int gridX, gridY;
	protected Rectangle rect;
	protected float alpha;
	protected int type;
	
	public static final int FLOOR = 1;
	public static final int WALL = 2;
	public static final int MOVEABLE = 3;
	
	public void setImage(ImageIcon i){
		image = i;
	}
	
	public Image getImage(){
		return image.getImage();
	}
	
	public void setGridXY(int gx, int gy){
		gridX = gx;
		gridY = gy;
	}
	
	public void resetRect(int x, int y){
		if(image != null){
			rect = new Rectangle(x, y, image.getIconWidth(), image.getIconHeight());
		}
	}
	
	public void setAlpha(float a){
		alpha = a;
	}
	
	public void setType(int t){
		type = t;
	}
	
	public void paint(Graphics2D g){
		if(alpha > 0 || type == MOVEABLE){
			AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			AlphaComposite orig = (AlphaComposite) g.getComposite();
			g.setComposite(alcom);
			g.drawImage(image.getImage(), rect.x, rect.y, null);
			g.setComposite(orig);
		}else{
			g.setColor(Color.black);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
	
	public boolean containsRect(Rectangle r){
		return rect.intersects(r);
	}
	
}
