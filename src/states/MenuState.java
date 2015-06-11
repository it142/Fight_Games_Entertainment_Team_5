package states;

import handlers.AudioManager;
import handlers.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import states.GameState;

public class MenuState extends GameState{
	
	private String[] options = {
			"����� ����",
			"��������� �������",
			"������"
	};
	
	private int curChoice;
	
	private Font font1;
	private Font font2;
	private BufferedImage bg, logo;
	private AudioManager audio;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		curChoice = 0;
		init();
		audio=new AudioManager();
	}

	public void init() {
		try {
			font1 = new Font("Arial", Font.BOLD, 28);
			font2 = new Font("Arial", Font.PLAIN, 24);
			
			bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/yolo.png"));
			logo = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/logo.gif"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		
	}

	public void draw(Graphics2D g) {
		
		g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.drawImage(logo, 40, 0, 400, 200, null);
		
		g.setColor(Color.BLACK);
		
		
		for(int i = 0; i < options.length; i++) {
			if(i == curChoice) {
				g.setFont(font1);
				g.setColor(Color.RED);
			}
			else {
				g.setFont(font2);
				g.setColor(Color.GRAY);
			}
			g.drawString(options[i], 80, 230 + i * 40);
		}
		
	}
	
	public void select() {
		if(curChoice == 0) {
			gsm.setState(gsm.MODESELECTIONSTATE);
		}
		if(curChoice == 1) {
			gsm.setState(gsm.KALUTEROISTATE);
		}
		if(curChoice == 2) {
			System.exit(0);
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
			gsm.setState(0);
			
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
