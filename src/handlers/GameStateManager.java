package handlers;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import states.BestState;
import states.GameState;
import states.MenuState;
import states.ModeSelectionState;
import states.PlayState;
import states.PlayerSelectionState;
import states.StageSelectionState;
import states.Tournament4DiagramState;
import states.Tournament4SelectionState;
import states.Tournament8DiagramState;
import states.Tournament8SelectionState;
import states.TournamentMenuState;

public class GameStateManager {

	private ArrayList<GameState> gameStates;
	
	public final int MENUSTATE = 0;
	public final int MODESELECTIONSTATE = 1;
	public final int PLAYERSELECTIONSTATE = 2;
	public final int TOURNAMENTMENUSTATE = 3;
	public final int PLAYSTATE = 4;
	public final int KALUTEROISTATE = 5;
	public final int TOURNAMENT4SELECTIONSTATE =6;
	public final int TOURNAMENT8SELECTIONSTATE =7;
	public final int STAGESELECTIONSTATE = 8;
	public final int TOURNAMENT4DIAGRAMSTATE=9;
	public final int TOURNAMENT8DIAGRAMSTATE=10;
	
	
	private int currentState;
	
	AudioManager audio;
	
	MenuState Menu = new MenuState(this);
	ModeSelectionState Mode = new ModeSelectionState(this);
	PlayerSelectionState PlayerSelection = new PlayerSelectionState(this);
	TournamentMenuState TournamentMenu = new TournamentMenuState(this);
	PlayState Play = new PlayState(this);
	BestState Kaluteroi = new BestState(this);
	Tournament4SelectionState Tournament4 = new Tournament4SelectionState(this);
	Tournament8SelectionState Tournament8 = new Tournament8SelectionState(this);
	StageSelectionState Stage = new StageSelectionState(this);
	Tournament4DiagramState Diagram4 = new Tournament4DiagramState(this);
	Tournament8DiagramState Diagram8 = new Tournament8DiagramState(this);
	
	
	
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		audio = new AudioManager();
		currentState = MENUSTATE;
		audio.StartLoop("menu");
		
		
		
		gameStates.add(Menu);
		gameStates.add(Mode);
		gameStates.add(PlayerSelection);
		gameStates.add(TournamentMenu);
		gameStates.add(Play);
		gameStates.add(Kaluteroi);
		gameStates.add(Tournament4);
		gameStates.add(Tournament8);
		gameStates.add(Stage);
		gameStates.add(Diagram4);
		gameStates.add(Diagram8);
		
	}
	
	public void setState(int state) {
		currentState = state;
		
		//Αν ειναι τουρνουα
		
		
		if(currentState==1 && Mode.getPreviousState()==4)
		{
			audio.StartLoop("menu");
			Mode.setPreviousState(-1);
		}
		
		
		if(currentState==6)
		{
			Stage.setPreviousState(9);
			
		}
		
		if(currentState==7)
		{
			Stage.setPreviousState(10);
		}
		
		if(currentState==9)
		{
			Diagram4.setPlayerChar(Tournament4.getPlayerChar());
			Tournament4.setStartingPlayers();
		}
		
		if(currentState==10)
		{	
			Diagram8.setPlayerChar(Tournament8.getPlayerChar());
			for(int i=0;i<Tournament8.getPlayerChar().length;i++)
			
			Tournament8.setStartingPlayers();
		}
		
		//Αν ειναι 1v1
		if(currentState==2)
		{
			Stage.setPreviousState(2);
		}
		
		
		if(currentState == 4)
		{
			audio.StopLoop("menu");
			Stage.setPreviousState(4);
			Mode.setPreviousState(4);
	
		}
		
		if(currentState==8 && Stage.getPreviousState()==4)
		{
			audio.StartLoop("menu");
			Stage.setPreviousState(2);
			Mode.setPreviousState(-1);
		}
		
		
		
		gameStates.get(currentState).init();
		
		
		
		
	}
	
	public void update() {
		gameStates.get(currentState).update();
			
	}
	
	public void draw(Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		
		
		if((k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE) && currentState == 4)
		{
			
				audio.StopLoop("battle");
		}
		gameStates.get(currentState).keyPressed(k);
	}

	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
	
	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}
}
