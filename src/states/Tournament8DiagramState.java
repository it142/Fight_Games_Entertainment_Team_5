package states;

import handlers.AudioManager;
import handlers.GameStateManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Tournament8DiagramState extends GameState {
	

		private BufferedImage bg;
		private Font font,font1;
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
		private ArrayList<Integer> playerChar = new ArrayList<Integer>();
		
		
		
		public Tournament8DiagramState(GameStateManager gsm) {
			super(gsm);
		}
		
		
		AudioManager audio = new AudioManager();
		
		
		
		public ArrayList<Integer> getPlayerChar() {
			return playerChar;
		}

		public void setPlayerChar(int[] players) {
			for(int i=0;i<players.length;i++)
			{
				playerChar.add(players[i]);
			}
			
			Collections.shuffle(playerChar);
		}
		
		
		
		
		
		public void init() {
			try{
				
				font = new Font("Arial", Font.BOLD, 12);
				font1 = new Font("Arial", Font.BOLD, 24);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		public void update() {
			
		}

		public void draw(Graphics2D g) {
			
			try {
				bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/menuBG.gif"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
			
			g.setFont(font1);
			
	
			g.setColor(Color.WHITE);
			g.fillRect(363,50, 75,75);
			
			g.fillRect(162,150, 75,75);
			g.fillRect(562,150, 75,75);
			
			g.fillRect(62,300, 75,75);
			g.fillRect(262,300, 75,75);
			g.fillRect(462,300, 75,75);
			g.fillRect(662,300, 75,75);
			
			
			g.fillRect(12,475, 75,75);
			g.fillRect(112,475, 75,75);
			g.fillRect(212,475,75,75);
			g.fillRect(312,475, 75,75);
			g.fillRect(412,475, 75,75);
			g.fillRect(512,475, 75,75);
			g.fillRect(612,475,75,75);
			g.fillRect(712,475, 75,75);
			
			
			
			
			g.setColor(Color.yellow);
			Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
			
            //7
            	
            	g2.draw(new Line2D.Float(600,80,600,148));
            	g2.draw(new Line2D.Float(600,80,440,80));
            
            	g2.draw(new Line2D.Float(200,80,200,148));
            	g2.draw(new Line2D.Float(200,80,360,80));
      
            //6
            
            g2.draw(new Line2D.Float(639,180,700,180));
            g2.draw(new Line2D.Float(700,297,700,180));
            
             g2.draw(new Line2D.Float(500,180,559,180));
             g2.draw(new Line2D.Float(500,297,500,180));
            
            //5
            g2.draw(new Line2D.Float(239,180,300,180));
            g2.draw(new Line2D.Float(300,297,300,180));
            
             g2.draw(new Line2D.Float(100,180,159,180));
             g2.draw(new Line2D.Float(100,297,100,180));
            
            
            //4
            g2.draw(new Line2D.Float(738,335,753,335));
            g2.draw(new Line2D.Float(753,472,753,335));
            
             g2.draw(new Line2D.Float(645,335,660,335));
             g2.draw(new Line2D.Float(645,472,645,335));
            
            
            //3
            g2.draw(new Line2D.Float(538,335,553,335));
            g2.draw(new Line2D.Float(553,472,553,335));
            
             g2.draw(new Line2D.Float(445,335,460,335));
             g2.draw(new Line2D.Float(445,472,445,335));
           
            
            //2
            
            g2.draw(new Line2D.Float(338,335,353,335));
            g2.draw(new Line2D.Float(353,472,353,335));
            
             g2.draw(new Line2D.Float(245,335,260,335));
             g2.draw(new Line2D.Float(245,472,245,335));
            
            
            
            
            //1
            
            g2.draw(new Line2D.Float(138,335,153,335));
            g2.draw(new Line2D.Float(153,472,153,335));
            
             g2.draw(new Line2D.Float(45,335,60,335));
             g2.draw(new Line2D.Float(45,472,45,335));
			
			
			g.setFont(font1);
			g.setColor(Color.black);
			g.drawString("Νικητής",360,40);
			g.setFont(font);
			g.drawString(options[playerChar.get(0)], 25, 517);
			g.drawString(options[playerChar.get(1)], 125, 517);
			g.drawString(options[playerChar.get(2)], 225, 517);
			g.drawString(options[playerChar.get(3)], 325, 517);
			g.drawString(options[playerChar.get(4)], 425, 517);
			g.drawString(options[playerChar.get(5)], 525, 517);
			g.drawString(options[playerChar.get(6)], 625, 517);
			g.drawString(options[playerChar.get(7)], 725, 517);
			
		}
		
		public void keyPressed(int k) {
			
			
			if(k == KeyEvent.VK_ENTER) 
			{
				gsm.setState(gsm.STAGESELECTIONSTATE);
			}
			
			
			if(k == KeyEvent.VK_BACK_SPACE || k == KeyEvent.VK_ESCAPE){
				AudioManager audio = new AudioManager();
				audio.Playaudio("escape.wav");
				gsm.setState(gsm.TOURNAMENT8SELECTIONSTATE);
				playerChar.clear();
				
				
			}
			
		}
		public void keyReleased(int k) {}

		
	}

