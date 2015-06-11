package main;

import handlers.FileManager;
import handlers.GameStateManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import content.Avatars;
import content.Powers;
import content.Stages;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	
	// dimension
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	// game thread
	private Thread thread;
	private boolean running = false;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private Graphics2D g;
	private BufferedImage image;
	
	//GameStateManager
	private GameStateManager gsm;
	
	public GamePanel() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		
		// init all
		Stages.load();
		Avatars.load();
		Powers.load();
		
		FileManager file = new FileManager("currentRound.txt");
		file.writeToFile("1 0 0");
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running) {
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(5);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void update() {
		gsm.update();
	}
	public void draw() {
		gsm.draw(g);
	}
	
	public void drawToScreen() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	public void keyTyped(KeyEvent key) {}

}
