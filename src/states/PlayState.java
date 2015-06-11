package states;

import handlers.AudioManager;
import handlers.FileManager;
import handlers.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.ArrayList;

import angelonias.tilemap.TileMap;
import content.Avatars;
import content.Stages;
import entity.Player;

public class PlayState extends GameState {
	
	private FileManager file;
	private BufferedImage bg;
	private TileMap tilemap;

	private int selectedp1;
	private int selectedp2;
	private Player player1;
	private Player player2;
	private BufferedImage avatar1;
	private BufferedImage avatar2;
	
	private boolean attackedLast = true;
	
	private String[] count;
	private int curCount;
	private long countTimer;
	private boolean keysLocked;
	private boolean roundOver;
	private long timer;
	
	private Font countFont;
	
	private int currentRound;
	private int p1Wins;
	private int p2Wins;
	
	private AudioManager audio;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		audio=new AudioManager();
		countFont = new Font("Arial", Font.BOLD, 100);
		
	}

	
	
	public void init() {
		
		curCount = 0;
		keysLocked = true;
		roundOver = false;
		try {
			// MODE
			FileManager mode = new FileManager("tournament.txt");
			ArrayList<String> modeData = mode.readFile();
			
			if(modeData.get(0).equals("false")) { // 1v1
				//Read the selected players from txt file
				file = new FileManager("selectedPlayers.txt");
				ArrayList<String> players = file.readFile();
				selectedp1 = Integer.parseInt(players.get(0));
				selectedp2 = Integer.parseInt(players.get(1));
				
				
			}
			else if(modeData.get(0).equals("true") && modeData.get(1).equals("4")) {
				file = new FileManager("4selectedPlayers.txt");
				ArrayList<String> players = file.readFile();
				selectedp1 = Integer.parseInt(players.get(0));
				selectedp2 = Integer.parseInt(players.get(1));
			}
			else if(modeData.get(0).equals("true") && modeData.get(1).equals("8")) {
				
			}
			
			avatar1 =Avatars.get(selectedp1);
			avatar2 =Avatars.get(selectedp2);
			
			
			// tilemap
			tilemap = new TileMap(60);
			tilemap.loadTiles("/TileMap/tileset.gif");
			tilemap.loadMap("/TileMap/map.map");
			tilemap.setPosition(0, 0);
			
			
			
			// LOAD PLAYERS

			if(selectedp1 == 0) {player1 = new Player(tilemap,"/Sprites/giorgosSprites.gif", true, 0);}
			else if(selectedp1 == 1) {player1 = new Player(tilemap,"/Sprites/katerinaSprites.gif", true, 1);}
			else if(selectedp1 == 2) {player1 = new Player(tilemap,"/Sprites/AggelosSprites.gif", true, 2);}
			else if(selectedp1 == 3) {player1 = new Player(tilemap,"/Sprites/EfthimisSprites.gif", true, 3);}
			else if(selectedp1 == 4) {player1 = new Player(tilemap,"/Sprites/elenhSprites.gif", true, 4);}
			else if(selectedp1 == 5) {player1 = new Player(tilemap,"/Sprites/komotineosSprites.gif", true, 5);}
			else if(selectedp1 == 6) {player1 = new Player(tilemap,"/Sprites/swkrathsSprites.gif", true, 6);}
			else if(selectedp1 == 7) {player1 = new Player(tilemap,"/Sprites/nikiforosSprites.gif", true, 7);}
			else if(selectedp1 == 8) {player1 = new Player(tilemap,"/Sprites/banaSprites.gif", true, 8);}
			else if(selectedp1 == 9) {player1 = new Player(tilemap,"/Sprites/giannisSprites.gif", true, 9);}
			player1.setPosition(200, 440);
				
			if(selectedp2 == 0) {player2 = new Player(tilemap,"/Sprites/giorgosSprites.gif", false, 0);}
			else if(selectedp2 == 1) {player2 = new Player(tilemap,"/Sprites/katerinaSprites.gif", false, 1);}
			else if(selectedp2 == 2) {player2 = new Player(tilemap,"/Sprites/AggelosSprites.gif", false, 2);}
			else if(selectedp2 == 3) {player2 = new Player(tilemap,"/Sprites/EfthimisSprites.gif", false, 3);}
			else if(selectedp2 == 4) {player2 = new Player(tilemap,"/Sprites/elenhSprites.gif", false, 4);}
			else if(selectedp2 == 5) {player2 = new Player(tilemap,"/Sprites/komotineosSprites.gif", false, 5);}
			else if(selectedp2 == 6) {player2 = new Player(tilemap,"/Sprites/swkrathsSprites.gif", false, 6);}
			else if(selectedp2 == 7) {player2 = new Player(tilemap,"/Sprites/nikiforosSprites.gif", false, 7);}
			else if(selectedp2 == 8) {player2 = new Player(tilemap,"/Sprites/banaSprites.gif", false, 8);}
			else if(selectedp2 == 9) {player2 = new Player(tilemap,"/Sprites/giannisSprites.gif", false, 9);}
			player2.setPosition(700, 440);

	
			// Read the selected stage from txt file
			file = new FileManager("selectedStage.txt");
			ArrayList<String> stage = file.readFile();
			
			// Load Stage
			bg = Stages.get(Integer.parseInt(stage.get(0)));
			
			
		
			
			//Read the current round and player wins
			file = new FileManager("currentRound.txt");
			ArrayList<String> data = file.readFile();
			currentRound = Integer.parseInt(data.get(0));
			p1Wins = Integer.parseInt(data.get(1));
			p2Wins = Integer.parseInt(data.get(2));
			
			count = new String[4];
			count[0] = "";
			count[1] = "Round " + Integer.toString(currentRound);
			count[2] = "Ready";
			count[3] = "Fight";
			countTimer = System.nanoTime();
			
			if (currentRound==1)
			{
				audio.StartLoop("battle");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		//check player attacks
		if(player1!= null && player2 != null) {
			player1.checkAttack(player2);
			player2.checkAttack(player1);
		}
		
		//update players
		if(player1 != null) player1.update();
		if(player2 != null) player2.update();
	
		//update counter
		if(curCount < 4) {
			if((System.nanoTime() - countTimer) / 1000000 >= 1000) {
				curCount++;
				countTimer = System.nanoTime();
			}
			
			if(curCount == 4) keysLocked = false;
		}
		
		// round over
		if(player1 != null && player2 != null) {
			if(player1.isDead() || player2.isDead()) {
				keysLocked = true;
				if(roundOver != true) {
					roundOver = true;
					currentRound++;
					timer = System.nanoTime();
					
				}
				
			}
		}
		
		if(roundOver && ((System.nanoTime() - timer) / 1000000) >= 3000) {
			select();
			
		}
	}
	
	public void select() {
		if(player2.isDead()) {
			p1Wins++;
		}
		else if(player1.isDead()) {
			p2Wins++;
		}
		
		if(p1Wins == 2 || p2Wins == 2) {
			file = new FileManager("currentRound.txt");
			file.writeToFile("1 0 0");
			player1.setAlive();
			player2.setAlive();
			
			FileManager manager = new FileManager("tournament.txt");
			ArrayList<String> dat = manager.readFile();

			
			if(p1Wins == 2) {
				file = new FileManager("victories.txt");
				ArrayList<String> victories = file.readFile();
				int addVic = Integer.parseInt(victories.get(selectedp1)) + 1;
								
				try {
					PrintWriter out = new PrintWriter("victories.txt");
					
					for(int i = 0; i < 10; i ++) {
						if(i == selectedp1) {
							out.print(Integer.toString(addVic) + " ");
						}
						else out.print(victories.get(i) + " ");
						
					}
					
					out.println();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				
			}
			else if(p2Wins == 2) {
				file = new FileManager("victories.txt");
				ArrayList<String> victories = file.readFile();
				int addVic = Integer.parseInt(victories.get(selectedp2)) + 1;
								
				try {
					PrintWriter out = new PrintWriter("victories.txt");
					
					for(int i = 0; i < 10; i ++) {
						if(i == selectedp2) {
							out.print(Integer.toString(addVic) + " ");
						}
						else out.print(victories.get(i) + " ");
						
					}
					
					out.println();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			audio.StopLoop("battle");
			if(dat.get(0).equals("true")) {
				String match = dat.get(3);
				int plus = Integer.parseInt(match) + 1;
				manager.writeToFile("true " + dat.get(1) + " " + dat.get(2) + " " + Integer.toString(plus));
				
				gsm.setState(gsm.TOURNAMENT4DIAGRAMSTATE);
			}
			else {
				gsm.setState(gsm.MODESELECTIONSTATE);
			}
			
		}
		else {
			file = new FileManager("currentRound.txt");
			String result = new String();
			result = Integer.toString(currentRound) + " " + Integer.toString(p1Wins) + " " + Integer.toString(p2Wins);
			file.writeToFile(result);
			
			gsm.setState(gsm.PLAYSTATE);
		}
	}
	
	public void draw(Graphics2D g) {
		// Draw Stage
		g.drawImage(bg, 0, 0, null);
		
		// Draw avatars
		g.drawImage(avatar1, 10, 10, 100, 100, null);
		g.drawImage(avatar2, 690, 10, 100, 100, null);
		
		// Draw HUD
		if(player1 != null && player2 != null) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(118, 18, 254, 14);
			g.fillRect(428, 18, 254, 14);
			
			if(player1.getHealth() < 7) {
				g.setColor(Color.RED);
			}
			else if(player1.getHealth() < 15) {
				g.setColor(Color.ORANGE);
			}
			else {
				g.setColor(Color.GREEN);
			}
			g.fillRect(120, 20, player1.getHealth() * 10, 10);
			
			if(player2.getHealth() < 7) {
				g.setColor(Color.RED);
			}
			else if(player2.getHealth() < 15) {
				g.setColor(Color.ORANGE);
			}
			else {
				g.setColor(Color.GREEN);
			}
			g.fillRect(430 + 10*Math.abs(player2.getHealth() - 25), 20, 250 - 10*Math.abs(player2.getHealth() - 25), 10);
			
			g.setColor(Color.MAGENTA);
			for(int i = 0; i < player1.getEnergy(); i++) {
				g.fillRect(120 + i * 50 , 40, 40, 10);
			}
			
			for(int i = 0; i < player2.getEnergy(); i++) {
				g.fillRect(430 + i * 50 , 40, 40, 10);
			}
		}
		
		// Draw players
		if(attackedLast) {
			if(player1 != null) player1.draw(g);
			if(player2 != null) player2.draw(g);
		}
		else {
			if(player2 != null) player2.draw(g);
			if(player1 != null) player1.draw(g);
		}
		
		// Draw counter
		if(count != null) {
			if(curCount < 4) {
				g.setColor(Color.GREEN);
				g.setFont(countFont);
				if(curCount == 1) g.drawString(count[curCount], 200, 300);
				else if(curCount == 2) g.drawString(count[curCount], 250, 300);
				else g.drawString(count[curCount], 280, 300);
			}
		}
		
		// Draw K.O.
		if(roundOver) {
			if((System.nanoTime() - timer) / 1000000 >= 2000) return;
			g.setColor(Color.RED);
			g.setFont(countFont);
			g.drawString("K.O.", 270, 300);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_BACK_SPACE || k == KeyEvent.VK_ESCAPE){
			
			file = new FileManager("currentRound.txt");
			file.writeToFile("1 0 0");
			player1.setAlive();
			player2.setAlive();
			audio.StopLoop("battle");
			gsm.setState(gsm.STAGESELECTIONSTATE);
		}	
		if(!keysLocked) {
			
			if(!player2.isGettingHit()) {
				if(k == KeyEvent.VK_LEFT) {
					attackedLast = true;
					player2.setLeft(true);
				}
				if(k == KeyEvent.VK_RIGHT) {
					attackedLast = true;
					player2.setRight(true);
				}
				if(k == KeyEvent.VK_UP) {
					attackedLast = true;
					player2.setJumping(true);
				}
				if(k == KeyEvent.VK_DOWN) {
					attackedLast = true;
					player2.setDown(true);
				}
				
				if(k == KeyEvent.VK_M) {
					if(!player2.isPunching() && !player2.isKicking() && player2.getEnergy() == 5) {
						attackedLast = true;
						player2.setThrowing(true);
						player2.addEnergy(-5);
					}
				}
				if(k == KeyEvent.VK_J) {	
					if(!player2.isKicking() && !player2.isThrowing()) {
						attackedLast = true;
						player2.setPunching(true);
					}
				}
				if(k == KeyEvent.VK_K) {
					if(!player2.isPunching() && !player2.isThrowing()) {
						attackedLast = true;
						player2.setKicking(true);	
					}
				}
			}
			
			if(!player1.isGettingHit()) {
				if(k == KeyEvent.VK_A) {
					attackedLast = false;
					player1.setLeft(true);
				}
				if(k == KeyEvent.VK_D && !player1.isGettingHit()) {
					attackedLast = false;
					player1.setRight(true);
				}
				if(k == KeyEvent.VK_W) {
					attackedLast = false;
					player1.setJumping(true);
				}
				if(k == KeyEvent.VK_S) {
					attackedLast = false;
					player1.setDown(true);
				}
				
		
		
				if(k == KeyEvent.VK_F) {
					if(!player1.isPunching() && !player1.isKicking() && !player1.isGettingHit() && player1.getEnergy() == 5) {
						attackedLast = false;
						player1.setThrowing(true);
						player1.addEnergy(-5);
					}
				}
				if(k == KeyEvent.VK_R) {	
					if(!player1.isKicking() && !player1.isThrowing() && !player1.isGettingHit()) {
						attackedLast = false;
						player1.setPunching(true);
					}
				}
				if(k == KeyEvent.VK_T) {
					if(!player1.isPunching() && !player1.isThrowing() && !player1.isGettingHit()) {
						attackedLast = false;
						player1.setKicking(true);	
					}
				}
			}
		}
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) {
			player2.setLeft(false);
		}
		if(k == KeyEvent.VK_RIGHT) {
			player2.setRight(false);
		}
		if(k == KeyEvent.VK_UP) {
			player2.setJumping(false);
		}
		if(k == KeyEvent.VK_DOWN) {
			player2.setDown(false);
		}
		
		if(k == KeyEvent.VK_A) {
			player1.setLeft(false);
		}
		if(k == KeyEvent.VK_D) {
			player1.setRight(false);
		}
		if(k == KeyEvent.VK_W) {
			player1.setJumping(false);
		}
		if(k == KeyEvent.VK_S) {
			player1.setDown(false);
		}
	}
	


}
