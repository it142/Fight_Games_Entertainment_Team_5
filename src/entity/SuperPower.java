package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import angelonias.tilemap.TileMap;
import content.Powers;
import entity.Animation;
import entity.MapObject;

public class SuperPower extends MapObject {
	
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	
	public SuperPower(TileMap tm, boolean right, int i) {
		super(tm);
		
		moveSpeed = 6;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		width = 50;
		height = 50;
		
		cwidth = 50;
		cheight = 50;
		
		sprites = new BufferedImage[1];
		sprites[0] = Powers.get(i);
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(70);
	}
	
	public void setHit() {
		if(hit) return;
		hit = true;
		dx = 0;
	}
	
	public boolean shouldRemove() { return remove;}
	
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		animation.update();
		
		if(hit) {
			remove = true;
		}
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		
		if(facingRight) {
			g.drawImage(animation.getImage(), 
					(int)(x  - width / 2 - cwidth / 2), 
					(int)(y + ymap - height / 2),
					null);
		}
		else {
			g.drawImage(animation.getImage(), 
					(int)(x + width / 2 - cwidth / 2), 
					(int)(y - height / 2),
					-width,
					height,
					null);
		}
	}

}
