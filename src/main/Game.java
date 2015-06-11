package main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("I.T. Fighter");
		window.add(new GamePanel());		
		window.setUndecorated(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		window.pack();
		window.setLocationRelativeTo(null);

	}

}

