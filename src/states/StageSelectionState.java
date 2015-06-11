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
import states.GameState;
import content.Stages;

public class StageSelectionState extends GameState {
	
	//dialegmenh pista
	private FileManager selectedStage;

	private int currentChoice;
	//arrow
	private BufferedImage arrow;
	private BufferedImage pressedArrow;
	private boolean leftArrowIsPressed = false;
	private boolean rightArrowIsPressed = false;
	
	private Font font;
	private int PreviousState;
	
	AudioManager audio = new AudioManager();
	
	public StageSelectionState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		try {
			currentChoice = 0;
			font = new Font("Arial", Font.PLAIN, 32);
			
			arrow = ImageIO.read(getClass().getResourceAsStream("/Arrows/arrow.gif"));
			pressedArrow = ImageIO.read(getClass().getResourceAsStream("/Arrows/arrowPressed.gif"));
			selectedStage = new FileManager("selectedStage.txt");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("Παρακαλώ διαλέξτε Πίστα", 200, 50);
		
		g.drawImage(Stages.get(currentChoice), 100, 100, 600, 400, null);
		
		if(leftArrowIsPressed) {
			g.drawImage(pressedArrow, 50, 275, null);
		}
		else {
			g.drawImage(arrow, 50, 275, null);
		}
		
		if(rightArrowIsPressed) {
			g.drawImage(pressedArrow, 749, 275, -50, 50, null);
		}
		else {
			g.drawImage(arrow, 749, 275, -50, 50, null);
		}
		
		
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE) 
		{	
			audio.Playaudio("escape.wav");
			if(PreviousState==2)
				gsm.setState(gsm.PLAYERSELECTIONSTATE);
				if(PreviousState==9)
					gsm.setState(gsm.TOURNAMENT4SELECTIONSTATE);
				if(PreviousState==10)
				 gsm.setState(gsm.TOURNAMENT8SELECTIONSTATE);
		}
		if(k == KeyEvent.VK_ENTER) {
			selectedStage.writeToFile(Integer.toString(currentChoice));
			gsm.setState(gsm.PLAYSTATE);
		}
		if(k == KeyEvent.VK_RIGHT) {
			rightArrowIsPressed = true;
			currentChoice++;
			if(currentChoice == Stages.NUM_STAGES) currentChoice = 0;
		}
		if(k == KeyEvent.VK_LEFT) {
			leftArrowIsPressed = true;
			currentChoice--;
			if(currentChoice == -1) currentChoice = Stages.NUM_STAGES - 1;
		}
		
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_RIGHT) {
			rightArrowIsPressed = false;
		}
		if(k == KeyEvent.VK_LEFT) {
			leftArrowIsPressed = false;
		}
	}

	
	public void setPreviousState(int aState)
	{
		PreviousState=aState;
	}

	public int getPreviousState() {
		return PreviousState;
	}

}	
