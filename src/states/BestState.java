package states;

import handlers.FileManager;
import handlers.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import content.Avatars;

public class BestState extends GameState{
	private FileManager file;
	private BufferedImage bg;
	private Font font;
	private ArrayList<String> data;

	private String[] players = {
			"Giorgos",
			"Katerina",
			"Aggelos",
			"Euthmhs",
			"Elena",
			"Panos",
			"Swkraths",
			"Nikiforos",
			"Vana",
			"Giannhs"
	};
	
	public BestState(GameStateManager gsm) {
		super(gsm);
		
		
		data = new ArrayList<String>();
		font = new Font("Arial", Font.BOLD, 24);	
		file = new FileManager("victories.txt");
		data = file.readFile();
	}

	public void init() {
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/menuBG.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		


	}

	public void update() {
		
	}

	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		for(int i = 0; i < 10; i++) {
			g.drawImage(Avatars.get(i), 50, (i * 60) + 5, 50, 50, null);
			g.drawString(players[i], 120, (i * 60) + 40);
			g.drawString(data.get(i), 300, (i * 60) + 40);
			g.drawString("συνολικές νίκες", 400, (i * 60) + 40);
		}
		
		
		
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_BACK_SPACE || k == KeyEvent.VK_ESCAPE){
			gsm.setState(gsm.MENUSTATE);
		}
		
	}

	public void keyReleased(int k) {}
	
}
