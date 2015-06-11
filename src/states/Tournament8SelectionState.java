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

public class Tournament8SelectionState extends GameState{
	
	private Font font,font1;	
	private BufferedImage p1,bg;
	
	
	private boolean samePlayers=false;
	private boolean[] flag= new boolean[8];
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
	private int [] playerChar=new int[8];
	private int [] pX = new int[8];
	private int [] pY = new int[8];
	
	private FileManager selectedPlayers;
	AudioManager audio = new AudioManager();
	
	public Tournament8SelectionState(GameStateManager gsm)
	{
		super(gsm);
		this.setStartingPlayers();
		
	}

	public void init() {
		try {
			font = new Font("Arial", Font.PLAIN, 20);
			font1 = new Font("Arial", Font.ITALIC, 20);
			
			p1 = ImageIO.read(getClass().getResourceAsStream("/Arrows/p1Ch.gif"));
			
			selectedPlayers = new FileManager("8selectedPlayers.txt");
			
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
		g.drawString("Παίχτης 1",115,30);
		g.drawString("Παίχτης 2", 275,30);
		g.drawString("Παίχτης 3", 445,30);
		g.drawString("Παίχτης 4", 615,30);
		g.drawString("Παίχτης 5", 115,180);
		g.drawString("Παίχτης 6", 275,180);
		g.drawString("Παίχτης 7", 445,180);
		g.drawString("Παίχτης 8", 615	,180);
		
		g.fillRect(110,50, 100,100);
		g.fillRect(270,50, 100,100);
		g.fillRect(440, 50, 100,100);
		g.fillRect(610, 50, 100,100);
		g.fillRect(110,200, 100,100);
		g.fillRect(270,200, 100,100);
		g.fillRect(440, 200, 100,100);
		g.fillRect(610, 200, 100,100);
		
		
		
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
		
		// μπορω και με for
		if(flag[0]==true)
			g.drawString(options[playerChar[0]], 117, 105);
		if(flag[1] == true)
			g.drawString(options[playerChar[1]], 277, 105);
		if(flag[2] == true)
			g.drawString(options[playerChar[2]], 447, 105);
		if(flag[3] == true)
			g.drawString(options[playerChar[3]], 617, 105);
		if(flag[4]==true)
			g.drawString(options[playerChar[4]], 117, 255);
		if(flag[5]==true)
			g.drawString(options[playerChar[5]], 277, 255);
		if(flag[6]==true)
			g.drawString(options[playerChar[6]], 447, 255);
		if(flag[7]==true)
			g.drawString(options[playerChar[7]], 617, 255);
		
		
		
		
		
		if(samePlayers) {
			g.setFont(font1);
			g.setColor(Color.orange);
			g.drawString("ΠΡΟΣΟΧΗ: Οι οχτώ παίχτες πρέπει να είναι διαφορετικοί μεταξύ τους.", 100, 292);
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
			if(currentPlayer==0 || currentPlayer==1 || currentPlayer==2|| currentPlayer==3|| 
					currentPlayer==4 || currentPlayer==5|| currentPlayer==6)
					currentPlayer += 1;
			
			if(currentPlayer==7 && flag[7]==true)
			{	
				// το 0 στο comparePlayers σημαινει οτι συγκρινει τους παιχτες με && αναμεσα
				if(this.ComparePlayers(0)==true) 
				{
					selectedPlayers.writeToFile
					(Integer.toString(playerChar[0]) + " " + Integer.toString(playerChar[1])
					+" " + Integer.toString(playerChar[2])+" " + Integer.toString(playerChar[3])
					+" " + Integer.toString(playerChar[4])+" " + Integer.toString(playerChar[5])
					+" " + Integer.toString(playerChar[6])+" " + Integer.toString(playerChar[7]));
					gsm.setState(gsm.TOURNAMENT8DIAGRAMSTATE);
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
	
	for(int i=0;i<8;i++)
	{
		playerChar[i]=0;
		pX[i]=50;
		pY[i]=280;
		if(i==0)
			flag[i]=true;
		else
			flag[i]=false;
	}
}

public void setFlag(int aNumber)
{
		flag[aNumber]=true;
}

public boolean ComparePlayers(int aChoice)
{	
	if(aChoice==0)
	{
	if((playerChar[0] != playerChar[1]) && ( playerChar[0] != playerChar[2])
	&&(playerChar[0]!= playerChar[3]) &&(playerChar[0]!= playerChar[4]) 
	&&(playerChar[0]!= playerChar[5])
	&&(playerChar[0]!= playerChar[6])
	&&(playerChar[0]!= playerChar[7])
	
	&&(playerChar[1] != playerChar[2])
	&&(playerChar[1] != playerChar[3]) 
	&& (playerChar[1] != playerChar[4])
	&&(playerChar[1] != playerChar[5])
	&&(playerChar[1] != playerChar[6])
	&&(playerChar[1] != playerChar[7])
	
	&& (playerChar[2] != playerChar[3])
	&& (playerChar[2] != playerChar[4])
	&& (playerChar[2] != playerChar[5])
	&& (playerChar[2] != playerChar[6])
	&& (playerChar[2] != playerChar[7])
	
	&& (playerChar[3] != playerChar[4])
	&& (playerChar[3] != playerChar[5])
	&& (playerChar[3] != playerChar[6])
	&& (playerChar[3] != playerChar[7])
	
	&& (playerChar[4] != playerChar[5])
	&& (playerChar[4] != playerChar[6])
	&& (playerChar[4] != playerChar[7])
	
	&& (playerChar[5] != playerChar[6])
	&& (playerChar[6] != playerChar[7])
	
	&& (playerChar[6] != playerChar[7]))
	
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
		if(flag[1]==true && flag[2]==true && flag[3]==true && flag[4]==true)
		{
			if(		(playerChar[0] == playerChar[1]) 
					|| ( playerChar[0] == playerChar[2])
					||( playerChar[0] == playerChar[3])
					|| ( playerChar[0] == playerChar[4]) 
					
					// 
					
					|| (playerChar[1] == playerChar[2]) 
					|| (playerChar[1] == playerChar[3])
					|| (playerChar[1] == playerChar[4])
					
					||(playerChar[2] == playerChar[3])
					|| (playerChar[2] == playerChar[4])
				
					||(playerChar[3] == playerChar[4]))
				
				{
					return true;
				}
		}
		if(flag[1]==true && flag[2]==true && flag[3]==true && flag[4]==true && flag[5]==true)
		{
			if(		(playerChar[0] == playerChar[1]) 
					|| 	(playerChar[0] == playerChar[2])
					||	(playerChar[0] == playerChar[3])
					|| 	(playerChar[0] == playerChar[4])
					|| 	(playerChar[0] == playerChar[5])
					
					// 
					
					||  (playerChar[1] == playerChar[2]) 
					||  (playerChar[1] == playerChar[3])
					||  (playerChar[1] == playerChar[4])
					|| 	(playerChar[1] == playerChar[5])
					
					|| 	(playerChar[2] == playerChar[3])
					|| 	(playerChar[2] == playerChar[4])
					|| 	(playerChar[2] == playerChar[5])
				
					||	(playerChar[3] == playerChar[4])
					|| 	(playerChar[3] == playerChar[5])
					
					|| 	(playerChar[4] == playerChar[5]))
				
				{
					return true;
				}
		}
		if(flag[1]==true && flag[2]==true && flag[3]==true && flag[4]==true && flag[5]==true
				&&flag[6]==true	)
		{
			if(		(playerChar[0] == playerChar[1]) 
					|| 	(playerChar[0] == playerChar[2])
					||	(playerChar[0] == playerChar[3])
					|| 	(playerChar[0] == playerChar[4])
					|| 	(playerChar[0] == playerChar[5])
					|| 	(playerChar[0] == playerChar[6])
					
					
					// 
					
					||  (playerChar[1] == playerChar[2]) 
					||  (playerChar[1] == playerChar[3])
					||  (playerChar[1] == playerChar[4])
					|| 	(playerChar[1] == playerChar[5])
					|| 	(playerChar[1] == playerChar[6])
					
					|| 	(playerChar[2] == playerChar[3])
					|| 	(playerChar[2] == playerChar[4])
					|| 	(playerChar[2] == playerChar[5])
					|| 	(playerChar[2] == playerChar[6])
				
					||	(playerChar[3] == playerChar[4])
					|| 	(playerChar[3] == playerChar[5])
					|| 	(playerChar[3] == playerChar[6])
					
					|| 	(playerChar[4] == playerChar[5])
					|| 	(playerChar[4] == playerChar[6])
					
					
					|| 	(playerChar[5] == playerChar[6]))
				
				{
					return true;
				}
		}
		if(flag[1]==true && flag[2]==true && flag[3]==true && flag[4]==true && flag[5]==true
				&&flag[6]==true && flag[7]==true)
		{
			if(		(playerChar[0] == playerChar[1]) 
					|| 	(playerChar[0] == playerChar[2])
					||	(playerChar[0] == playerChar[3])
					|| 	(playerChar[0] == playerChar[4])
					|| 	(playerChar[0] == playerChar[5])
					|| 	(playerChar[0] == playerChar[6])
					|| 	(playerChar[0] == playerChar[7])
					
					
					// 
					
					||  (playerChar[1] == playerChar[2]) 
					||  (playerChar[1] == playerChar[3])
					||  (playerChar[1] == playerChar[4])
					|| 	(playerChar[1] == playerChar[5])
					|| 	(playerChar[1] == playerChar[6])
					|| 	(playerChar[1] == playerChar[7])
					
					|| 	(playerChar[2] == playerChar[3])
					|| 	(playerChar[2] == playerChar[4])
					|| 	(playerChar[2] == playerChar[5])
					|| 	(playerChar[2] == playerChar[6])
					|| 	(playerChar[2] == playerChar[7])
				
					||	(playerChar[3] == playerChar[4])
					|| 	(playerChar[3] == playerChar[5])
					|| 	(playerChar[3] == playerChar[6])
					|| 	(playerChar[3] == playerChar[7])
					
					|| 	(playerChar[4] == playerChar[5])
					|| 	(playerChar[4] == playerChar[6])
					|| 	(playerChar[4] == playerChar[7])
					
					
					|| 	(playerChar[5] == playerChar[6])
					|| 	(playerChar[5] == playerChar[7])
					
					|| 	(playerChar[6] == playerChar[7]))
				
				{
					return true;
				}
		}
		
		
		
		
	}
	return false;
}

public void keyReleased(int k) {}
}

