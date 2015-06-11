package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import angelonias.tilemap.TileMap;
import entity.SuperPower;

public class Player extends MapObject{
	
	//player stuff
	private int health;
	private int maxHealth;
	private int energy;
	private int maxEnergy;
	
	private boolean dead;
	private boolean gettingHit;
	private boolean blocking;
	private boolean winning;
	private boolean flinching;
	private long flinchTimer;
	
	// special power
	private boolean throwing;
	private int specialPowerDamage;
	private ArrayList<SuperPower> powers;
	
	// punch
	private boolean punching;
	private int punchDamage;
	private int punchRange;
	
	//kick
	private boolean kicking;
	private int kickDamage;
	private int kickRange;
	
	//animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			5, 6, 6, 2, 4, 4, 3, 2, 2, 2, 3, 3
	};
	
	//animation actions
	private static final int IDLE = 0;
	private static final int WALKINGFORWARDS = 1;
	private static final int WALKINGBACKWARDS = 2;
	private static final int CROUCHING = 3;
	private static final int PUNCHING = 4;
	private static final int KICKING = 5;
	private static final int THROWING = 6;
	private static final int GETTINGHIT = 7;
	private static final int BLOCKING = 8;
	private static final int WINNING = 9;
	private static final int JUMPING = 10;
	private static final int FALLING = 11;
	
	// only use for special power
	private int id;

	
	public Player(TileMap tm,String path, boolean facingRight, int id) {
		super(tm);
		this.facingRight = facingRight;
		this.id = id;
		
		width = 150;
		height = 240;
		cwidth = 90;
		cheight = 200;
		
		moveSpeed = 0.3;
		maxSpeed = 2.5;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -6;
		stopJumpSpeed = 2;
		
		facingRight = true;
		
		health = 25;
		maxHealth = 25;
		
		energy = 0;
		maxEnergy = 5;
		
		specialPowerDamage = 3;
		powers = new ArrayList<SuperPower>();
		
		punchDamage = 1;
		punchRange = 170;
		
		kickDamage =  2;
		kickRange = 180;
		
		//load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(path)	
				);
			
			sprites = new ArrayList<BufferedImage[]>();
			
			for(int i = 0; i < 10; i ++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {			
					if(i == 4 || i == 5 || i == 6) {
						bi[j] = spritesheet.getSubimage(j * 250, i * height, 250, height);
					}
					else {
						bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					}
				}
				sprites.add(bi);
			}
			for(int i = 0; i < 2; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i + 10]];
				for(int j = 0; j < numFrames[i + 10]; j++) {
					width = 150;
					bi[j] = spritesheet.getSubimage(j * width, 2400 + i * 260, width, 260);
				}
				sprites.add(bi);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(200);
	}
	
	public void checkAttack(Player p) {
		if(punching) {
			if(facingRight) {
				if(p.getX() >= x && p.getX() <= x + punchRange &&
						p.getY() >= y - height / 2 && p.getY() <= y + height / 2) {
					
							p.hit(punchDamage, this);
				}
			}
			else {
				if(p.getX() <= x && p.getX() >= x - punchRange &&
						p.getY() >= y - height / 2 && p.getY() <= y + height / 2) {

						p.hit(punchDamage, this);

				}
			}
		}
		else if(kicking) {
			if(p.isCrouching()) return;
			if(facingRight) {
				if(p.getX() >= x && p.getX() <= x + kickRange &&
						p.getY() >= y - height / 2 && p.getY() <= y + height / 2) {	

						p.hit(kickDamage, this);
				}
			}
			else {
				if(p.getX() <= x && p.getX() >= x - kickRange &&
						p.getY() >= y - height / 2 && p.getY() <= y + height / 2) {

						p.hit(kickDamage, this);
				}
			}
		}
		
		// powers
		for(int i = 0; i < powers.size(); i++) {
			if(powers.get(i).intersects(p)) {
				p.hit(specialPowerDamage, this);
				powers.get(i).setHit();
			}
		}
	}
	
	
	public void setAlive() {
		dead = false;
	}

	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public int getMaxEnergy() {
		return maxEnergy;
	}
	
	public boolean isThrowing() {
		return throwing;
	}
	
	public boolean isPunching() {
		return punching;
	}
	
	public boolean isKicking() {
		return kicking;
	}
	
	public boolean isCrouching() {
		return down;
	}
	
	public boolean isWalkingBackwards() {
		if(currentAction == WALKINGBACKWARDS) {
			return true;
		}
		else return false;
	}
	
	public boolean isGettingHit() {
		return gettingHit;
	}
	
	public boolean isFlinching() {
		return flinching;
	}

	public void setThrowing(boolean b) {
		throwing = b;
	}
	
	public void setPunching(boolean b) {
		punching = b;
	}
	
	public void setKicking(boolean b) {
		kicking = b;
	}
	
	public void setWinning() {
		winning = true;
	}
	
	public void addEnergy(int i) {
		energy += i;
		if(energy > maxEnergy) energy = maxEnergy;
	}
	
	private void getNextPosition() {
		//movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		//cannot move while attacking except in air
		if((currentAction == PUNCHING || currentAction == KICKING || 
				currentAction == THROWING || currentAction == CROUCHING || dead)
				&& !(jumping || falling)) {
			dx = 0;
		}
		
		//jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		//falling
		if(falling) {
			if(!(dy > 0 && false)) {dy += fallSpeed;}
			
			if(dy > 0) jumping = false;
			
			//while pressing space going higher
			//if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	
	public void update() {
		
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(currentAction == PUNCHING ) {
			if(animation.hasPlayedOnce()) {
				punching = false;
			}
		}
		if(currentAction == KICKING) {
			if(animation.hasPlayedOnce()) {
				kicking = false;
			}
		}
		if(currentAction == THROWING) {
			if(animation.hasPlayedOnce()) {
				throwing = false;
			}
		}	
		if(currentAction == GETTINGHIT) {
			if(animation.hasPlayedOnce()) {
				gettingHit = false;
			}
		}
		if(currentAction == BLOCKING) {
			if(animation.hasPlayedOnce()) {
				//blocking = false;
			}
		}
		
		
		// eidikh dunamh
		if(throwing && currentAction != THROWING) {
			SuperPower sp = new SuperPower(tileMap, facingRight, id);
			sp.setPosition(x, y);
			powers.add(sp);
		}
		
		// update powers
		
		for(int i = 0; i < powers.size(); i++) {
			powers.get(i).update();
			
			if(powers.get(i).shouldRemove()) {
				powers.remove(i);
				i--;
			}
		}

		
		if(punching) {
			if(currentAction != PUNCHING) {
				kicking = false;
				currentAction = PUNCHING;
				animation.setFrames(sprites.get(PUNCHING));
				animation.setDelay(100);
				width = 250;
			}
		}
		else if(kicking) {
			if(currentAction != KICKING) {
				punching = false;
				currentAction = KICKING;
				animation.setFrames(sprites.get(KICKING));
				animation.setDelay(120);
				width = 250;
			}
		}
		else if(throwing) {
			if(currentAction !=  THROWING) {
				currentAction = THROWING;
				animation.setFrames(sprites.get(THROWING));
				animation.setDelay(100);
				width = 250;
			}
		}
		else if(blocking) {
			if(currentAction != BLOCKING) {
				currentAction = BLOCKING;
				animation.setFrames(sprites.get(BLOCKING));
				animation.setDelay(200);
				width = 150;
			}
		}
		else if(gettingHit) {
			if(currentAction != GETTINGHIT) {
				currentAction = GETTINGHIT;
				animation.setFrames(sprites.get(GETTINGHIT));
				animation.setDelay(200);
				width = 150;
				
				if(facingRight) { dx = - 6; }
				else dx = 6;
			}
		}	
		else if(winning) {
			if(currentAction != WINNING) {
				currentAction = WINNING;
				animation.setFrames(sprites.get(WINNING));
				animation.setDelay(200);
				width = 150;
			}
		}
		else if(down) {
			if(currentAction != CROUCHING) {
				currentAction = CROUCHING;
				animation.setFrames(sprites.get(CROUCHING));
				animation.setDelay(250);
				width = 150;
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(140);
				width = 150;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(140);
				width = 150;
			}
		}
		else if(dx > 0) {
			if(right) {
				if(currentAction != WALKINGFORWARDS) {
					currentAction = WALKINGFORWARDS;
					animation.setFrames(sprites.get(WALKINGFORWARDS));
					animation.setDelay(80);
					width = 150;
				}
			}
		}
		else if(dx < 0) {
			if(left) {
				if(currentAction != WALKINGBACKWARDS) {
					currentAction = WALKINGBACKWARDS;
					animation.setFrames(sprites.get(WALKINGBACKWARDS));
					animation.setDelay(80);
					width = 150;
				}
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(200);
				width = 150;
			}
		}
		

		animation.update();
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		
		// draw special power
		for(int i = 0; i < powers.size(); i++) {
			powers.get(i).draw(g);
		}
		
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
		
		//draw player
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
	}
	
	public boolean isDead() {
		return dead;
	}

	public void hit(int damage, Player p) {
		if((System.nanoTime() - flinchTimer) / 1000000 >= 1000) flinching = false;
		if(dead || flinching) return;	
		p.addEnergy(1);
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) {
			dead = true;
			p.setWinning();
		}
		flinching = true;
		flinchTimer = System.nanoTime();
		gettingHit = true;
	}
}








