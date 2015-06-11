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

import main.GamePanel;

public class TournamentMenuState extends GameState {
	
	private BufferedImage bg;
	private int curChoice;
	
	private String[] options = {
			"Τεσσάρων ατόμων",
			"Οχτώ ατόμων"
	};
	
	
	
	public TournamentMenuState(GameStateManager gsm) {
		super(gsm);
		curChoice = 0;
		
	}

	
	AudioManager audio = new AudioManager();
	
	public void init() {
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/yolo.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		
	}

	public void draw(Graphics2D g) {
		
		Font font2 = new Font("Arial", Font.PLAIN, 24);
		Font font1 = new Font("Arial", Font.BOLD, 28);
		g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.setColor(Color.BLACK);
		g.setFont(font1);
		g.drawString("ΤΟΥΡΝΟΥΑ", 80, 100);
		
		for(int i = 0; i < options.length; i++) {
			if(i == curChoice) {
				g.setFont(font1);
				g.setColor(Color.RED);
			}
			else {
				g.setFont(font2);
				g.setColor(Color.GRAY);
			}
			g.drawString(options[i], 80, 150 + i * 40);
		}
		
	}

	public void select() {
		FileManager file = new FileManager("tournament.txt");
		
		if(curChoice == 0) {
			file.writeToFile("true 4 1 1");
			gsm.setState(6);
		}
		if(curChoice == 1) {
			file.writeToFile("true 8 1 1");
			gsm.setState(7);
			
		}

	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			audio.Playaudio("Select.wav");
			select();
			
		}
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE) {
			audio.Playaudio("escape.wav");
			curChoice = 0;
			gsm.setState(1);
			
		}
		if(k == KeyEvent.VK_UP) {
			curChoice--;
			if(curChoice == -1) curChoice = options.length - 1;
			audio.Playaudio("up-down.wav");
		}
		
		if(k == KeyEvent.VK_DOWN) {
			curChoice++;
			if(curChoice == options.length) curChoice = 0;
			audio.Playaudio("up-down.wav");
		}
	}
	public void keyReleased(int k) {}
}
