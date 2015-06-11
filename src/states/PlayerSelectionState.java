package states;

import handlers.AudioManager;
import handlers.FileManager;
import handlers.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PlayerSelectionState extends GameState {
	
	private Font font, vsFont, font1;	
	private BufferedImage p1, p2, bg;
	
	private boolean samePlayers=false;
	private BufferedImage giwrgos,katerina,aggelos,euthimis,elena,panos,
							swkratis,nikiforos,vana, giannis;
	
	private String[] options = {
			"Γιώργος",
			"Κατερίνα",
			"’γγελος",
			"Eυθύμης",
			"Έλενα",
			"Πάνος",
			"Σωκράτης",
			"Νικηφόρος",
			"Βάνα",
			"Γιάννης"
	};
	
	private int curP1, curP2;	
	private int p1X, p1Y, p2X, p2Y;
	
	private FileManager selectedPlayers;
	AudioManager audio = new AudioManager();
	public PlayerSelectionState(GameStateManager gsm)
	{
		super(gsm);
		this.setStartingPlayers();
	}

	public void init() {
		try {
			samePlayers = false;
			font = new Font("Arial", Font.PLAIN, 32);
			vsFont = new Font("Arial", Font.BOLD, 48);
			font1 = new Font("Arial", Font.ITALIC, 20);
			
			p1 = ImageIO.read(getClass().getResourceAsStream("/Arrows/p1Ch.gif"));
			p2 = ImageIO.read(getClass().getResourceAsStream("/Arrows/p2Ch.gif"));
			
			selectedPlayers = new FileManager("selectedPlayers.txt");
			
			giwrgos = ImageIO.read(getClass().getResourceAsStream("/avatars/giwrgos.png"));
			katerina = ImageIO.read(getClass().getResourceAsStream("/avatars/katerina.png"));
			aggelos = ImageIO.read(getClass().getResourceAsStream("/avatars/aggelos.png"));
			euthimis = ImageIO.read(getClass().getResourceAsStream("/avatars/euthimis.png"));
			elena = ImageIO.read(getClass().getResourceAsStream("/avatars/elena.png"));
			panos = ImageIO.read(getClass().getResourceAsStream("/avatars/komotinaios.png"));
			swkratis = ImageIO.read(getClass().getResourceAsStream("/avatars/sokratis.png"));
			nikiforos = ImageIO.read(getClass().getResourceAsStream("/avatars/nikiforos.png"));
			vana = ImageIO.read(getClass().getResourceAsStream("/avatars/vana.png"));
			giannis = ImageIO.read(getClass().getResourceAsStream("/avatars/giannis.png"));
			
			bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/menuBG.gif"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		
	}

	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, null);

		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Παίχτης 1", 100, 50);
		g.drawString("Παίχτης 2", 550, 50);
		g.fillRect(50, 70, 250, 200);
		g.fillRect(500, 70, 250, 200);
		g.setColor(Color.orange);
		g.setFont(vsFont);
		g.drawString("VS", 365, 180);
		
		g.drawImage(giwrgos, 50, 320, 100, 100, null);
		g.drawImage(katerina, 200, 320, 100, 100, null);
		g.drawImage(aggelos, 350, 320, 100, 100, null);
		g.drawImage(euthimis, 500, 320, 100, 100, null);
		g.drawImage(elena, 650, 320, 100, 100, null);
		g.drawImage(panos, 50, 470, 100, 100, null);
		g.drawImage(swkratis, 200, 470, 100, 100, null);
		g.drawImage(nikiforos, 350, 470, 100, 100, null);
		g.drawImage(vana, 500, 470, 100, 100, null);
		g.drawImage(giannis, 650, 470, 100, 100, null);
		
		g.drawImage(p1, p1X, p1Y, 50, 50, null);
		g.drawImage(p2, p2X, p2Y, 50, 50, null);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(options[curP1], 80, 180);
		g.drawString(options[curP2], 530, 180);
		
		if(samePlayers) {
			g.setFont(font1);
			g.setColor(Color.orange);
			g.drawString("ΠΡΟΣΟΧΗ: Οι δυο παίχτες πρέπει να είναι διαφορετικοί μεταξύ τους.", 100, 292);
		}
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
		
			if(curP1 != curP2) {
				this.setSamePlayers(false);
				selectedPlayers.writeToFile(Integer.toString(curP1) + " " + Integer.toString(curP2));
				gsm.setState(gsm.STAGESELECTIONSTATE);
				this.setStartingPlayers();
			}
			else {
				this.setSamePlayers(true);
				audio.Playaudio("Wrong.wav");
			}
		}
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE) {
			
			audio.Playaudio("escape.wav");
			gsm.setState(1);
			this.setStartingPlayers();
			
		}
		if(k == KeyEvent.VK_W) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			curP1 -= 5;
			p1Y = 280;
			if(curP1 < 0) { 
				curP1 += 10;
				p1Y = 430;
			}
		}
		
		if(k == KeyEvent.VK_A) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			curP1--;
			p1X -= 150;
			if(curP1 == -1) {
				curP1 = options.length - 1;
				p1X = 650;
				p1Y = 430;
			}
			
			if(curP1 == 4) {
				audio.Playaudio("up-down.wav");
				p1X = 650;
				p1Y = 280;
			}
		}

		if(k == KeyEvent.VK_S) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			curP1 += 5;
			p1Y = 430;
			if(curP1 > 9) {
				curP1 -= 10;
				p1Y = 280;
			}
		}
		
		if(k == KeyEvent.VK_D) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			curP1++;
			p1X += 150;
			if(curP1 == 5) {
				p1X = 50;
				p1Y = 430;
			}
			
			if(curP1 == options.length) {
				curP1 = 0;
				p1X = 50;
				p1Y = 280;
			}
		}
		
		if(k == KeyEvent.VK_UP) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			curP2 -= 5;
			p2Y = 280;
			if(curP2 < 0) { 
				curP2 += 10;
				p2Y = 430;
			}
		}
		
		if(k == KeyEvent.VK_LEFT) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			curP2--;
			p2X -= 150;
			if(curP2 == -1) {
				curP2 = options.length - 1;
				p2X = 695;
				p2Y = 430;
			}
			
			if(curP2 == 4) {
				p2X = 695;
				p2Y = 280;
			}
		}

		if(k == KeyEvent.VK_DOWN) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			curP2 += 5;
			p2Y = 430;
			if(curP2 > 9) {
				curP2 -= 10;
				p2Y = 280;
			}
		}
		
		if(k == KeyEvent.VK_RIGHT) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			curP2++;
			p2X += 150;
			if(curP2 == 5) {
				p2X = 95;
				p2Y = 430;
			}
			
			if(curP2 == options.length) {
				curP2 = 0;
				p2X = 95;
				p2Y = 280;
			}
		}
		
	}
	public void keyReleased(int k) {}

public boolean isSamePlayers() {
	return samePlayers;
}

public void setSamePlayers(boolean samePlayers) {
	this.samePlayers = samePlayers;
}

public void setStartingPlayers()
{
	curP1 = 0;
	curP2 = 0;
	p1X = 50;
	p1Y = 280;
	p2X = 95;
	p2Y = 280;
}

}
