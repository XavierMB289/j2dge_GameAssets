package topDown;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import engine.Window;

public abstract class Player{
	
	Window w;
	
	protected ImageIcon[] idle;
	
	protected ImageIcon[] run;
	
	protected ImageIcon weapon;
	
	protected int sprite = 0;
	protected String state = "idle";
	protected int timer = 5;
	int maxTimer = 5;
	
	protected boolean facingLeft = false;
	
	public static String IDLE = "idle";
	public static String MOVING = "move";
	
	public Rectangle bounds;
	
	public Player(Window w){
		this.w = w;
	}
	
	public void setupSheets(ImageIcon idleSheet, ImageIcon runSheet, ImageIcon weapon, int spriteSize, int scale){
		idle = w.ImageH.getSprites(w, w.ImageH.resizeImage(w, idleSheet, scale), spriteSize*scale);
		run = w.ImageH.getSprites(w, w.ImageH.resizeImage(w, runSheet, scale), spriteSize*scale);
		this.weapon = w.ImageH.resizeImage(w, weapon, scale);
		bounds = new Rectangle(w.HALF_W-(spriteSize*scale)/2, w.HALF_H-(spriteSize*scale)/2, spriteSize*scale, spriteSize*scale);
	}
	
	public void setState(String state){
		if(this.state.equals(state) != true){
			sprite = 0;
			this.state = state;
		}
	}
	
	public void paint(Graphics2D g){
		Image img = null;
		if(state.equals(IDLE)){
			if(facingLeft){
				img = w.ImageH.flip(w.ImageH.toBuffImage(idle[sprite]));
			}else{
				img = idle[sprite].getImage();
			}
			w.functions.drawCenteredImage(g, img, w.WINDOW_RECT, 0, 0);
			if(timer <= 0){
				timer = maxTimer;
				sprite = sprite + 1 < idle.length ? sprite + 1 : 0;
			}else{
				timer--;
			}
		}else if(state.equals(MOVING)){
			if(facingLeft){
				img = w.ImageH.flip(w.ImageH.toBuffImage(run[sprite]));
			}else{
				img = run[sprite].getImage();
			}
			w.functions.drawCenteredImage(g, img, w.WINDOW_RECT, 0, 0);
			if(timer <= 0){
				timer = maxTimer;
				sprite = sprite + 1 < run.length ? sprite + 1 : 0;
			}else{
				timer--;
			}
		}
	}
	
	public void setLeftFacing(boolean left){
		facingLeft = left;
	}
	
	public abstract void AttackAnimation(Graphics2D g);
	
}
