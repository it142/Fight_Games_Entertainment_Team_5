package states;

import handlers.AudioManager;
import handlers.FileManager;
import handlers.GameStateManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Tournament4DiagramState extends GameState {
	

		private BufferedImage bg;
		private Font font,font1;
		private String curMatch, curLine;
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
		
		
		
		public Tournament4DiagramState(GameStateManager gsm) {
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
			FileManager file = new FileManager("4selectedPlayers.txt");
			String result = Integer.toString(playerChar.get(0)) + " " + Integer.toString(playerChar.get(1)) + " " + 
					Integer.toString(playerChar.get(2)) + " " + Integer.toString(playerChar.get(3));
			file.writeToFile(result);
			
		}
		
		
		
		
		
		public void init() {
			
			FileManager file = new FileManager("tournament.txt");
			ArrayList<String> data = file.readFile();
			curLine = data.get(2);
			curMatch = data.get(3);
			try{
				bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/menuBG.gif"));
				
				font = new Font("Arial", Font.PLAIN, 20);
				font1 = new Font("Arial", Font.BOLD, 24);
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		public void update() {
			
		}

		public void draw(Graphics2D g) {
			
			g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
			
			g.setFont(font1);
			g.setColor(Color.YELLOW);
			
			if(curLine.equals("1") && curMatch.equals("1")) {
				g.drawString(options[playerChar.get(0)] + "  vs  " + options[playerChar.get(1)], 20, 30);
				g.drawString("Πατήστε Enter για συνέχεια", 20, 60);
			}
			else if(curLine.equals("1") && curMatch.equals("2")) {
				g.drawString(options[playerChar.get(2)] + "  vs  " + options[playerChar.get(3)], 20, 30);
				g.drawString("Πατήστε Enter για συνέχεια", 20, 60);
			}
			
			
			g.setColor(Color.yellow);
			Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            
            g2.draw(new Line2D.Float(200,300,200,100));
            g2.draw(new Line2D.Float(200,100,400,100));
            
            g2.draw(new Line2D.Float(600,300,600,100));
            g2.draw(new Line2D.Float(600,100,400,100));
            
            
            //4
            g2.draw(new Line2D.Float(100,500,100,300));
            g2.draw(new Line2D.Float(100,300,200,300));
            
           
            g2.draw(new Line2D.Float(300,500,300,300));
            g2.draw(new Line2D.Float(300,300,200,300));
            
            g2.draw(new Line2D.Float(500,500,500,300));
            g2.draw(new Line2D.Float(500,300,600,300));
            
            g2.draw(new Line2D.Float(700,500,700,300));
            g2.draw(new Line2D.Float(700,300,500,300));
            
     
            
			
		
			
			g.setColor(Color.WHITE);
			g.fillRect(357,50, 100,100);
			
			g.fillRect(150,250, 100,100);
			g.fillRect(550,250, 100,100);
			
			g.fillRect(50,450, 100,100);
			g.fillRect(250,450, 100,100);
			g.fillRect(450,450, 100,100);
			g.fillRect(650,450, 100,100);
			
			
			g.setFont(font1);
			g.setColor(Color.black);
			g.drawString("Νικητής",360,40);
			g.setFont(font);
			g.drawString(options[playerChar.get(0)], 60, 505);
			g.drawString(options[playerChar.get(1)], 260, 505);
			g.drawString(options[playerChar.get(2)], 460, 505);
			g.drawString(options[playerChar.get(3)], 660, 505);
			
		}
		
		public void keyPressed(int k) {
			
			
			if(k == KeyEvent.VK_ENTER) 
			{
				gsm.setState(gsm.STAGESELECTIONSTATE);
			}
			
			
			if(k == KeyEvent.VK_BACK_SPACE || k == KeyEvent.VK_ESCAPE){
				AudioManager audio = new AudioManager();
				audio.Playaudio("escape.wav");
				gsm.setState(gsm.TOURNAMENT4SELECTIONSTATE);
				playerChar.clear();
				
				
			}
			
		}
		public void keyReleased(int k) {}

		
	}

