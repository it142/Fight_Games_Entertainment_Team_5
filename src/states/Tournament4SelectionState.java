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

public class Tournament4SelectionState extends GameState{
	
	private Font font,font1;	
	private BufferedImage p1,bg;
	
	
	private boolean samePlayers=false;
	private boolean[] flag= new boolean[4];
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
	
	private int currentPlayer;
	private int [] playerChar=new int[4];
	private int [] pX = new int[4];
	private int [] pY = new int[4];
	
	private FileManager selectedPlayers;
	AudioManager audio = new AudioManager();
	
	public Tournament4SelectionState(GameStateManager gsm)
	{
		super(gsm);
		this.setStartingPlayers();
		
	}

	public void init() {
		try {
			font = new Font("Arial", Font.PLAIN, 28);
			font1 = new Font("Arial", Font.ITALIC, 20);
			
			p1 = ImageIO.read(getClass().getResourceAsStream("/Arrows/p1Ch.gif"));
			
			selectedPlayers = new FileManager("4selectedPlayers.txt");
			
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
		g.drawString("Παίχτης 1", 80,70);
		g.drawString("Παίχτης 2", 250,70);
		g.drawString("Παίχτης 3", 420,70);
		g.drawString("Παίχτης 4", 590,70);
		
		g.fillRect(70,100, 150,150);
		g.fillRect(240,100, 150,150);
		g.fillRect(410, 100, 150, 150);
		g.fillRect(580, 100, 150, 150);
		
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
		
		
		g.drawImage(p1, pX[currentPlayer], pY[currentPlayer], 50, 50, null);
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		
		if(flag[0]==true)
			g.drawString(options[playerChar[0]], 80, 180);
		
		if(flag[1] == true)
		{
			g.drawString(options[playerChar[1]], 250, 180);
		}
		if(flag[2] == true)
		{
		
			g.drawString(options[playerChar[2]], 420, 180);
		}
		if(flag[3] == true)
		{
			g.drawString(options[playerChar[3]], 590, 180);
		}
		
		
		if(samePlayers) {
			g.setFont(font1);
			g.setColor(Color.orange);
			g.drawString("ΠΡΟΣΟΧΗ: Οι τέσσερις παίχτες πρέπει να είναι διαφορετικοί μεταξύ τους.", 100, 292);
			this.setStartingPlayers();
		}
	}

	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_ENTER) 
		{	
			this.setSamePlayers(false);
			//το 1 στο comparePlayers σημαινει οτι συγκρινει τους παιχτες με || αναμεσα
			if(this.ComparePlayers(1))
			{	
				this.setSamePlayers(true);
				audio.Playaudio("Wrong.wav");
			}
			
			audio.Playaudio("Select.wav");
			if(currentPlayer==0 || currentPlayer==1 || currentPlayer==2 )
					currentPlayer += 1;
			if(currentPlayer==3 && flag[3]==true)
			{	
				// το 0 στο comparePlayers σημαινει οτι συγκρινει τους παιχτες με && αναμεσα
				if(this.ComparePlayers(0)==true) 
				{
					
					selectedPlayers.writeToFile
					(Integer.toString(playerChar[0]) + " " + Integer.toString(playerChar[1])
					+" " + Integer.toString(playerChar[2])+" " + Integer.toString(playerChar[3]));
					gsm.setState(gsm.TOURNAMENT4DIAGRAMSTATE);
					this.setStartingPlayers();
					audio.Playaudio("trumpet.wav");
					}
					else 
					{	audio.Playaudio("Wrong.wav");
						this.setSamePlayers(true);
						this.setStartingPlayers();
						
					}
				}
			this.setFlag(currentPlayer);
		}
		
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE) {
			
			audio.Playaudio("escape.wav");
			gsm.setState(3);
			this.setStartingPlayers();
			
		}
		if(k == KeyEvent.VK_W) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			playerChar[currentPlayer] -=5;
			pY[currentPlayer] = 280;
			if( playerChar[currentPlayer] < 0) { 
				playerChar[currentPlayer] += 10;
				pY[currentPlayer] = 430;
			}
		}
		
		if(k == KeyEvent.VK_A) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			playerChar[currentPlayer]--;
			pX[currentPlayer] -= 150;
			if(playerChar[currentPlayer] == -1) {
				playerChar[currentPlayer] = options.length - 1;
				pX[currentPlayer] = 650;
				pY[currentPlayer] = 430;
			}
			
			if(playerChar[currentPlayer] == 4) {
				audio.Playaudio("up-down.wav");
				pX[currentPlayer] = 650;
				pY[currentPlayer] = 280;
			}
		}

		if(k == KeyEvent.VK_S) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			playerChar[currentPlayer] += 5;
			pY[currentPlayer] = 430;
			if(playerChar[currentPlayer] > 9) {
				playerChar[currentPlayer] -= 10;
				pY[currentPlayer] = 280;
			}
		}
		
		if(k == KeyEvent.VK_D) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			playerChar[currentPlayer]++;
			pX[currentPlayer] += 150;
			if(playerChar[currentPlayer] == 5) {
				pX[currentPlayer] = 50;
				pY[currentPlayer] = 430;
			}
			
			if(playerChar[currentPlayer] == options.length) {
				playerChar[currentPlayer] = 0;
				pX[currentPlayer] = 50;
				pY[currentPlayer] = 280;
			}
		}
		
		if(k == KeyEvent.VK_UP) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			playerChar[currentPlayer] -= 5;
			pY[currentPlayer] = 280;
			if(playerChar[currentPlayer] < 0) { 
				playerChar[currentPlayer] += 10;
				pY[currentPlayer] = 430;
			}
		}
		
		if(k == KeyEvent.VK_LEFT) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			playerChar[currentPlayer]--;
			pX[currentPlayer] -= 150;
			if(playerChar[currentPlayer] == -1) {
				playerChar[currentPlayer] = options.length - 1;
				pX[currentPlayer] = 695;
				pY[currentPlayer]= 430;
			}
			
			if(playerChar[currentPlayer] == 4) {
				pX[currentPlayer] = 695;
				pY[currentPlayer] = 280;
			}
		}

		if(k == KeyEvent.VK_DOWN) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			playerChar[currentPlayer] += 5;
			pY[currentPlayer] = 430;
			if(playerChar[currentPlayer] > 9) {
				playerChar[currentPlayer] -= 10;
				pY[currentPlayer] = 280;
			}
		}
		
		if(k == KeyEvent.VK_RIGHT) {
			this.setSamePlayers(false);
			audio.Playaudio("up-down.wav");
			playerChar[currentPlayer]++;
			pX[currentPlayer] += 150;
			if(playerChar[currentPlayer] == 5) {
				pX[currentPlayer] = 95;
				pY[currentPlayer] = 430;
			}
		}
			if(playerChar[currentPlayer] == options.length) {
				playerChar[currentPlayer] = 0;
				pX[currentPlayer] = 95;
				pY[currentPlayer] = 280;
			}
}

public boolean isSamePlayers() {
	return samePlayers;
}

public void setSamePlayers(boolean samePlayers) {
	this.samePlayers = samePlayers;
}


public void setPlayerChar(int[] playerChar) {
	this.playerChar = playerChar;
}

public int[] getPlayerChar()
{
	return playerChar;
}


public void setStartingPlayers()
{
	currentPlayer=0;
	playerChar[0] = 0;
	playerChar[1] = 0;
	playerChar[2] = 0;
	playerChar[3] = 0;
	flag[0]=true;
	flag[1]=false;
	flag[2]=false;
	flag[3]=false;
	pX[0] = 50;
	pY[0]= 280;
	pX[1] = 50;
	pY[1]= 280;
	pX[2] = 50;
	pY[2] = 280;
	pX[3] = 50;
	pY[3] = 280;
}

public void setFlag(int aNumber)
{
	if(aNumber==0)
	{
		flag[0]=true;
	}
	if(aNumber==1)
	{
		flag[1]=true;
	}
	
	if(aNumber==2)
	{
		flag[2]=true;
	}
	
	if(aNumber==3)
	{
		flag[3]=true;
	}
	
}

public boolean ComparePlayers(int aChoice)
{	
	if(aChoice==0)
	{
	if((playerChar[0] != playerChar[1]) && ( playerChar[0] != playerChar[2])
	&&( playerChar[0]!= playerChar[3])&& (playerChar[1] != playerChar[2]) 
	&& (playerChar[1] != playerChar[3])
	&& (playerChar[2] != playerChar[3]))
		return true;
	}
	else if(aChoice==1)
	{
		if(flag[1]==true)
		{	
			if(	(playerChar[0]==playerChar[1]))
			{
				
				return true;
			}
		}
		if(flag[1]==true && flag[2]==true)
		{
			if((playerChar[0] == playerChar[1]) || ( playerChar[0] == playerChar[2])
					|| (playerChar[1] == playerChar[2]) )
			{
				return true;
			}
		}
		if(flag[1]==true && flag[2]==true && flag[3]==true)
		{
			if(	(playerChar[0] == playerChar[1]) || ( playerChar[0] == playerChar[2])
				||( playerChar[0] == playerChar[3])|| (playerChar[1] == playerChar[2]) 
				|| (playerChar[1] == playerChar[3])
				|| (playerChar[2] == playerChar[3]))
			{
				return true;
			}
			
		}
			
	}
	return false;
}




public void keyReleased(int k) {}
}